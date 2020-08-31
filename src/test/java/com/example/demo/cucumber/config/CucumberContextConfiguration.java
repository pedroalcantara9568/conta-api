package com.example.demo.cucumber.config;

import com.example.demo.ContaApplication;
import io.cucumber.java.Before;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;


@SpringBootTest(classes = ContaApplication.class)
public class CucumberContextConfiguration {

    @Before
    @Transactional
    public void setup_cucumber_spring_context() {
        
    }

}
