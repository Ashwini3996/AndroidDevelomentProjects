package test.voicerecognitiontest;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import test.voicerecognitiontest.gmail.GMailSender;

/**
 * Created by tanmay on 24/09/17.
 */

public class ComposeEmail extends AppCompatActivity {


    private final int BODY_SPEECH = 100;
    private final int TO_SPEECH = 101;
    private final int SUBJECT_SPEECH = 102;
    EditText editText_body,editText_subject,editText_to;
    int count = -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_email);
        setTitle("Compose Email");
        editText_body = (EditText)findViewById(R.id.editText_body);
        editText_subject = (EditText)findViewById(R.id.editText_subject);
        editText_to = (EditText)findViewById(R.id.editText_to);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }
    private void promptSpeechInput(int REQUEST_CODE) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQUEST_CODE);
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
            case BODY_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Toast.makeText(getApplicationContext(),result.get(0),
                            Toast.LENGTH_SHORT).show();
                    editText_body.setText(result.get(0));
                }
                break;
            }
            case SUBJECT_SPEECH :{
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Toast.makeText(getApplicationContext(),result.get(0),
                            Toast.LENGTH_SHORT).show();
                    editText_subject.setText(result.get(0));
                }
                break;
            }
            case TO_SPEECH :{
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Toast.makeText(getApplicationContext(),result.get(0),
                            Toast.LENGTH_SHORT).show();
                    String a = result.get(0);
                    editText_to.setText(result.get(0).replaceAll("\\s+",""));
                }
                break;
            }

        }

    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    //TODO
                    count ++;
                    if (count == 0){
                        promptSpeechInput(TO_SPEECH);
                    }
                    if (count == 1){
                        promptSpeechInput(SUBJECT_SPEECH);
                    }
                    if ( count >= 2){
                        promptSpeechInput(BODY_SPEECH);
                    }

                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    String to = editText_to.getText().toString().trim();
                    String body = editText_body.getText().toString();
                    String subject = editText_subject.getText().toString();
                    try {
                        sendEmail(subject,body,to);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }
    private void sendEmail(String subject,String body,String recepient) throws Exception {
        GMailSender sender = new GMailSender("itgole3996@gmail.com", "9881424334");
        sender.sendMail(subject,
                body,
                "itgole3996@gmail.com",recepient);
        Utilities.toast(ComposeEmail.this,"Mail Sent to " + " " + recepient);
    }
}
