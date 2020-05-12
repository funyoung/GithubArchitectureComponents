package com.boisneyphilippe.githubarchitecturecomponents.repositories.api;

import com.boisneyphilippe.githubarchitecturecomponents.repositories.entity.User;

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
