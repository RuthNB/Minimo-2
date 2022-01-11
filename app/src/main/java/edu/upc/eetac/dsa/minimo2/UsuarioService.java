package edu.upc.eetac.dsa.minimo2;
import java.util.List;

import edu.upc.eetac.dsa.minimo2.models.Repository;
import edu.upc.eetac.dsa.minimo2.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsuarioService {

    @GET("{usuario}")
    Call<User> getUsuario(@Path("usuario") String username);
    @GET("{usuario}/repos")
    Call<List<Repository>> getRepositorios(@Path("usuario") String username);
}