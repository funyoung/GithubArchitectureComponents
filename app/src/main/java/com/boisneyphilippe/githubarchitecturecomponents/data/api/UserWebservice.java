package com.boisneyphilippe.githubarchitecturecomponents.data.api;

import com.boisneyphilippe.githubarchitecturecomponents.data.database.entity.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Philippe on 02/03/2018.
 */

public interface UserWebservice {
    @GET("/users/{user}")
    Call<User> getUser(@Path("user") String userId);
}
