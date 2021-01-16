package test.voicerecognitiontest;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

//Open application voice command for welcome only once.
public class MainActivity extends AppCompatActivity {
    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    public Button speakButton,button_text_speech;
    public TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String text = "The usage of application is as follows , press volume down button to get the inbox." +
                "Then press volume down button to start reading the mail in order. To read above mail press volume up button." +
                "On Home page , press volume up button to compose mail. Press first time volume up button to add recipient." +
                " Second time volume up button to enter the subject. And third time volume uo button to add body." +
                "And volume down button to send mail.";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.speak("The usage of application is as follows , press volume down button to get the inbox.Then press volume down button to start reading the mail in order. To read above mail press volume up button.On Home page , press volume up button to compose mail. Press first time volume up button to add recipient. Second time volume up button to enter the subject. And third time volume uo button to add body.And volume down button to send mail.", TextToSpeech.QUEUE_FLUSH, null);

                }
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

        speakButton = (Button) findViewById(R.id.button_speak);
        button_text_speech = (Button)findViewById(R.id.button_text_speech);

        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        button_text_speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ActivityTextToSpeech.class);
                startActivity(intent);
            }
        });
    }
    public void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speech recognition demo");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Not supported",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Toast.makeText(getApplicationContext(),result.get(0),
                            Toast.LENGTH_SHORT).show();
//                    txtSpeechInput.setText(result.get(0));
                }
                break;
            }

        }

    }
    public void informationMenu() {
        startActivity(new Intent("android.intent.action.INFOSCREEN"));
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    //TODO
                    Toast.makeText(getApplicationContext(),
                            "Volume Up",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,ComposeEmail.class);
                    startActivity(intent);
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    //TODO
                    Toast.makeText(getApplicationContext(),
                            "Volume Down",
                            Toast.LENGTH_SHORT).show();
//                    new TestAsynk().execute();
//                    new MailReader();
                    Intent intent = new Intent(MainActivity.this,EmailDisplayActivity.class);
                    startActivity(intent);
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

        public class MyAsynk extends AsyncTask<Void, Void, Void> {
    //Voice - From - Subject - Body
            @Override
            protected Void doInBackground(Void... params) {

                Properties props = new Properties();
                props.setProperty("mail.store.protocol", "imaps");
                try {
                    Session session = Session.getInstance(props, null);
                    Store store = session.getStore();
                    store.connect("imap.gmail.com", "itgole3996@gmail.com", "9881424334");
                    Folder inbox = store.getFolder("INBOX");
                    inbox.open(Folder.READ_ONLY);
//                    Message[] msgs = inbox.getMessages();
                    Message message[]=inbox.getMessages();
                    for(int i=0;i<message.length;i++){

                        //print subjects of all mails in the inbox
                        try{
                            Log.d("SUBJECTS" , message[i].getSubject());
                            Log.d("FROM" , String.valueOf(message[i].getFrom()));

                        }catch (Exception e){
                            Log.e("EXCEPTION",Log.getStackTraceString(e));
                        }


                        //anything else you want

                    }

                    FetchProfile fp = new FetchProfile();
                    javax.mail.Message msg = inbox.getMessage(inbox.getMessageCount());

                    javax.mail.Address[] in = msg.getFrom();

                    for (javax.mail.Address address : in) {
                        System.out.println("FROM:" + address.toString());
                    }
                    Multipart mp = (Multipart) msg.getContent();
                    BodyPart bp = mp.getBodyPart(0);
                    System.out.println("SENT DATE:" + msg.getSentDate());
                    System.out.println("SUBJECT:" + msg.getSubject());
                    System.out.println("CONTENT:" + bp.getContent());
                } catch (Exception mex) {
                    mex.printStackTrace();
                }
                return null;
            }

    }

    public class TestAsynk extends AsyncTask<Void, Void, Void> {
        //Voice - From - Subject - Body
        @Override
        protected Void doInBackground(Void... params) {

            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imaps");
            try {
                Session session = Session.getInstance(props, null);
                Store store = session.getStore();
                store.connect("imap.gmail.com", "itgole3996@gmail.com", "9881424334");
                Folder inbox = store.getFolder("INBOX");
                inbox.open(Folder.READ_ONLY);
                FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.RECENT), false);
                Message messages[] = inbox.search(ft);
                String mail,sub,bodyText="";
                Object body;
                String result;
                for(Message message:messages) {
                    mail = message.getFrom()[0].toString();
                    sub = message.getSubject();
                    body = message.getContent();
                    Log.d("mail" , mail);
                    Log.d("sub" , sub);
//                    if (message.isMimeType("text/plain")) {
//                        result = message.getContent().toString();
//                      Log.d("body" , result);
//                    }
                    if (message.isMimeType("text/plain")) {
                        result = message.getContent().toString();
                        Log.d("body" , result);
                    } else if (message.isMimeType("multipart/*")) {
                        MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
                        result = getTextFromMimeMultipart(mimeMultipart);
                        Log.d("body" , result);
                    }

                    //bodyText = body.....
                }
            } catch (Exception mex) {
                mex.printStackTrace();
            }
            return null;
        }

    }
    public synchronized Message[] readMail() throws Exception {

            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imaps");
            try {
                Session session = Session.getInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect("imaps.gmail.com","itgole3996@gmail.com", "9881424334");
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            Message[] msgs = folder.getMessages(1, 10);
            FetchProfile fp = new FetchProfile();
            fp.add(FetchProfile.Item.ENVELOPE);
            folder.fetch(msgs, fp);
            return msgs;
        } catch (Exception e) {
            Log.e("readMail", e.getMessage(), e);
            return null;
        }
    }
    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart)  throws MessagingException, IOException {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
//            } else if (bodyPart.isMimeType("text/html")) {
//                String html = (String) bodyPart.getContent();
//                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
//            }
            }else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }


    public void onPause(){
        if(textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }
}
