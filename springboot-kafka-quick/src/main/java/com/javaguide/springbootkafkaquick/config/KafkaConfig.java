package com.javaguide.springbootkafkaquick.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;

@Configuration
public class KafkaConfig {

    @Value("${kafka.topic.my-topic}")
    String myTopic;
    @Value("${kafka.topic.my-topic2}")
    String myTopic2;

    /**
     * constructor inject the bean
     */
    @Bean
    public RecordMessageConverter jsonConvertor() {
        return new JsonMessageConverter();
    }

    /**
     * 通过注入一个 NewTopic 类型的 Bean 来创建 topic，如果 topic 已存在，则会忽略。
     */
    @Bean
    public NewTopic myTopic() {
        return new NewTopic(myTopic, 2, (short) 1);
    }

    @Bean
    public NewTopic myTopic2() {
        return new NewTopic(myTopic2, 1, (short) 1);
    }
}
