package com.sogou.inputmethod.moment.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.widget.Toast;

import com.sogou.inputmethod.moment.App;
import com.sogou.inputmethod.moment.repositories.api.UserWebservice;
import com.sogou.inputmethod.moment.repositories.entity.User;

import java.util.Date;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author yangfeng
 */
public class UserRepository {
    private final UserWebservice webservice;
    private final Executor executor;

    private final MutableLiveData<User> liveUser = new MutableLiveData<>();

    public UserRepository(UserWebservice webservice, Executor executor) {
        this.webservice = webservice;
        this.executor = executor;
    }


    // ---

    public LiveData<User> getUser(String userLogin) {
        refreshUser(userLogin);
        return liveUser;
    }

    // ---

    private void refreshUser(final String userLogin) {
        executor.execute(() -> {
            webservice.getUser(userLogin).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Log.e("TAG", "DATA REFRESHED FROM NETWORK");
                    Toast.makeText(App.context, "Data refreshed from network !", Toast.LENGTH_LONG).show();
                    executor.execute(() -> {
                        User user = response.body();
                        user.setLastRefresh(new Date());
                        liveUser.postValue(user);
                    });
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) { }
            });
        });
    }

}
