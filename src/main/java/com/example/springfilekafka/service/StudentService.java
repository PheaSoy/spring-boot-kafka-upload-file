package com.example.springfilekafka.service;

import com.example.springfilekafka.domain.Student;
import com.example.springfilekafka.repo.StudentRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class StudentService {

    private final StudentRepo studentRepo;

    public void process(Student student) {
        log.info("Process the student:{}", student);
        studentRepo.save(student);
    }
}
