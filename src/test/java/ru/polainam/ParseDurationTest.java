package ru.polainam;
import org.junit.jupiter.api.Test;
import ru.polainam.utils.Parser;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParseDurationTest {

    @Test
    void testParseDuration() {
        String time = "01:15:30";

        Duration parsedDuration = Parser.parseDuration(time);

        assertEquals(Duration.ofHours(1).plusMinutes(15).plusSeconds(30), parsedDuration);
    }
}

