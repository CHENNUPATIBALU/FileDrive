package com.chennupatibalu.filedrive;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DriveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DriveFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<String> al;
    ListView listView;
    String path,location="",symbol = "";
    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, al);

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DriveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DriveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DriveFragment newInstance(String param1, String param2) {
        DriveFragment fragment = new DriveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drive, container, false);

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
                    StringBuilder sb = new StringBuilder(path+"/");
                    String second = arrayAdapter.getItem(i);
                    sb.append(second);
                    location = sb.toString();
                    listDir(location);
                    listView.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), location, Toast.LENGTH_SHORT).show();
                    sb.append("/");
                }
            } catch (Exception e){
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}