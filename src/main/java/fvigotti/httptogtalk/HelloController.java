package fvigotti.httptogtalk;

import fvigotti.httptogtalk.services.GtalkXmppSender;
import fvigotti.httptogtalk.translators.SeyrenToGtalkMessageTranslator;
import org.apache.log4j.Logger;
import org.jivesoftware.smack.XMPPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class HelloController {
    private Logger log = Logger.getLogger(HelloController.class);


    @Autowired
    SeyrenToGtalkMessageTranslator seyrenToGtalkMessageTranslator;

    @Autowired
    GtalkXmppSender gtalkXmppSender;

    @Value("#{systemEnvironment['gtalkMessageDestinations']}")
    String gtalkMessageDestinations;



    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    /*

        public class SeyrenAlert{
            String seyrenUrl", seyrenConfig.getBaseUrl());
            String check", check);
            String subscription", subscription);
            String alerts", alerts);
            String preview", getPreviewImage(check));
        }
    */
    List<String> getGtalkDestinations(){
        return Arrays.asList(gtalkMessageDestinations.split(","));
    }
    @RequestMapping("/gmessage")
    public String gmessage(@RequestBody(required = false) String input) {
        log.info("received message" + Optional.ofNullable(input).orElse("null"));
        AtomicReference<String> statusMessage = new AtomicReference<>("success");

        getGtalkDestinations().forEach( gtalkMessageDestination-> {
            String gtalkMessage=seyrenToGtalkMessageTranslator.convertToGtalk(input);
            try {
                gtalkXmppSender.sendMessage(gtalkMessage,gtalkMessageDestination);
            } catch (XMPPException e) {
                log.error(e);
                statusMessage.set("error");
            }
        });

        return statusMessage.get();
    }

}
