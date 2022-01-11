package edu.upc.eetac.dsa.minimo2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView userTextView,passTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userTextView = findViewById(R.id.username);
        passTextView = findViewById(R.id.password);
    }
    public void onSignInClicked(View view){
        //SignIn and close this activity if correct else stay
        //User==user && password == dsamola
        Log.w("Login","Username "+userTextView.getText().toString()+" password " +passTextView.getText().toString());

        if(userTextView.getText().toString().equals("user") && passTextView.getText().toString().equals("dsamola")){
            //Means the user has putten correct user and pass, we can store it
            Log.w("Login","Mean we are in Username "+userTextView.getText()+" password " +passTextView.getText());
            SharedPreferences settings = getSharedPreferences("UserInfo", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("Username",userTextView.getText().toString());
            editor.putString("Password",passTextView.getText().toString());
            editor.commit();
            //Succesfully logged in and saved the data in shared preference
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
    }
}
