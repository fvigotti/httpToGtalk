package fvigotti.httptogtalk;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RestController
public class HelloController {
    private Logger log = Logger.getLogger(HelloController.class);

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

    @RequestMapping("/gmessage")
    public String gmessage(@RequestBody(required = false) String input) {
        log.info("received message" + Optional.ofNullable(input).orElse("null"));
        return "success, received:"+Optional.ofNullable(input).orElse("null");
    }

}
