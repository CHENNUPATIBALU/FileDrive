package com.chennupatibalu.filedrive;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Fragment;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StorageFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_storage, container, false);

        ProgressBar pb = view.findViewById(R.id.storageProgressBar);

        long totalSpace = Environment.getExternalStorageDirectory().getTotalSpace();
        long freeSpace = Environment.getExternalStorageDirectory().getFreeSpace();

        TextView tv = view.findViewById(R.id.storageTextView);
        tv.setText(String.format("Total Space: %d\nFree Space: %d", totalSpace, freeSpace));
        pb.setMax((int)totalSpace);
        pb.setProgress((int)(freeSpace));

        return view;
    }
}