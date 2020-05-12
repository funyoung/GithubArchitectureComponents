package com.boisneyphilippe.githubarchitecturecomponents.ui.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.boisneyphilippe.githubarchitecturecomponents.repositories.entity.User;
import com.boisneyphilippe.githubarchitecturecomponents.repositories.UserRepository;

/**
 * @author yangfeng
 */

public class UserProfileViewModel extends ViewModel {

    private LiveData<User> user;
    private UserRepository userRepo;

    public UserProfileViewModel(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // ----

    public void init(String userId) {
        if (this.user != null) {
            return;
        }
        user = userRepo.getUser(userId);
    }

    public LiveData<User> getUser() {
        return this.user;
    }
}
