package com.malicia.mrg.assistant.photo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AppPropertiesTest {

    @Autowired
    private AppProperties appProperties;

    @Test
    void testApplicationProperties() {
        assertEquals("TestApp", appProperties.getName());
        assertEquals("1.0", appProperties.getVersion());
    }
}
