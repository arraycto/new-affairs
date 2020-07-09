package com.affairs.killers.service.impl;

import com.affairs.killers.feign.ICourseFeignService;
import com.affairs.killers.service.IKillersService;
import com.affairs.killers.vo.CourseVo;
import com.affaris.common.utils.R;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
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

    Logger logger = LoggerFactory.getLogger(this.getClass());

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
