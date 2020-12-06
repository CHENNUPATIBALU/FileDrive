package com.chennupatibalu.filedrive;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.os.storage.StorageManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.chennupatibalu.filedrive.ui.main.SectionsPagerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DriveActivity extends AppCompatActivity {

    ArrayList<String> al;
    ListView listView;
    String path,location="";
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive);

        listView = findViewById(R.id.listView);
        al = new ArrayList<>();

        path = Environment.getExternalStorageDirectory().getPath();

        listDir(path);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> shareIntent());
        addFolders();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, al);
        listView.setAdapter(arrayAdapter);
    }

    private void listDir(String path) {
        File file = new File(path);
        File[] f = file.listFiles();
        al.clear();
        for(File file1:f)
            al.add(file1.getName());
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
    protected void addFolders()
    {
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            try {
                if(adapterView.getItemAtPosition(i)!=null)
                {
                    location = path+"/"+arrayAdapter.getItem(i)+"/"+location;
                    listDir(location);
                    listView.setAdapter(arrayAdapter);
                    Toast.makeText(this, location, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}