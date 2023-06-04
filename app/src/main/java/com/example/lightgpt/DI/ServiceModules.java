package com.example.lightgpt.DI;

import com.example.lightgpt.Networks.IAuthService;
import com.example.lightgpt.Networks.IChatBotService;
import com.example.lightgpt.Networks.IProfileService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class ServiceModules {
    @Provides
    @Singleton
    public IProfileService iProfileService(Retrofit retrofit){
        return retrofit.create(IProfileService.class);
    }
    @Provides
    @Singleton
    public IChatBotService iChatBotService(Retrofit retrofit){
        return retrofit.create(IChatBotService.class);
    }
    @Provides
    @Singleton
    public IAuthService iAuthServiceInstance(Retrofit retrofit){
        return retrofit.create(IAuthService.class);
    }

    @Provides
    @Singleton
    public Retrofit retrofitBuilderMain(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS) // increase read timeout to 60 seconds
                .writeTimeout(60, TimeUnit.SECONDS) // increase write timeout to 60 seconds
                .build();

        return new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
