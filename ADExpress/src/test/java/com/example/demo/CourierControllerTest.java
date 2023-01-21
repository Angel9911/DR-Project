package com.example.demo;

import com.example.demo.controllers.CourierController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CourierController.class})
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class CourierControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void whenGetPackages_thenReturnJsonArray() throws Exception{

    }
}
