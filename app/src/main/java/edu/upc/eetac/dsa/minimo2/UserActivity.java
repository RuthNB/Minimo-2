package edu.upc.eetac.dsa.minimo2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

import edu.upc.eetac.dsa.minimo2.models.Repository;
import edu.upc.eetac.dsa.minimo2.models.User;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserActivity extends AppCompatActivity {
    TextView name,followers,following;
    ImageView profileImage;
    User usuario1;
    private static Retrofit retrofit;
    private static String baseUrl = "https://api.github.com/users/";
    UsuarioService usuarioService;
    String ultimoUsuario;
    private RecyclerView recyclerView;
    //As we added new methods inside our custom Adapter, we need to create our own type of adapter
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        ultimoUsuario = (settings.getString("ultimoUsuario", ""));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        name=findViewById(R.id.name);
        followers=findViewById(R.id.followers);
        following=findViewById(R.id.following);
        profileImage=findViewById(R.id.imageView);
        recyclerView = findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(false);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //HTTP &
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(interceptor).build();
        try{
            startRetrofit();
            usuarioService = retrofit.create(UsuarioService.class);
            //Get Data
            getData(ultimoUsuario);
        }catch(Exception e){
            //Not possible to connect to server
            e.printStackTrace();
            Toast toast = Toast.makeText(UserActivity.this,"Error al conectar con el servidor",Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    private static void startRetrofit(){
        //HTTP &
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //Attaching Interceptor to a client
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(interceptor).build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        // Running Retrofit to get result from Local tracks service Interface
        //Remember when using Local host on windows the IP is 10.0.2.2 for Android
        //Also added NullOnEmptyConverterFactory when the response from server is empty
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }
    private void getData(String username){
        //Set Progress Bar dialog
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
                        usuario1=response.body();
                        name.setText(usuario1.getLogin());
                        followers.setText(usuario1.getFollowers());
                        following.setText(usuario1.getFollowing());
                        Picasso.get().load(usuario1.getAvatarUrl()).into(profileImage);
                        try{
                            Call<List<Repository>> repositoryInfo = usuarioService.getRepositorios(username);
                            repositoryInfo.enqueue(new Callback<List<Repository>>() {
                                @Override
                                public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                                    mAdapter.setData(response.body());
                                }
                                @Override
                                public void onFailure(Call<List<Repository>> call, Throwable t) {
                                    Toast toast = Toast.makeText(UserActivity.this,"Usuario no encontrado",Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            });
                        }catch(Exception e){

                        }
                    }catch(Exception e){
                        Toast toast = Toast.makeText(UserActivity.this,"Usuario no encontrado",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast toast = Toast.makeText(UserActivity.this,"Usuario no encontrado",Toast.LENGTH_SHORT);
                    toast.show();                }
            });
        }
        catch(Exception e){
            Log.d("FindUser","Exception: " + e.toString());
        }
    }

}