package com.example.gabriela.asynctaskapp.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiCaller extends AsyncTask <Void, Integer, String>{

    private static final String TAG = "ApiCaller";

    private boolean isPost = false;

    private String url = "";
    private int statusCode = 0;
    private OnApiCallFinish listener;

    private HashMap<String, String> idiomas;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setOnApiCallFinish(OnApiCallFinish listener) {
        this.listener = listener;
    }

    public void setPost(boolean post) {
        isPost = post;
    }

    public void setIdiomas(HashMap<String, String> idiomas) {
        this.idiomas = idiomas;
    }

    @Override
    protected String doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();

        if(isPost){
            if (!this.url.equals("")){
                if(!idiomas.isEmpty()){
                    //Crear un builder que tiene los idiiomas
                    FormBody.Builder bodyBuilder = new FormBody.Builder();
                    for(Map.Entry<String, String> i:idiomas.entrySet()){
                        bodyBuilder.add(String.valueOf(i.getKey()), i.getValue());
                    }
                    RequestBody body = bodyBuilder.build();
                    Request request = new Request.Builder().url(this.url).post(body).build();

                    //procesar las respuestas
                    try {
                        Response response = client.newCall(request).execute();
                        this.statusCode = response.code();
                        if (this.statusCode >= 200 && this.statusCode < 300) {
                            if (response.body() != null) {
                                return response.body().string();
                            }else{
                                return "";
                            }
                        }else{
                            return "";
                        }

                    }catch (IOException e){
                        Log.e(TAG,e.getMessage());

                    }
                    return  "";
                }
            }
            return "";
        }else{
            Request.Builder requestBuilder = new Request.Builder();
            //evaluar condiciones
            if (!this.url.equals("")){
                requestBuilder.url(this.url);
            }
            //ejecutamos llamada al server
            Request request = requestBuilder.build();
            //procesar las respuestas
            try {
                Response response = client.newCall(request).execute();
                this.statusCode = response.code();
                if (this.statusCode >= 200 && this.statusCode < 300) {
                    if (response.body() != null) {
                        return response.body().string();
                    }else{
                        return "";
                    }
                }else{
                    return "";
                }

            }catch (IOException e){
                Log.e(TAG,e.getMessage());

            }
            return  "";
        }
    }


    @Override
    protected void onPostExecute(String content) {
        super.onPostExecute(content);
        if(this.listener == null){
            return;
        }
            if (this.statusCode >= 200 && this.statusCode < 300) {
                this.listener.onSuccess(content);
            }else{
                this.listener.onError(this.statusCode);
            }
    }

}
