package fvigotti.httptogtalk.translators;

import com.sun.scenario.effect.Offset;
import fvigotti.httptogtalk.utils.ResourceUtil;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class SeyrenToGtalkMessageTranslatorTest {
    private Logger log = Logger.getLogger(SeyrenToGtalkMessageTranslator.class);

    SeyrenToGtalkMessageTranslator seyrenToGtalkMessageTranslator;

    String sampleJsonResourcePath = "samples/seyrenAlertSample.json";

    @Before
    public void setup(){
        seyrenToGtalkMessageTranslator = new SeyrenToGtalkMessageTranslator();
    }

    @Test
    public void invalidMessageShouldReturnErrorInTranslation(){
        String invalidMessage_0 = seyrenToGtalkMessageTranslator.convertToGtalk("");
        String invalidMessage_1 = seyrenToGtalkMessageTranslator.convertToGtalk(null);

        assertEquals(invalidMessage_0,seyrenToGtalkMessageTranslator.getErrorMessage(0l));
        assertEquals(invalidMessage_1,seyrenToGtalkMessageTranslator.getErrorMessage(1l));
    }


    @Test
    public void readValidMessageData() throws IOException, ParseException {
        String jsonSampleText = ResourceUtil.getLocalResource(sampleJsonResourcePath);

        String gtalkMessage = seyrenToGtalkMessageTranslator.convertToGtalk(jsonSampleText);

        log.info("parsed message = " + gtalkMessage);
        assertTrue(gtalkMessage.contains("name="));
    }




}