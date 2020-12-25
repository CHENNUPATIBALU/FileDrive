package com.chennupatibalu.filedrive;

import android.Manifest;
import android.app.Activity;
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
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        View view1 = inflater.inflate(R.layout.fragment_drive, container, false);
        listView = view1.findViewById(R.id.listView);
        al = new ArrayList<>();

        //Requesting Permissions
        writeStoragePermission();   // Edit/Modify
        readStoragePermission();    // Read

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
                openFile(new File(sb.toString()));
            }
        });
        FloatingActionButton fab = view1.findViewById(R.id.fab);

        fab.setOnClickListener(view -> shareIntent());

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

    private void openFile(File file)
    {
        try {

            Uri uri = FileProvider.getUriForFile(getActivity().getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider",file);;

            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (file.toString().contains(".doc") || file.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (file.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (file.toString().contains(".ppt") || file.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (file.toString().contains(".xls") || file.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (file.toString().contains(".zip")) {
                // ZIP file
                intent.setDataAndType(uri, "application/zip");
            } else if (file.toString().contains(".rar")){
                // RAR file
                intent.setDataAndType(uri, "application/x-rar-compressed");
            } else if (file.toString().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if (file.toString().contains(".wav") || file.toString().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if (file.toString().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if (file.toString().contains(".jpg") || file.toString().contains(".jpeg") || file.toString().contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if (file.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if (file.toString().contains(".3gp") || file.toString().contains(".mpg") ||
                    file.toString().contains(".mpeg") || file.toString().contains(".mpe") || file.toString().contains(".mp4") || file.toString().contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                intent.setDataAndType(uri, "*/*");
            }

            intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "No application found which can open the file", Toast.LENGTH_SHORT).show();
        }
    }
    public void writeStoragePermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                Toast.makeText(getActivity(),"Storage Write Permission granted",Toast.LENGTH_SHORT).show();
            else
            {
                Toast.makeText(getActivity(), "Permission not Granted", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},3);
            }
        }
        else
            Toast.makeText(getActivity(),"Storage Read Permission granted",Toast.LENGTH_SHORT).show();
    }
    public void readStoragePermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                Toast.makeText(getActivity(),"Storage Permission granted",Toast.LENGTH_SHORT).show();
            else
            {
                Toast.makeText(getActivity(), "Permission not Granted", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
            }
        }
        else
            Toast.makeText(getActivity(),"Storage Permission granted",Toast.LENGTH_SHORT).show();
    }
}