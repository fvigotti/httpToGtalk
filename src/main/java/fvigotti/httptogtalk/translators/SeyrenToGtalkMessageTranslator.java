package fvigotti.httptogtalk.translators;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import static org.junit.Assert.assertTrue;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


@Component
public class SeyrenToGtalkMessageTranslator {
    private Logger log = Logger.getLogger(SeyrenToGtalkMessageTranslator.class);

    protected static final String translationErrorMessage = "TRANSLATION ERROR";

    AtomicLong translatedMessagesCount = new AtomicLong(0L);

    public String convertToGtalk(String seyrenJsonText){
        Long messageId = translatedMessagesCount.getAndIncrement();
        if (!(validatSource(seyrenJsonText))){
            log.error("invalid message source received : "+ Objects.toString(seyrenJsonText) + " , messageId= " + messageId);
            return getErrorMessage(messageId);
        }
        try {
            JSONParser parser = new JSONParser();
            return seyrenAlertToGtalk((JSONObject) parser.parse(seyrenJsonText));
        }catch (Exception ex)                        {
            log.error("error parsing message source , received : "+ Objects.toString(seyrenJsonText) + " , messageId= " + messageId);
            return getErrorMessage(messageId);
        }

    }

    protected String seyrenAlertToGtalk(JSONObject seyrenAlert){
        log.info("parsing seyrenAlert : "+ seyrenAlert.toJSONString());

        JSONArray alerts = (JSONArray) seyrenAlert.get("alerts");
        log.info("alerts count  : " + alerts.size());
        JSONObject mainAlert = (JSONObject) alerts.get(0);
        LocalDateTime alertMessageLocalDateTime = convertMillisecondsToDate(mainAlert);
        log.info("alertMessageLocalDateTime : " + alertMessageLocalDateTime);



        JSONObject check = (JSONObject) seyrenAlert.get("check");


        Map<String,String> response = new LinkedHashMap<>();
        response.put("name", (String) check.get("name"));
        response.put("STATE", (String) check.get("state"));
        response.put("date", alertMessageLocalDateTime.toString());

        response.put("value", Objects.toString(mainAlert.get("value")));
        response.put("warn", Objects.toString(mainAlert.get("warn")));
        response.put("error", Objects.toString(mainAlert.get("error")));

        response.put("fromType", (String) mainAlert.get("fromType"));
        response.put("toType", (String) mainAlert.get("toType"));
        response.put("target", (String) mainAlert.get("target"));

        //response.put("name", ((JSONObject)alerts.get(0)).get())
        return Objects.toString(response);
    }

    protected LocalDateTime convertMillisecondsToDate(JSONObject mainAlert) {
        long alertTimestampMilliseconds = (long) mainAlert.get("timestamp");
        return  LocalDateTime.ofEpochSecond(alertTimestampMilliseconds/1000,0, ZoneOffset.UTC);
    }


    protected String getErrorMessage(long messageId){
        return translationErrorMessage+" id:"+messageId;
    }
    protected boolean validatSource(String seyrenJsonText){
        return
                seyrenJsonText!= null && seyrenJsonText.length() > 1;

    }
}
