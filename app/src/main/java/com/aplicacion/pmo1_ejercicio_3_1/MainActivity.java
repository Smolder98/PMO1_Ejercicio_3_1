package com.aplicacion.pmo1_ejercicio_3_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.aplicacion.pmo1_ejercicio_3_1.Fragments.AddFragment;
import com.aplicacion.pmo1_ejercicio_3_1.Fragments.EditFragment;
import com.aplicacion.pmo1_ejercicio_3_1.Fragments.ListFragment;
import com.aplicacion.pmo1_ejercicio_3_1.Utils.MethodUtils;
import com.aplicacion.pmo1_ejercicio_3_1.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();
        setListeners();
    }

    private void init(){

        bottomNavigationView = binding.bottomNavigationView;
        showSelectedFragment(new ListFragment());
    }

    private void setListeners(){


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()){

                    case R.id.menu_list:
                            showSelectedFragment(new ListFragment());
                        break;

                    case R.id.menu_add:
                            showSelectedFragment(new AddFragment());
                        break;

                    case R.id.menu_edit:
                            showSelectedFragment(new EditFragment());
                        break;

                    default:
                        break;
                }

                return true;
            }
        });
    }

    private void showSelectedFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}