package com.example.gabriela.asynctaskapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.gabriela.asynctaskapp.api.Response;
import com.example.gabriela.asynctaskapp.utils.ApiCaller;
import com.example.gabriela.asynctaskapp.utils.OnApiCallFinish;
import com.google.gson.Gson;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements OnApiCallFinish {

    //Objeto tipo clave valor
    HashMap<String, String> idiomas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Valores del HashMap
        this.idiomas = new HashMap<String, String>();
        this.idiomas.put("Paris","Frances");
        this.idiomas.put("Inglaterra","Ingeles");

        //Metodo Get
        ApiCaller task = new ApiCaller();
        task.setUrl("https://visibleoutsource.com/admin/api/idiomas/lista");
        task.setOnApiCallFinish(this);
        task.execute();

        //Metodo Post
        ApiCaller postTask = new ApiCaller();
        postTask.setUrl("https://github.com");
        postTask.setPost(true);
        postTask.setIdiomas(idiomas);
        postTask.setOnApiCallFinish(this);
        postTask.execute();
    }

    @Override
    public void onSuccess(String content) {
            Response response = new Gson().fromJson(content, Response.class);
            Toast.makeText(this, response.getResults().size()+"", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(Integer code) {
        Toast.makeText(this, code+"", Toast.LENGTH_SHORT).show();
    }
}
