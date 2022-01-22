package com.example.springfilekafka.repo;

import com.example.springfilekafka.domain.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepo extends CrudRepository<Student, Long> {
}