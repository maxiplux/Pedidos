package net.juanfrancisco.blog.pedidos.Services;

import net.juanfrancisco.blog.pedidos.Models.Producto;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by franc on 29/08/2016.
 */
public interface  ProductoServices {

    @GET("/api/v1/productos/")
    Call<List<Producto>> listRepos(@Path("producto") Producto producto);

}
