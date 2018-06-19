package com.example.gabriela.asynctaskapp.utils;

public interface OnApiCallFinish {
    void onSuccess(String content);
    void onError(Integer code);
}
