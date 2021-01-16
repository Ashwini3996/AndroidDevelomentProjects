package test.voicerecognitiontest.pojo;

/**
 * Created by tanmay on 29/10/17.
 */

public class RecieveMailModel {
    String from;
    String subject;
    String body;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Subject is " + subject+"  And the Body is "+ body;
    }
}
