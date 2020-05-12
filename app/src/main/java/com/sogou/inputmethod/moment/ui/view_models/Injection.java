package com.sogou.inputmethod.moment.ui.view_models;

import android.arch.lifecycle.ViewModelProvider;

/**
 * @author yangfeng
 */
public class Injection {
    public static ViewModelProvider.Factory injectUserProfileViewModelFactory() {
        return new UserProfileViewModelFactory();
    }
}
