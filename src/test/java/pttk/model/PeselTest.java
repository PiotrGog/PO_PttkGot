package pttk.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PeselTest {

    @Test
    public void test_empty_pesel()
    {
        assertThrows(IllegalArgumentException.class, () -> {new Pesel("");});
    }

    @Test
    public void test_incorrect_pesel()
    {
        assertThrows(IllegalArgumentException.class, () -> {new Pesel("01231248611");});
    }

    @Test
    public void test_correct_pesel()
    {
        assertDoesNotThrow(() -> {new Pesel("01231249611");});
    }

    @Test
    public void test_too_long_pesel()
    {
        assertThrows(IllegalArgumentException.class, () -> {new Pesel("012312496110");});
    }

    @Test
    public void test_too_short_pesel()
    {
        assertThrows(IllegalArgumentException.class, () -> {new Pesel("0312496110");});
    }
}