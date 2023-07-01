package com.pitroq.kevin.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TasksControllerTest {
    @Test
    public void testFormatUrl() {
        assertEquals("user", TasksController.columnNameToCamelCase("user"));
        assertEquals("userDataLoader", TasksController.columnNameToCamelCase("user data loader"));
        assertEquals("userName", TasksController.columnNameToCamelCase("user name"));
    }
}
