package com.patient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = PatientServiceApplication.class)
@ExtendWith(SpringExtension.class)
class PatientServiceApplicationTests {

    @Test
    void contextLoads() {
    }
}