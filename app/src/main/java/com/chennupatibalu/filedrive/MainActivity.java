package com.chennupatibalu.filedrive;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.chennupatibalu.filedrive.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        loadFragment(new DriveFragment());

            tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if(tab.getText().equals("Drive"))
                    {
                        loadFragment(new DriveFragment());
                    }
                    if(tab.getText().equals("Storage"))
                    {
                        loadFragment(new StorageFragment());
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    if(tab.getText().equals("Drive"))
                    {
                        loadFragment(new DriveFragment());
                    }
                    if(tab.getText().equals("Storage"))
                    {
                        loadFragment(new StorageFragment());
                    }
                }
            });

            FloatingActionButton fab = findViewById(R.id.fab);

            fab.setOnClickListener(view -> shareIntent());
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.newFolderMenu:
                new DriveFragment().newFolder();
                Toast.makeText(this, "New Folder Clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.aboutMenu:
                about();
                Toast.makeText(this, "About Clicked", Toast.LENGTH_SHORT).show();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainActivityConstraintLayout,fragment);
        fragmentTransaction.commit();
    }
    protected void shareIntent()
    {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "This is an app that fetches your files and folders from your Phone";
        String shareSub = "FileDrive";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    protected void about()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage(R.string.dialog_message);
        builder.setTitle(R.string.dialog_title);

        builder.setPositiveButton(R.string.ok, (dialogInterface, i) -> Toast.makeText(MainActivity.this, "Ok Clicked", Toast.LENGTH_SHORT).show());
        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> Toast.makeText(MainActivity.this, "Cancel Clicked", Toast.LENGTH_SHORT).show());

        AlertDialog dialog = builder.create();

        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}