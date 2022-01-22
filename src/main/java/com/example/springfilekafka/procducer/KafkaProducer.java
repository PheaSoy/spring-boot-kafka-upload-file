package com.example.springfilekafka.procducer;

import com.example.springfilekafka.domain.MessageEvent;
import com.example.springfilekafka.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
@Primary
public class KafkaProducer implements MessageSender {

    @Autowired
    private KafkaTemplate<String, Student> kafkaTemplate;

    private void sendMessage(String topicName, Student student) {

        ListenableFuture<SendResult<String, Student>> future =
                kafkaTemplate.send(topicName, student);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Student>>() {
            @Override
            public void onSuccess(SendResult<String, Student> result) {
                log.info("Sent message = {} with offset : {}, partition:{}", student, result.getRecordMetadata().offset()
                        , result.getRecordMetadata().partition());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Failed to send the message with error :{}", ex.getMessage());
            }
        });
    }

    @Override
    public void send(MessageEvent messageEvent) {
        this.sendMessage(messageEvent.getDestination(),(Student) messageEvent.getMessageBody());
    }
}
