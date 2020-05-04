package com.boisneyphilippe.githubarchitecturecomponents.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.boisneyphilippe.githubarchitecturecomponents.R;
import com.boisneyphilippe.githubarchitecturecomponents.fragments.UserProfileFragment;

public class MainActivity extends AppCompatActivity {
    private static String USER_LOGIN = "JakeWharton";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.showFragment(savedInstanceState);
    }

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
}
