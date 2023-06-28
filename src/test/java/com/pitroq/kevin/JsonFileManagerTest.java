package com.pitroq.kevin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonFileManagerTest {
    @Test
    public void testIsJsonValid() {
        assertTrue(JsonFileManager.isJsonValid("{}"));
        assertFalse(JsonFileManager.isJsonValid(""));
        assertTrue(JsonFileManager.isJsonValid("""
            {
                "a" : 1
            }
        """));
        assertFalse(JsonFileManager.isJsonValid("""
            {
                "a" : 1,
            }
        """));
        assertFalse(JsonFileManager.isJsonValid("""
            {
                "a" 1
            }
        """));

    }
}
