package com.chennupatibalu.filedrive;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.media.audiofx.EnvironmentalReverb;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.chennupatibalu.filedrive.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isExternalStorageWritable())
        {
            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
            ViewPager viewPager = findViewById(R.id.view_pager);
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = findViewById(R.id.tabs);
            tabs.setupWithViewPager(viewPager);

            loadFragment(new DriveFragment());

            tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.newFolderMenu:
                newFolder();
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
    protected void newFolder()
    {

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
    public boolean isExternalStorageWritable()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
            {
                return true;
            }
            else
            {
                Toast.makeText(this, "Permission not Granted", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},3);
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
                return true;
            }
        }
        else
        {
            return true;
        }
    }
}