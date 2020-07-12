package com.affairs.killers.service.impl;

import com.affairs.killers.feign.ICourseFeignService;
import com.affairs.killers.service.IKillersService;
import com.affaris.common.to.ElectiveTo;
import com.affaris.common.utils.R;
import com.affaris.common.vo.CourseVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Vulgarities
 */
@Service
public class KillersServiceImpl implements IKillersService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    ICourseFeignService courseFeignService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean kill(Integer stuId, Integer couId, Integer teaId, String randomCode) {
        logger.info("start:" + System.getProperty("user.timezone"));
        // 合法性校验
        BoundHashOperations<String, String, String> hashOperations = stringRedisTemplate.boundHashOps("killers:info");
        String courseVoStr = hashOperations.get(String.valueOf(couId));
        logger.info("开始抢课");
        logger.info("courseVoStr" + courseVoStr);
        if (!StringUtils.isEmpty(courseVoStr)) {
            CourseVo courseVo = JSON.parseObject(courseVoStr, CourseVo.class);
            logger.info("从redis中获取到的CourseVo:" + courseVo);
            if (courseVo != null) {
                // 多重校验在合法时间内
                LocalDateTime couTime = courseVo.getCouTime();
                if (LocalDateTime.now().isAfter(couTime)) {
                    logger.info("时间校验通过！");
                    String randomCodeFromCourseVo = courseVo.getRandomCode();
                    // 随机码校验
                    if (randomCodeFromCourseVo.equals(randomCode)) {
                        logger.info("随机码通过！");
                        // 校验幂等性（每门课程每人只能选一次）
                        // 抢课成功后在redis中做标记
                        String redisKey = stuId + "_" + couId;
                        // 如果不存在就占个坑
                        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(redisKey, String.valueOf(teaId), 1, TimeUnit.DAYS);
                        if (flag != null && flag) {
                            logger.info("占坑成功！");
                            // 占位成功说明没选过，减信号量
                            RSemaphore semaphore = redissonClient.getSemaphore("killers:token:" + randomCodeFromCourseVo);
                            // 减一个
                            boolean tryAcquire = semaphore.tryAcquire(1);
                            if (tryAcquire) {
                                // redis中相应课程的最大可选人数减1，方便页面展示
                                updateCourseVosWithRedis(couId);
                                // 在redis中做一份选课表缓存
                                saveELectiveWithRedis(stuId, couId);

                                // 抢课成功交给课程服务处理
                                ElectiveTo electiveTo = new ElectiveTo();
                                electiveTo.setStuId(stuId);
                                electiveTo.setCouId(couId);
                                electiveTo.setTeaId(teaId);
                                electiveTo.setElectiveTime(LocalDateTime.now());
                                // 发送消息
                                String jsonString = JSON.toJSONString(electiveTo);
                                rabbitTemplate.convertAndSend("course-event-exchange", "course", jsonString);
                                logger.info("消息发送成功，内容是" + jsonString);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 在redis中做一份选课表的缓存
     *
     * @param stuId
     * @param couId
     */
    private void saveELectiveWithRedis(Integer stuId, Integer couId) {
        // 在redis中存一份已选课程表，key为当前学生id，值为已选课程id
        ListOperations<String, String> opsForList = stringRedisTemplate.opsForList();
        opsForList.leftPush("killers:elective:" + stuId, String.valueOf(couId));
        // 选课信息保存7天
        opsForList.getOperations().expire("killers:elective:" + stuId, 7, TimeUnit.DAYS);
    }

    /**
     * 对redis中相应课程的最大可选人数进行更新
     *
     * @param couId
     */
    private void updateCourseVosWithRedis(Integer couId) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        // 获取redis中已上架的课程信息
        String str = ops.get("killers:courseVos");
        List<CourseVo> courseVoListTmp = JSON.parseObject(str, new TypeReference<List<CourseVo>>() {
        });
        if (courseVoListTmp != null) {
            // redis中相应课程的最大可选人数减1，方便页面展示
            for (CourseVo vo : courseVoListTmp) {
                if (couId.equals(vo.getCouId())) {
                    vo.setCouCount(vo.getCouCount() - 1);
                    break;
                }
            }
            // 更新redis中的killers:courseVos
            String jsonString = JSON.toJSONString(courseVoListTmp);
            LocalDateTime timeTmp = LocalDateTime.now().plusDays(1).withHour(3).withMinute(0).withSecond(0);
            logger.info(timeTmp.toString());
            long endTime = timeTmp.toInstant(ZoneOffset.of("+8")).toEpochMilli();
            // 更新保存到redis中并设置过期时间(此刻到第二天三点钟再次更新的毫秒值)
            ops.set("killers:courseVos", jsonString, endTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void getCourse() {
        R courseList = courseFeignService.list();
        Integer code = (Integer) courseList.get("code");
        int successCode = 200;
        if (successCode == code) {
            // 此处存在类型转换的坑
            List<?> allCourse = (List<?>) courseList.get("allCourse");
            String str = JSON.toJSONString(allCourse);
            List<CourseVo> courseVos = JSON.parseObject(str, new TypeReference<List<CourseVo>>() {
            });

            courseVos.forEach(obj -> {
                // 为每个课程添加自己独一无二的随机码
                String randomCode = UUID.randomUUID().toString().replace("-", "");
                obj.setRandomCode(randomCode);

                BoundHashOperations<String, Object, Object> hashOperations = stringRedisTemplate.boundHashOps("killers:info");
                // 缓存课程详细信息
                String objString = JSON.toJSONString(obj);
                hashOperations.put(obj.getCouId() + "", objString);
                hashOperations.expire(1, TimeUnit.DAYS);
                // 分布式信号量
                RSemaphore semaphore = redissonClient.getSemaphore("killers:token:" + randomCode);
                // 课程的最大人数作为分布式信号量
                semaphore.trySetPermits(obj.getCouCount());
                semaphore.expire(1, TimeUnit.DAYS);
            });

            // 将可选的课程信息加入缓存
            String courseVosString = JSON.toJSONString(courseVos);
            stringRedisTemplate.opsForValue().set("killers:courseVos", courseVosString, Duration.ofDays(1));
        }
    }
}
