package com.boisneyphilippe.githubarchitecturecomponents.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.boisneyphilippe.githubarchitecturecomponents.R;
import com.boisneyphilippe.githubarchitecturecomponents.fragments.UserProfileFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class MainActivity extends AppCompatActivity implements HasAndroidInjector {

    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    private static String USER_LOGIN = "JakeWharton";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureDagger();
        this.showFragment(savedInstanceState);
    }


    // ---

    private void showFragment(Bundle savedInstanceState){
        if (savedInstanceState == null) {

            UserProfileFragment fragment = new UserProfileFragment();

            Bundle bundle = new Bundle();
            bundle.putString(UserProfileFragment.UID_KEY, USER_LOGIN);
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, null)
                    .commit();
        }
    }

    private void configureDagger(){
        AndroidInjection.inject(this);
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }
}
