package pttk.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    public void test_empty_email() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Email("");
        });
    }

    @Test
    public void test_incorrect_no_eta() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Email("abccba");
        });
    }

    @Test
    public void test_incorrect_web_address() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Email("abc@www");
        });
    }

    @Test
    public void test_incorrect_empty_before_eta() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Email("@www");
        });
    }

    @Test
    public void test_correct() {
        assertDoesNotThrow(() -> {
            new Email("abc@cba.com");
        });
    }
}