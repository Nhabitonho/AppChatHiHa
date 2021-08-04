package com.example.appchathiha.Fragments;

import com.example.appchathiha.Notifications.MyResponse;
import com.example.appchathiha.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAATkQFHew:APA91bEgJv-CWv1N7gD4hJiPkrN2pBMYo0-ja5MbtvFKZAkyE806fAJtbTGNJYzOosCviq06BbudV3shkd4YVG7FElUDDpt4q_PVE0s_TaUgP2hmGhBevVFX9gQQbVqAdvg_AeQsSR5J"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender bodybody);
}
