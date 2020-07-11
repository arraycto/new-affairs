package com.affairs.course.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Vulgarities
 */
@Configuration
public class MyRabbitMQConfig {
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Exchange courseEventExchange() {
        return new TopicExchange("course-event-exchange", true, false);
    }

    @Bean
    public Queue courseReleaseQueue() {
        return new Queue("course-release-queue", true, false, false);
    }

    @Bean
    public Binding courseReleaseBinding() {
        return new Binding("course-release-queue", Binding.DestinationType.QUEUE, "course-event-exchange", "course", null);
    }
}
