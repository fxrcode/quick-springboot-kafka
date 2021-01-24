package com.javaguide.springbootkafkaquick.service;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;

@Service
public class BookProducerService {
    private static final Logger LOG = LoggerFactory.getLogger(BookProducerService.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public BookProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * dummy version of kafkaTemplate.send(topic, o)
     *  the send actually returned a ListenableFuture<SendResult<K,V>>
     * @param topic
     * @param o
     */
    @Deprecated
    public void sendMessageDummy(String topic, Object o) {
        kafkaTemplate.send(topic, o);
    }

    /**
     * Still not a good way to use KafkaTemplate, wasting the feature of ListenableFuture
     * @param topic
     * @param o
     */
    @Deprecated
    public void sendMessageSync(String topic, Object o) {
        try {
            SendResult<String, Object> sendResult = kafkaTemplate.send(topic, o).get();
            RecordMetadata metadata = sendResult.getRecordMetadata();
            if (metadata != null) {
                LOG.info("Producer sent message successfully to: " +
                        metadata.topic() +
                        " -> " +
                        sendResult.getProducerRecord().value().toString());
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * async version, anonymous callback for success & failure handling
     *  the callback interface is implemented as an anonymous class: ref to oracle tutorial
     * ListenableFuture.addCallback() requires ListenableFutureCallback<? super T> callback
     *  the callback is a consumer, so <? super T>
     * @param topic
     * @param o
     */
    @Deprecated
    public void sendMessageAsync0(String topic, Object o) {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, o);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                LOG.error("Producer send message {} failed for {}", o.toString(), ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                LOG.info("Producer sent message to {} -> {}", topic, result.getProducerRecord().value().toString());
            }
        });
    }

    public void sendMessageAsyncLambda(String topic, Object o) {

    }
}
