package com.android.resturant.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.resturant.Fragments.DrinkFragment;
import com.android.resturant.Fragments.EntreesFragment;
import com.android.resturant.Fragments.HomeFragment;
import com.android.resturant.Fragments.MealsFragment;
import com.android.resturant.Fragments.SandwicheFragment;
import com.android.resturant.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {


    private Toolbar tbr;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("Resturant", MODE_PRIVATE);
        spEditor = sp.edit();

        tbr = findViewById(R.id.toolbar_main);
        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
        }
        toggle = new ActionBarDrawerToggle(
                this, drawer, tbr, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        tbr.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(tbr);
        setNavigationItemSelectedListener();
    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.menu_cart) {
            startActivity(new Intent(this, CartActivity.class));
        }
        return false;
    }

    private void setNavigationItemSelectedListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.nav_Home_fragment:
                        replaceFragment(new HomeFragment());
                        break;
                    case R.id.nav_Meals_fragment:
                        replaceFragment(new MealsFragment());
                        break;
                    case R.id.nav_sandwiche_fragment:
                        replaceFragment(new SandwicheFragment());
                        break;
                    case R.id.nav_drink_fragment:
                        replaceFragment(new DrinkFragment());
                        break;
                    case R.id.nav_entrees_fragment:
                        replaceFragment(new EntreesFragment());
                        break;
                    case R.id.nav_profile:
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        break;
                    case R.id.nav_logout:
                        spEditor.putString("token", "-1");
                        spEditor.apply();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}