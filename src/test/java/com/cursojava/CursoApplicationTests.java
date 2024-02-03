package com.cursojava;

import com.cursojava.controllers.WebController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Assertions;
@SpringBootTest
class CursoApplicationTests {

        @Autowired
        private WebController controller;
        
	@Test
	void contextLoads() {
            Assertions.assertNotNull(controller);
	}

}
