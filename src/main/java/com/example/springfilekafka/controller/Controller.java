package com.example.springfilekafka.controller;

import com.example.springfilekafka.domain.MessageEvent;
import com.example.springfilekafka.domain.MessageType;
import com.example.springfilekafka.domain.Student;
import com.example.springfilekafka.file.CsvUtils;
import com.example.springfilekafka.procducer.MessageSender;
import com.example.springfilekafka.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class Controller {
    @Autowired
    MessageSender messageSender;

    @Value(value = "${kafka.topic}")
    private String topic;

    @Autowired
    StudentRepo studentRepo;

    @PostMapping("/bulk-file")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        List<Student> read = CsvUtils.read(Student.class, file.getInputStream());

        read.forEach(stud -> {
            MessageEvent messageEvent = MessageEvent.builder()
                    .messageType(MessageType.KAFKA)
                    .destination(topic)
                    .messageBody(stud)
                    .build();
            messageSender.send(messageEvent);
        });
        return ResponseEntity.ok("Done");
    }
}
