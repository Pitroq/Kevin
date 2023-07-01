package com.pitroq.kevin.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrowserControllerTest {
    @Test
    public void testFormatUrl() {
        assertEquals("https://facebook", BrowserController.formatURL("https://facebook"));
        assertEquals("http://facebook", BrowserController.formatURL("http://facebook"));
        assertEquals("file:///C:/Users", BrowserController.formatURL("file:///C:/Users"));

        assertEquals("http://192.168", BrowserController.formatURL("192.168"));
        assertEquals("https://facebook", BrowserController.formatURL("facebook"));
    }
}
