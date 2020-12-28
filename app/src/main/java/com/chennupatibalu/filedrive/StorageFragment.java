package com.chennupatibalu.filedrive;

/*
 *
 *  Copyright (c) 2020 Chennupati Balu.
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */

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