package com.example.dsaminimo2;


import com.example.dsaminimo2.models.Museums;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MuseumService {

    @GET("api/dataset/museus/format/json/pag-ini/")
    Call<Museums> getMuseums();
}
