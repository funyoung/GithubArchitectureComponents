package com.sogou.inputmethod.moment.repositories.api;

import com.sogou.inputmethod.moment.repositories.entity.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author yangfeng
 */

public interface UserWebservice {
    @GET("/users/{user}")
    Call<User> getUser(@Path("user") String userId);
}
