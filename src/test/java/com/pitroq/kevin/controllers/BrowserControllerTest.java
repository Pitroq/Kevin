package com.pitroq.kevin.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BrowserControllerTest {
    @Test
    public void formatUrl() {
        assertEquals("https://facebook", BrowserController.formatURL("https://facebook"));
//
    }

}
