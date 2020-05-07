package com.boisneyphilippe.githubarchitecturecomponents.view_models;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;

import com.boisneyphilippe.githubarchitecturecomponents.App;
import com.boisneyphilippe.githubarchitecturecomponents.data.api.UserWebservice;
import com.boisneyphilippe.githubarchitecturecomponents.data.database.MyDatabase;
import com.boisneyphilippe.githubarchitecturecomponents.data.database.dao.UserDao;
import com.boisneyphilippe.githubarchitecturecomponents.data.repositories.UserRepository;
import com.google.gson.Gson;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * singleton
 */
public class UserProfileViewModelFactory implements ViewModelProvider.Factory {
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserProfileViewModel.class)) {
            return (T) SingletonHolder.INSTANCE;
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

    private static class SingletonHolder {
        private static final UserProfileViewModel INSTANCE = new UserProfileViewModel(
                new UserRepository(getUserService(), getUserDao(), getExecutor())
        );

        private static Executor getExecutor() {
            return Executors.newSingleThreadExecutor();
//            return Executors.newFixedThreadPool(THREAD_COUNT);
        }

        private static UserDao getUserDao() {
            MyDatabase myDatabase = Room.databaseBuilder(App.context,
                    MyDatabase.class, "MyDatabase.db")
                    .build();
            return myDatabase.userDao();
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
