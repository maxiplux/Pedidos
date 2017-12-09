package net.juanfrancisco.blog.pedidos.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.orm.query.Select;

import net.juanfrancisco.blog.pedidos.Adapters.DataAdapter;
import net.juanfrancisco.blog.pedidos.Models.AndroidVersion;
import net.juanfrancisco.blog.pedidos.Models.Producto;
import net.juanfrancisco.blog.pedidos.R;
import net.juanfrancisco.blog.pedidos.Services.ProductoServices;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

public class ListaProductosActivity extends AppCompatActivity {



    private final String android_version_names[] = {
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Marshmallow"
    };
    private final String android_image_urls[] =
            {
            "http://api.learn2crack.com/android/images/donut.png",
            "http://api.learn2crack.com/android/images/eclair.png",
            "http://api.learn2crack.com/android/images/froyo.png",
            "http://api.learn2crack.com/android/images/ginger.png",
            "http://api.learn2crack.com/android/images/honey.png",
            "http://api.learn2crack.com/android/images/icecream.png",
            "http://api.learn2crack.com/android/images/jellybean.png",
            "http://api.learn2crack.com/android/images/kitkat.png",
            "http://api.learn2crack.com/android/images/lollipop.png",
            "http://api.learn2crack.com/android/images/marshmallow.png"
    };
    String rawJsonData;
    SharedPreferences pref ;
    String token;
    ProductoServices product_services;
    private String URL_BASE = "http://servicios.testbox.site/";
    private String PRODUCTOS_URI = "api/v1/productos/";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);



        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode

        token=pref.getString("TOKEN", null); // getting String


//        Producto.findById(Producto.class, (long) 1);


        Intent intent = getIntent();

        String name = intent.getStringExtra("TOKEN");

        product_services =new ProductoServices();





        rawJsonData="";


        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Importante?")
                .setContentText("Descargar actualizacion de productos!")
                .setConfirmText("Si ,Procede!")
                .setCancelText("No,Cancelar!")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                {
                    @Override
                    public void onClick(SweetAlertDialog sDialog)
                    {
                        Sincronizar(sDialog);

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        ConsultarProductos(sDialog);
                    }
                })

                .show();

        //Sincronizar();
        //ConsultarProductos();


    }


    private void ConsultarProductos(SweetAlertDialog sDialog)
    {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListaProductosActivity.this);
        recyclerView.setLayoutManager(layoutManager);


        List<Producto> android_version =  Select.from(Producto.class).list();
        ArrayList<Producto> productos = (ArrayList<Producto>) android_version;
        DataAdapter adapter = new DataAdapter(ListaProductosActivity.this,  productos);
        recyclerView.setAdapter(adapter);

        sDialog.cancel();
    }

    private void Sincronizar(final SweetAlertDialog sDialog)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "TOKEN "+token);
        client.get(URL_BASE+PRODUCTOS_URI, new BaseJsonHttpResponseHandler()
        {


            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Log.e("Codigo ", String.valueOf(statusCode));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.e("Codigo error ", String.valueOf(statusCode));
                Log.e("traza", throwable.toString());
                sDialog.dismissWithAnimation();


            }

            @Override
            protected Object parseResponse(String tmp_rawJsonData, boolean isFailure) throws Throwable {



                Gson gson = new GsonBuilder().create();

                Log.e("que paso", tmp_rawJsonData);
                //JSONObject jsonObject = new JSONObject(rawJsonData.toString());
                //rawJsonData=tmp_rawJsonData;
                product_services = gson.fromJson(tmp_rawJsonData, ProductoServices.class);
                Log.e("genero servicio", product_services.toString());
                if (product_services.valid())
                {


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
                            recyclerView.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListaProductosActivity.this);
                            recyclerView.setLayoutManager(layoutManager);


                            ArrayList<Producto> android_version = new ArrayList<>();

                            ArrayList<Producto> productos =product_services.getResults();
                            DataAdapter adapter = new DataAdapter(ListaProductosActivity.this,  productos);
                            recyclerView.setAdapter(adapter);

                            for(Producto producto : productos)
                            {

                                producto.save();

                            }
                            sDialog.dismissWithAnimation();




                        }
                    });







                }
                else
                {

                    runOnUiThread(new Runnable()

                    {
                        public void run()
                        {

                            new SweetAlertDialog(ListaProductosActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("No se que paso , perdon ;( llama al developer ya  +57-3168101369!")
                                    .show();
                            sDialog.dismissWithAnimation();


                        }
                    });


                }










                //initViews();






                return null;



            }
        });


    }
    private ArrayList<AndroidVersion> prepareData(){

        ArrayList<AndroidVersion> android_version = new ArrayList<>();
        for(int i=0;i<android_version_names.length;i++)
        {
            AndroidVersion androidVersion = new AndroidVersion();
            androidVersion.setAndroid_version_name(android_version_names[i]);
            androidVersion.setAndroid_image_url(android_image_urls[i]);
            android_version.add(androidVersion);
        }
        return android_version;
    }
}
