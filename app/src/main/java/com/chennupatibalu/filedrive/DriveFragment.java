package com.chennupatibalu.filedrive;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

public class DriveFragment extends Fragment {

    ArrayList<String> al;
    ListView listView;
    String path;
    String location="";
    String second;
    ArrayAdapter<String> arrayAdapter;
    StringBuilder sb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1 = null;
        if(isExternalStorageReadable() && isExternalStorageWritable())
        {
            // Inflate the layout for this fragment
            view1 = inflater.inflate(R.layout.fragment_drive, container, false);
            setHasOptionsMenu(true);
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
                    try
                    {
                        openFile(new File(sb.substring(0,sb.length()-1)));
                    }
                    catch(Exception exception)
                    {
                        Toast.makeText(getActivity(), "Error Opening this file", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return view1;
    }
    private void listDir(String path) {
        File file = new File(path);
        File[] f = file.listFiles();
        al.clear();
        for(File file1:f)
            al.add(file1.getName());
    }
    public void newFolder()
    {
        String folderPath = sb.toString();
        EditText folderName = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_message);
        folderName.setLayoutParams(lp);
        builder.setView(folderName);
        builder.setTitle(R.string.dialog_title);

        builder.setPositiveButton(R.string.ok, (dialogInterface, i) -> {
            File folder = new File(folderPath+"/"+folderName.getText().toString());
            boolean success = true;

            if(!folder.exists()) {
                success = folder.mkdirs();
            }

            if(success) {
                Toast.makeText(getActivity(), "Folder created successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Folder already exists", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> Toast.makeText(getActivity(), "Cancel Clicked", Toast.LENGTH_SHORT).show());

        AlertDialog dialog = builder.create();

        dialog.setCancelable(false);
        dialog.show();
    }
    private void openFile(File file)
    {
        String extension = getExtension(file.getAbsolutePath());
        if(file.exists())
        {
            try
            {
                Intent fileIntent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.fromFile(file);
                fileIntent.setDataAndType(uri,"application/"+extension);
                fileIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(fileIntent);
            }
            catch(ActivityNotFoundException exception)
            {
                Toast.makeText(getActivity(), "No Application found to Open this file", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getExtension(@NotNull String path) {
        return path.substring(path.lastIndexOf('.')).toLowerCase();
    }

    public boolean isExternalStorageWritable()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(getActivity(),android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
            {
                return true;
            }
            else
            {
                Toast.makeText(getActivity(), "Permission not Granted", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},3);
                return true;
            }
        }
        else
        {
            return true;
        }
    }
    public boolean isExternalStorageReadable()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(getActivity(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
            {
                return true;
            }
            else
            {
                Toast.makeText(getActivity(), "Permission not Granted", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
                return true;
            }
        }
        else
        {
            return true;
        }
    }
}