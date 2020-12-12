package com.chennupatibalu.filedrive;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.util.ArrayList;

public class DriveFragment extends Fragment {

    ArrayList<String> al;
    ListView listView;
    String path,location="",symbol = "",second;
    ArrayAdapter<String> arrayAdapter;
    StringBuilder sb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_drive, container, false);
        listView = view1.findViewById(R.id.listView);
        al = new ArrayList<>();
        path = Environment.getExternalStorageDirectory().getAbsolutePath();

        listDir(path);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, al);
        listView.setAdapter(arrayAdapter);

        sb = new StringBuilder(path+"/");
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            try {
                if(adapterView.getItemAtPosition(i)!=null)
                {
                    second = arrayAdapter.getItem(i);
                    sb.append(second);
                    location = sb.toString();
                    listDir(location);
                    listView.setAdapter(arrayAdapter);
                    Toast.makeText(getActivity(), second, Toast.LENGTH_SHORT).show();
                    sb.append("/");
                }
            } catch (Exception e){
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        return view1;
    }
    private void listDir(String path) {
        File file = new File(path);
        File[] f = file.listFiles();
        al.clear();
        for(File file1:f)
            al.add(file1.getName());
    }
}