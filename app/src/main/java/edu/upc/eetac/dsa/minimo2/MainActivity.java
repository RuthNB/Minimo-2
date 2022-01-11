package edu.upc.eetac.dsa.minimo2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import edu.upc.eetac.dsa.minimo2.models.Element;
import edu.upc.eetac.dsa.minimo2.models.Museums;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static Retrofit retrofit;
    protected static String username, password;
    private static String baseUrl = "https://do.diba.cat/";
    private Museums museums;
    //Museum Service
    MuseumService museumService;
    private ProgressBar spinner;
    private RecyclerView recyclerView;
    //As we added new methods inside our custom Adapter, we need to create our own type of adapter
        private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme); //Importantissiimooo
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.progressBar_cyclic);
        //Implementing RecyclerView
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
        //Attaching Interceptor to a client
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(interceptor).build();
        if(!ExistUserSaved()){
            LaunchLoginActivity();
        }else{
            //Connect with retrofit & Login Automatically and Retrieve the Player Data
            try{
                startRetrofit();
                museumService = retrofit.create(MuseumService.class);

                //Get Data
                getData();


            }catch(Exception e){
                //Not possible to connect to server
                e.printStackTrace();
                NotifyUser("Can't Connect to Server!");
            }
        }

    }
    private Dialog progressDialog(){
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_progress);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }
    private void getData(){
        //Set Progress Bar dialog
        spinner.setVisibility(View.VISIBLE);
        try {
            Call<Museums> playersStats = museumService.getMuseums();
            /* Android Doesn't allow synchronous execution of Http Request and so we must put it in queue*/
            playersStats.enqueue(new Callback<Museums>() {
                @Override
                public void onResponse(Call<Museums> call, Response<Museums> response) {
                    //Retrieve the result containing in the body
                        // non empty response, Mapping Json via Gson...
                        Log.d("MainActivity","Server Response Ok Museums");
                        museums = response.body();
                        spinner.setVisibility(View.GONE);
                        //If clicked once then new player list else update the recyclerview
                        if(mAdapter == null){
                            MyMuseumsRecyclerViewAdapter(museums.getElements());
                        }else{
                            mAdapter = null;
                            MyMuseumsRecyclerViewAdapter(museums.getElements());
                        }
                }
                @Override
                public void onFailure(Call<Museums> call, Throwable t) {
                    NotifyUser("Error,could not retrieve data!");
                }
            });
        }
        catch(Exception e){
            Log.d("RankingActivity","Exception: " + e.toString());
        }
    }
    private void MyMuseumsRecyclerViewAdapter(List<Element> elements){
        mAdapter = new MyAdapter(elements);
        recyclerView.setAdapter(mAdapter);
        mAdapter.SetOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //TODO NEED TO IMPLEMENT PLAYER STATS DETAIL ACTIVITY
                LaunchMuseumDetailPopup(position);
            }
        });
    }
    private void LaunchMuseumDetailPopup(int position){
        //Start a new activity with the detailed data of the Museum
        Log.d("Museum Detail Activity","Detail of the Museum PopUp");
        Intent intent = new Intent(MainActivity.this , MuseumDetailActivity.class);
        intent.putExtra("Element",museums.getElements().get(position));
        startActivityForResult(intent,2);
    }
    private void NotifyUser(String showMessage){
        Toast toast = Toast.makeText(MainActivity.this,showMessage,Toast.LENGTH_SHORT);
        toast.show();
    }
    private void LaunchLoginActivity() {
        Intent intent = new Intent(MainActivity.this ,LoginActivity.class);
        startActivityForResult(intent,1);
    }
    private boolean ExistUserSaved(){
        //Access the shared preference UserInfo and obtain the parameters, default =string empty
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        username = (settings.getString("Username", ""));
        password = (settings.getString("Password", ""));
        return !username.equals("");
    }
    private static void startRetrofit(){
        //HTTP &
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //Attaching Interceptor to a client
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(interceptor).build();

        // Running Retrofit to get result from Local tracks service Interface
        //Remember when using Local host on windows the IP is 10.0.2.2 for Android
        //Also added NullOnEmptyConverterFactory when the response from server is empty
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If login activity closed means the user has logged in, and the data is stored in the database
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Log.w("MainActivity","Sucesfully Logged In");
                //User Logged In
               if(!ExistUserSaved()){
                   Log.w("MainActivity","Sucesfully Logged In than going back to login again, something bad!");
                   LaunchLoginActivity();
               }
               //After Logging in, Get the Data from the Web
                Log.w("MainActivity","Sucesfully Logged In,Now getting the data from the web");
                getData();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Not Possible due to the fact Login is a must
                Log.w("MainActivity","Somehow user cancelled the login activity and got back to main");
            }
        }
    }
}
