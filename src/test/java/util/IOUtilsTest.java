package util;

import java.io.BufferedReader;
import java.io.StringReader;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class IOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @Test
    void readData() throws Exception {
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        String readData = IOUtils.readData(br, data.length());
        logger.debug("parse body : {}", readData);
        assertThat(readData).isEqualTo(data);
    }
}