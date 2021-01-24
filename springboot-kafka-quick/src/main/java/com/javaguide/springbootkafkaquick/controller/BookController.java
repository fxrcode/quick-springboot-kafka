package com.javaguide.springbootkafkaquick.controller;

import com.javaguide.springbootkafkaquick.entity.Book;
import com.javaguide.springbootkafkaquick.service.BookProducerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = "/book")
public class BookController {
    @Value("${kafka.topic.my-topic}")
    String myTopic;
    @Value("${kafka.topic.my-topic2}")
    String myTopic2;
    private final BookProducerService producerService;
    private AtomicLong atomicLong = new AtomicLong();

    BookController(BookProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public void sendMessageToKafkaTopic(@RequestParam("name") String name) {
        this.producerService.sendMessage(myTopic, new Book(atomicLong.addAndGet(1), name));
        this.producerService.sendMessage(myTopic2, new Book(atomicLong.addAndGet(1), name));
    }
}
