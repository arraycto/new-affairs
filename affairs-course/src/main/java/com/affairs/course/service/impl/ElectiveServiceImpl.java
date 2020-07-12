package com.affairs.course.service.impl;

import com.affairs.course.entity.Course;
import com.affairs.course.entity.Elective;
import com.affairs.course.mapper.ElectiveMapper;
import com.affairs.course.service.ICourseService;
import com.affairs.course.service.IElectiveService;
import com.affaris.common.to.ElectiveTo;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = {"course-release-queue"})
    @Transactional(rollbackFor = Exception.class)
    public void receiveMessageToSaveElective(Message message, String jsonString, Channel channel) throws ExecutionException, InterruptedException, IOException {
        // 异步编排
        logger.info("消息接收成功：内容是" + jsonString);
        logger.info("message:" + message);
        ElectiveTo electiveTo = JSON.parseObject(jsonString, ElectiveTo.class);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        // 提取数据
        CompletableFuture<Boolean> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
            // 更新选课表
            Elective elective = new Elective();
            BeanUtils.copyProperties(electiveTo, elective);
            int flag = baseMapper.insert(elective);
            return flag > 0;
        });

        CompletableFuture<Boolean> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
            // 更新课程表
            QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
            Integer couId = electiveTo.getCouId();
            courseQueryWrapper.select("cou_count").eq("cou_id", couId);
            Course course = courseService.getOne(courseQueryWrapper);
            Integer couCount = course.getCouCount() - 1;
            // 构造条件更新课程最大人数
            UpdateWrapper<Course> courseUpdateWrapper = new UpdateWrapper<>();
            courseUpdateWrapper.set("cou_count", couCount).eq("cou_id", couId);
            return courseService.update(courseUpdateWrapper);
        });

        if (future1.get() && future2.get()) {
            channel.basicAck(deliveryTag, false);
        } else {
            channel.basicNack(deliveryTag, false, true);
        }
    }
}
