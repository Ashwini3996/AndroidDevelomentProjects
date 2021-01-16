package test.voicerecognitiontest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

import test.voicerecognitiontest.adapter.EmailAdapter;
import test.voicerecognitiontest.pojo.RecieveMailModel;

/**
 * Created by tanmay on 29/10/17.
 */

public class EmailDisplayActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private List<RecieveMailModel> recieveMailModels = new ArrayList<>();
    private EmailAdapter emailAdapter;
    Message messages[];
    TextToSpeech textToSpeech;
    int count;
    ProgressDialog progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        count = -1;
        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_email);

//        getEmail();
        progressBar = new ProgressDialog(EmailDisplayActivity.this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Getting Inbox messages");
        progressBar.show();
        new TestAsynk().execute();
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(EmailDisplayActivity.this);
        mLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        emailAdapter = new EmailAdapter(EmailDisplayActivity.this,recieveMailModels);
        recyclerView.setAdapter(emailAdapter);
    }

    public class TestAsynk extends AsyncTask<Object, Object, Message[]> {

        //Voice - From - Subject - Body
        @Override
        protected Message[] doInBackground(Object... params) {

            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imaps");
            try {
                Session session = Session.getInstance(props, null);
                Store store = session.getStore();
                store.connect("imap.gmail.com", "itgole3996@gmail.com", "9881424334");
                Folder inbox = store.getFolder("INBOX");
                inbox.open(Folder.READ_ONLY);
                FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.RECENT), false);

                messages = inbox.search(ft);
                /*String mail,sub,bodyText="";
                Object body;
                String result;
                RecieveMailModel recieveMailModel = new RecieveMailModel();
                for(Message message:messages) {
                    mail = message.getFrom()[0].toString();
                    sub = message.getSubject();
                    body = message.getContent();
                    Log.d("mail" , mail);
                    Log.d("sub" , sub);
                    recieveMailModel.setFrom(mail);
                    recieveMailModel.setSubject(sub);
//                    if (message.isMimeType("text/plain")) {
//                        result = message.getContent().toString();
//                      Log.d("body" , result);
//                    }
                    if (message.isMimeType("text/plain")) {
                        result = message.getContent().toString();
                        Log.d("body" , result);
                        recieveMailModel.setBody(result);
                    } else if (message.isMimeType("multipart*//*")) {
                        MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
                        result = getTextFromMimeMultipart(mimeMultipart);
                        Log.d("body" , result);
                        recieveMailModel.setBody(result);
                    }


                    //bodyText = body.....
                }*/


            } catch (Exception mex) {
                mex.printStackTrace();
            }
            return messages;
        }

        @Override
        protected void onPostExecute(Message[] aVoid) {
            super.onPostExecute(aVoid);
            progressBar.dismiss();
            try{
                String mail,sub,bodyText="";
                Object body;
                String result;

                for(Message message:aVoid) {
                    RecieveMailModel recieveMailModel = new RecieveMailModel();
                    mail = message.getFrom()[0].toString();
                    sub = message.getSubject();
                    body = message.getContent();
                    Log.d("mail" , mail);
                    Log.d("sub" , sub);
                    recieveMailModel.setFrom(mail);
                    recieveMailModel.setSubject(sub);
//                    if (message.isMimeType("text/plain")) {
//                        result = message.getContent().toString();
//                      Log.d("body" , result);
//                    }
                    if (message.isMimeType("text/plain")) {
                        result = message.getContent().toString();
                        Log.d("body" , result);
                        recieveMailModel.setBody(result);
                    } else if (message.isMimeType("multipart/*")) {
                        MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
                        result = getTextFromMimeMultipart(mimeMultipart);
                        Log.d("body" , result);
                        recieveMailModel.setBody(result);
                    }
                    //bodyText = body.....
                    recieveMailModels.add(recieveMailModel);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            emailAdapter = new EmailAdapter(EmailDisplayActivity.this,recieveMailModels);
            recyclerView.setAdapter(emailAdapter);
        }
    }

//    public void getEmail(){
//        Properties props = new Properties();
//        props.setProperty("mail.store.protocol", "imaps");
//        try {
//            Session session = Session.getInstance(props, null);
//            Store store = session.getStore();
//            store.connect("imap.gmail.com", "itgole3996@gmail.com", "9881424334");
//            Folder inbox = store.getFolder("INBOX");
//            inbox.open(Folder.READ_ONLY);
//            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.RECENT), false);
//            Message messages[] = inbox.search(ft);
//            String mail,sub,bodyText="";
//            Object body;
//            String result;
//            RecieveMailModel recieveMailModel = new RecieveMailModel();
//            for(Message message:messages) {
//                mail = message.getFrom()[0].toString();
//                sub = message.getSubject();
//                body = message.getContent();
//                Log.d("mail" , mail);
//                Log.d("sub" , sub);
//                recieveMailModel.setFrom(mail);
//                recieveMailModel.setSubject(sub);
////                    if (message.isMimeType("text/plain")) {
////                        result = message.getContent().toString();
////                      Log.d("body" , result);
////                    }
//                if (message.isMimeType("text/plain")) {
//                    result = message.getContent().toString();
//                    Log.d("body" , result);
//                    recieveMailModel.setBody(result);
//                } else if (message.isMimeType("multipart/*")) {
//                    MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
//                    result = getTextFromMimeMultipart(mimeMultipart);
//                    Log.d("body" , result);
//                    recieveMailModel.setBody(result);
//                }
//
//
//                //bodyText = body.....
//            }
//
//            emailAdapter = new EmailAdapter(EmailDisplayActivity.this,recieveMailModels);
//            recyclerView.setAdapter(emailAdapter);
//        } catch (Exception mex) {
//            mex.printStackTrace();
//        }
//    }
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
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    //TODO
                    if (count == -1){
                        textToSpeech.speak("Please press volume down button", TextToSpeech.QUEUE_FLUSH, null);
                    }else if (count == 0){
                        textToSpeech.speak(String.valueOf(recieveMailModels.get(count)), TextToSpeech.QUEUE_FLUSH, null);
                    }else {
                        count--;
                        textToSpeech.speak(String.valueOf(recieveMailModels.get(count)), TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    //TODO
                    count++;
                    if (count < recieveMailModels.size()){
                        Log.d("VOICE",String.valueOf(recieveMailModels.get(0)));
                        textToSpeech.speak(String.valueOf(recieveMailModels.get(count)), TextToSpeech.QUEUE_FLUSH, null);
                    }

                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }
    public void onPause(){
        if(textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }
}
