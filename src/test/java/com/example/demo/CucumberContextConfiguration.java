package com.example.demo;

import com.example.demo.BancoApplication;
import io.cucumber.java.Before;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;


@SpringBootTest(classes = BancoApplication.class)
public class CucumberContextConfiguration {

    @Before
    @Transactional
    public void setup_cucumber_spring_context() {

    }
}
