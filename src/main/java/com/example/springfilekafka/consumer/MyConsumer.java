package com.example.springfilekafka.consumer;

import com.example.springfilekafka.domain.Student;
import com.example.springfilekafka.exception.MyRetryException;
import com.example.springfilekafka.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyConsumer {

    @Value(value = "${kafka.topic}")
    private String topic;

    @Autowired
    private StudentService studentService;

    @KafkaListener(topics = {"${kafka.topic}"}, groupId = "${kafka.groupId}",
            containerFactory = "kafkaListenerContainerFactory", concurrency = "10")
    @RetryableTopic(attempts = "5", backoff = @Backoff(delay = 2_000, maxDelay = 10_000, multiplier = 2)
            , include = MyRetryException.class)
    public void listenGroupFoo(ConsumerRecord<String, Student> record, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition
    ) {
        if (log.isDebugEnabled())
            log.debug("Received key:{}, Message:{} from the topic:{} and partition:{}", record.key(),
                    record.value(), topic,
                    partition);
        studentService.process(record.value());
    }

    @DltHandler
    public void listenDlt(Student in, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(KafkaHeaders.OFFSET) long offset) {
        log.debug("DLT Received: {} from {} @ {}", in, topic, offset);
    }
}
