package fvigotti.httptogtalk.entities;

import org.springframework.context.annotation.Bean;

import java.util.Objects;

public class GtalkAuthParams {

    String xmppUser = "";
    String xmppPass = "";
    String xmppHost = "gmail.com"; // gmail.com

    public GtalkAuthParams() {
    }

    public GtalkAuthParams(String xmppUser, String xmppPass, String xmppHost) {
        this.xmppUser = xmppUser;
        this.xmppPass = xmppPass;
        this.xmppHost = xmppHost;
    }

    public String getXmppUser() {
        return xmppUser;
    }

    public void setXmppUser(String xmppUser) {
        this.xmppUser = xmppUser;
    }

    public String getXmppPass() {
        return xmppPass;
    }

    public void setXmppPass(String xmppPass) {
        this.xmppPass = xmppPass;
    }

    public String getXmppHost() {
        return xmppHost;
    }

    public void setXmppHost(String xmppHost) {
        this.xmppHost = xmppHost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xmppUser, xmppPass, xmppHost);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final GtalkAuthParams other = (GtalkAuthParams) obj;
        return Objects.equals(this.xmppUser, other.xmppUser) && Objects.equals(this.xmppPass, other.xmppPass) && Objects.equals(this.xmppHost, other.xmppHost);
    }

    @Override
    public String toString() {
        return "GtalkAuthParams{" +
                "xmppUser='" + xmppUser + '\'' +
                ", xmppPass='" + xmppPass + '\'' +
                ", xmppHost='" + xmppHost + '\'' +
                '}';
    }
}
