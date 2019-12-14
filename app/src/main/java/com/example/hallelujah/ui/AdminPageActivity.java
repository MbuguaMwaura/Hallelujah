package com.example.hallelujah.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.hallelujah.R;
import com.example.hallelujah.ui.AdminPackage.ChurchFragment;
import com.example.hallelujah.ui.AdminPackage.FinanceFragment;

public class AdminPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        setFragment( new ChurchFragment());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavBarAdmin);
        final ChurchFragment churchFragment = new ChurchFragment();
        final NotesFragment notesFragment = new NotesFragment();
        final FinanceFragment financeFragment  = new FinanceFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.createChurch){
                    setFragment(churchFragment);
                    return true;
                }
                if (id == R.id.adminNotes){
                    setFragment(notesFragment);
                    return true;
                }
                if (id == R.id.financials){
                    setFragment(financeFragment);
                    return true;
                }
                return false;
            }
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameAdmin,fragment);
        fragmentTransaction.commit();
    }
}
