package com.the_trueth_league_academy.academy.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void testCreateUserId() {
        int id1 = userService.createUserId();
        int id2 = userService.createUserId();

        assertNotEquals(id1, id2, "Id should not be the same");
        System.out.println(id1 + " : " + id2);
    }

    @Test
    void testUnquieId() {

        // Act
        int id1 = userService.unquieId();
        int id2 = userService.unquieId();

        assertNotEquals(id1, id2, String.valueOf(id2));
    }
    
}
