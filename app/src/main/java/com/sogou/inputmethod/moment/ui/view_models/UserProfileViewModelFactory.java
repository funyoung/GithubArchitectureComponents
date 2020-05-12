package com.sogou.inputmethod.moment.ui.view_models;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.sogou.inputmethod.moment.repositories.api.UserWebservice;
import com.sogou.inputmethod.moment.repositories.UserRepository;
import com.google.gson.Gson;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * singleton
 */
public class UserProfileViewModelFactory implements ViewModelProvider.Factory {
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserProfileViewModel.class)) {
            return (T) SingletonHolder.INSTANCE;
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

    private static class SingletonHolder {
        private static final UserProfileViewModel INSTANCE = new UserProfileViewModel(
                new UserRepository(getUserService(), getExecutor())
        );

        private static Executor getExecutor() {
            return Executors.newSingleThreadExecutor();
//            return Executors.newFixedThreadPool(THREAD_COUNT);
        }

        private static UserWebservice getUserService() {
            String BASE_URL = "https://api.github.com/";
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .baseUrl(BASE_URL)
                    .build();
            return retrofit.create(UserWebservice.class);
        }
    }
}
