package com.malicia.mrg.assistant.photo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MyConfigTest {

    @Autowired
    private MyConfig mockConfig; // Mocking the MyConfig dependency

    @Test
    void test_getName() {
        assertEquals("assistant", mockConfig.getName());
    }

}