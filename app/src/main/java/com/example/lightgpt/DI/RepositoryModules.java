package com.example.lightgpt.DI;

import com.example.lightgpt.Networks.IAuthService;
import com.example.lightgpt.Networks.IChatBotService;
import com.example.lightgpt.Networks.IProfileService;
import com.example.lightgpt.Repositories.AuthRepo;
import com.example.lightgpt.Repositories.ChatBotRepo;
import com.example.lightgpt.Repositories.ProfileRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModules {
    @Provides
    @Singleton
    public AuthRepo authRepo(IAuthService iAuthService){
        return new AuthRepo(iAuthService);
    }

    @Provides
    @Singleton
    public ChatBotRepo chatBotRepo(IChatBotService iChatBotService){
        return new ChatBotRepo(iChatBotService);
    }

    @Provides
    @Singleton
    public ProfileRepo profileRepo(IProfileService iProfileService){
        return new ProfileRepo(iProfileService);
    }
}
