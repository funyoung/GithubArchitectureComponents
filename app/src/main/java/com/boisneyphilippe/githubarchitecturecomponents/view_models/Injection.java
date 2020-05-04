package com.boisneyphilippe.githubarchitecturecomponents.view_models;

import android.arch.lifecycle.ViewModelProvider;

public class Injection {
    public static ViewModelProvider.Factory injectUserProfileViewModelFactory() {
        return new UserProfileViewModelFactory();
    }
}
