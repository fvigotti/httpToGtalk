package fvigotti.httptogtalk.configuration;

import fvigotti.httptogtalk.entities.GtalkAuthParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by francesco on 04/05/15.
 */
@Configuration
public class EnvPropertybasedConfiguration {

    @Value("#{systemEnvironment['gtalkMessageDestinations']}")
    String gtalkMessageDestinations;

    @Value("#{systemEnvironment['gtalkUser']}")
    String gtalkSender;

    @Value("#{systemEnvironment['gtalkToken']}")
    String gtalkSendPass;


    @Bean
    GtalkAuthParams gtalkAuthParams(){
        return new GtalkAuthParams(gtalkSender,gtalkSendPass,"gmail.com");
    }

}
