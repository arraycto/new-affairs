package com.affairs.course.service.impl;

import com.affairs.course.entity.Course;
import com.affairs.course.entity.Elective;
import com.affairs.course.mapper.ElectiveMapper;
import com.affairs.course.service.ICourseService;
import com.affairs.course.service.IElectiveService;
import com.affaris.common.dto.ElectiveDTO;
import com.affaris.common.vo.ElectiveVO;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * <p>
 * 选课信息 服务实现类
 * </p>
 *
 * @author Vulgarities
 * @since 2020-06-25
 */
@Service
public class ElectiveServiceImpl extends ServiceImpl<ElectiveMapper, Elective> implements IElectiveService {
    @Autowired
    private ICourseService courseService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 加入课程（消息队列消费者）
     *
     * @param message
     * @param jsonString
     * @param channel
     */
    @RabbitListener(queues = {"course-release-queue"})
    @Transactional(rollbackFor = Exception.class)
    public void saveSelectedCourseByMessage(Message message, String jsonString, Channel channel) {
        logger.debug("消息接收成功：内容是" + jsonString);
        logger.debug("message:" + message);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            ElectiveDTO electiveDTO = JSON.parseObject(jsonString, ElectiveDTO.class);
            // 更新选课表
            Elective elective = new Elective();
            BeanUtils.copyProperties(electiveDTO, elective);
            int insert = baseMapper.insert(elective);
            if (insert > 0) {
                // 更新课程表
                QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
                Integer couId = electiveDTO.getCouId();
                courseQueryWrapper.select("cou_count").eq("cou_id", couId);
                // 查出当前所选的课程信息
                Course course = courseService.getOne(courseQueryWrapper);
                Integer couCount = course.getCouCount() - 1;
                // 构造条件更新课程最大人数
                UpdateWrapper<Course> courseUpdateWrapper = new UpdateWrapper<>();
                courseUpdateWrapper.set("cou_count", couCount).eq("cou_id", couId);
                boolean update = courseService.update(courseUpdateWrapper);
                if (update) {
                    // 一切成功，确认消息
                    channel.basicAck(deliveryTag, false);
                } else {
                    throw new RuntimeException("An error occurred in saving the course selection information, and roll back...");
                }
            } else {
                throw new RuntimeException("An error occurred in saving the course selection information, and roll back...");
            }
        } catch (Exception e) {
            // 拒收消息
            try {
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    @Override
    public IPage<ElectiveVO> getSelectedCourseFromDataBase(Integer stuId, Long current) {
        Page<ElectiveVO> page = new Page<>();
        page.setSize(12);
        page.setCurrent(current);
        return baseMapper.getSelectedCourseFromDataBase(page, stuId);
    }
}
