package edu.upc.eetac.dsa.minimo2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.upc.eetac.dsa.minimo2.models.User;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindUser extends AppCompatActivity {
    TextView usuario;
    User usuario1;
    private static Retrofit retrofit;
    private static String baseUrl = "https://api.github.com/users/";
    UsuarioService usuarioService;
    private ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);
        usuario = findViewById(R.id.usuario);
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        String ultimoUsuario = (settings.getString("ultimoUsuario", ""));
        if (ultimoUsuario!=null){
            usuario.setText(ultimoUsuario);
        }
        else{
            usuario.setText("");
        }


            startRetrofit();
            usuarioService = retrofit.create(UsuarioService.class);

    }
    public void buscarClicked(View view){
        Log.w("Login","usuario : "+usuario.getText().toString());
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("ultimoUsuario",usuario.getText().toString());
        editor.commit();
        String username = "ruthnb";
        if (usuario.getText().toString()!=null){
            username = usuario.getText().toString();
        }

        getData(username);
    }
    private static void startRetrofit(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        // Running Retrofit to get result from Local tracks service Interface
        //Remember when using Local host on windows the IP is 10.0.2.2 for Android
        //Also added NullOnEmptyConverterFactory when the response from server is empty
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    private Dialog progressDialog(){
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_progress);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }
    private void getData(String username){
        try {
            Call<User> userInfo = usuarioService.getUsuario(username);
            /* Android Doesn't allow synchronous execution of Http Request and so we must put it in queue*/
            userInfo.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    //Retrieve the result containing in the body
                    // non empty response, Mapping Json via Gson...
                    Log.d("FindUser","Server Response Ok");
                    //If clicked once then new player list else update the recyclerview
                   try{
                       usuario1 = response.body();
                       Intent intent = new Intent(FindUser.this,UserActivity.class);
                       startActivity(intent);
                   }catch(Exception e){
                       Toast toast = Toast.makeText(FindUser.this,"Error",Toast.LENGTH_SHORT);
                       toast.show();
                   }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast toast = Toast.makeText(FindUser.this,"Usuario no encontrado",Toast.LENGTH_SHORT);
                    toast.show();                }
            });
        }
        catch(Exception e){
            Log.d("FindUser","Exception: " + e.toString());
        }
    }
}