package com.mytasks.app;

import com.mytasks.app.model.User;

public class TestUserFactory {
    public static User createTestUser() {
        User user = new User();
        user.setId(1L);
        user.setName("user1");
        user.setEmail("user1@example.com");
        user.setPassword("secret");
        return user;
    }

    public static User createOtherTestUser() {
        User user = new User();
        user.setId(2L);
        user.setName("user2");
        user.setEmail("user2@example.com");
        user.setPassword("secret");
        return user;
    }
}
