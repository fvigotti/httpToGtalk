package fvigotti.httptogtalk.services;

import fvigotti.httptogtalk.entities.GtalkAuthParams;
import org.apache.log4j.Logger;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Consumer;

@Service
public class GtalkXmppSender {
    private static Logger log = Logger.getLogger(GtalkXmppSender.class);

    GtalkAuthParams gtalkAuthParams;

    @Autowired
    public GtalkXmppSender( GtalkAuthParams gtalkAuthParams ) {
        this.gtalkAuthParams = gtalkAuthParams;
    }

    public void sendMessage(String message,String destination) throws XMPPException {
        log.info("sending to : [" + destination + "] , message "+ message );

        Message msg = new Message(destination, Message.Type.chat);
        msg.setBody(message);

        connectAndDo(connection -> {
            ChatManager chatManager = connection.getChatManager();
            Chat chat = chatManager.createChat(destination, new MessageListener() {
                @Override
                public void processMessage(Chat chat, Message message) {
                    log.info("received message reply  : [" + chat.toString() + "] , message " + message.getSubject() + " / " + message.getBody());
                }
            });
            try {
                chat.sendMessage(msg);
            } catch (XMPPException e) {
                e.printStackTrace();
                log.error(e);
            }finally {
                log.info("chat session closed");
            }

            //connection.sendPacket(msg);
        });
    }


    protected void connectAndDo(Consumer<XMPPConnection> connectionConsumer) throws XMPPException {
        log.error("sending message authParams="+ Objects.toString(gtalkAuthParams));

        XMPPConnection connection = new XMPPConnection(gtalkAuthParams.getXmppHost());
        try {
            // Connect
            connection.connect();

            // Login with appropriate credentials
            connection.login(gtalkAuthParams.getXmppUser(), gtalkAuthParams.getXmppPass());

            connectionConsumer.accept(connection);

        } catch (XMPPException e) {
            // Do something better than this!
            e.printStackTrace();
            log.error(e);
            log.error("failed message authParams="+ Objects.toString(gtalkAuthParams));
            throw  e;
        }
    }

}
