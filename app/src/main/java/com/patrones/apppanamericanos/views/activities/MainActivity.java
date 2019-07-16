package com.patrones.apppanamericanos.views.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.patrones.apppanamericanos.utils.viewtemplates.MyActivity;
import com.patrones.apppanamericanos.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

public class MainActivity extends MyActivity {

    private NavController navController;
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        navView = findViewById(R.id.nav_view);

        NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        navController = host.getNavController();

        navController.addOnDestinationChangedListener(mOnDestinationChangedListener);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Obtiene login


    }

    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            navigateTo(item.getItemId());
            return false;
        }
    };

    private NavController.OnDestinationChangedListener mOnDestinationChangedListener = new NavController.OnDestinationChangedListener() {
        @Override
        public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
            switch (destination.getId()) {
                case R.id.events_dest:
                    navView.getMenu().findItem(R.id.events_tab).setChecked(true);
                    break;
                case R.id.profile_dest:
                    navView.getMenu().findItem(R.id.profile_tab).setChecked(true);
                    break;
                case R.id.help_dest:
                    navView.getMenu().findItem(R.id.help_tab).setChecked(true);
                    break;
            }
        }
    };


    public void navigateTo(int itemId) {
        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.events_dest, (itemId == R.id.events_tab) ? true : false)
                .setEnterAnim(R.anim.fade_in)
                .setExitAnim(R.anim.fade_out)
                .setPopEnterAnim(R.anim.fade_in)
                .setPopExitAnim(R.anim.fade_out)
                .build();

        int dest = -1;
        switch (itemId) {
            case R.id.profile_tab:
                dest = R.id.profile_dest;
                break;
            case R.id.events_tab:
                dest = R.id.events_dest;
                break;
            case R.id.help_tab:
                dest = R.id.help_dest;
                break;
        }
        if (dest != -1) {
            navController.navigate(dest, null, navOptions);
        }

    }
}