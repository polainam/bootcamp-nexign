package ru.polainam;
import org.junit.jupiter.api.Test;
import ru.polainam.utils.Parser;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FormatDurationTest {

    @Test
    void testFormatDuration() {
        Duration duration = Duration.ofHours(2).plusMinutes(30).plusSeconds(15);

        String formattedDuration = Parser.formatDuration(duration);

        assertEquals("02:30:15", formattedDuration);
    }
}

