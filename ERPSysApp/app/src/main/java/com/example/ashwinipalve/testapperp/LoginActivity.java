package com.example.ashwinipalve.testapperp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.signin.SignIn;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener
{

     private LinearLayout prof_section;
     private Button signOut;
     private SignInButton signIn;
     private TextView Name,Email;
     private ImageView prof_pic;
     private GoogleApiClient googleApiClient;
     private static final int REQ_CODE=9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prof_section=findViewById(R.id.prof_section);
        signOut=findViewById(R.id.logout_btn);
        signIn=findViewById(R.id.login_btn);

        Name=findViewById(R.id.user_name);
        Email=findViewById(R.id.email);
        prof_pic=findViewById(R.id.prof_pic);

        signIn.setOnClickListener(this);
        signOut.setOnClickListener(this);

        prof_section.setVisibility(View.GONE);
        GoogleSignInOptions signInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
         googleApiClient =new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();


    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.login_btn:
                signIn();
                break;

            case R.id.logout_btn:
                signOut();
                break;
        }

    }

    @Override

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

 private void signIn()
 {
     Intent intent=Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
     startActivityForResult(intent,REQ_CODE);


 }

 private void signOut()
 {

     Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
         @Override
         public void onResult(@NonNull Status status) {
             upadteUI(false);
         }
     });

 }

 private void handleResult(GoogleSignInResult result)
 {
   if(result.isSuccess())
   {
       GoogleSignInAccount account =result.getSignInAccount();
       String name=account.getDisplayName();
       String email=account.getEmail();
      // String img_url=account.getPhotoUrl().toString();

       Name.setText(name);
       Email.setText(email);

       //Glide.with(this).load(img_url).into(prof_pic);

        upadteUI(true);
   }
  else{
       upadteUI(false);
   }

 }

private void upadteUI(boolean isLogin)
{

if(isLogin)
{
    prof_section.setVisibility(View.VISIBLE);
    signIn.setVisibility(View.GONE);

}
else
    {
        prof_section.setVisibility(View.GONE);
        signIn.setVisibility(View.VISIBLE);


    }
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_CODE)
        {
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);

        }

    }
}
