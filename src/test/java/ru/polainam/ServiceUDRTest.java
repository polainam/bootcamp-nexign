package ru.polainam;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.polainam.models.Call;
import ru.polainam.models.UDR;
import ru.polainam.services.ServiceUDR;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ServiceUDRTest {

    @Autowired
    private ServiceUDR serviceUDR;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String TEST_CDR_DIRECTORY = "src/test/cdr_2024";
    private static final String TEST_REPORTS_DIRECTORY = "src/test/reports";
    private static final int TEST_CHARGING_PERIOD = 1;

    @Test
    void testGenerateUDR() throws IOException {
        serviceUDR.generateUDR(TEST_CDR_DIRECTORY, TEST_REPORTS_DIRECTORY, TEST_CHARGING_PERIOD);
        assertGeneratedUDRFiles();
    }

    private void assertGeneratedUDRFiles() throws IOException {
        for (int month = 1; month <= TEST_CHARGING_PERIOD; month++) {
            File monthDirectory = new File(TEST_REPORTS_DIRECTORY + "/" + month);
            if (!monthDirectory.exists()) {
                continue;
            }
            for (File file : Objects.requireNonNull(monthDirectory.listFiles())) {
                if (file.getName().endsWith(".json")) {
                    UDR actualUDR = objectMapper.readValue(file, UDR.class);
                    assertUDREquals(actualUDR, getExpectedUDRByMsisdn(actualUDR.getMsisdn()));
                }
            }
        }
    }

    private void assertUDREquals(UDR actualUDR, UDR expectedUDR) {
        assertEquals(expectedUDR.getIncomingCall().getTotalTime(), actualUDR.getIncomingCall().getTotalTime());
        assertEquals(expectedUDR.getOutcomingCall().getTotalTime(), actualUDR.getOutcomingCall().getTotalTime());
    }

    private UDR getExpectedUDRByMsisdn(String msisdn) {
        UDR expectedUDR = new UDR();
        if (msisdn.equals("79055678901")) {
            expectedUDR.setMsisdn("79055678901");
            expectedUDR.setIncomingCall(createCall("00:14:49"));
            expectedUDR.setOutcomingCall(createCall("00:07:27"));
        } else if (msisdn.equals("79011234567")) {
            expectedUDR.setMsisdn("79011234567");
            expectedUDR.setIncomingCall(createCall("00:59:34"));
            expectedUDR.setOutcomingCall(createCall(null));
        }
        return expectedUDR;
    }

    private Call createCall(String totalTime) {
        Call call = new Call();
        call.setTotalTime(totalTime);
        return call;
    }
}
