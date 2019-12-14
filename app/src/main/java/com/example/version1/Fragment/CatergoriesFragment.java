package com.example.version1.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.version1.Activity.AcademicDissertations;
import com.example.version1.Activity.Electronicbooks;
import com.example.version1.R;

public class CatergoriesFragment extends Fragment {
    private  Button electronicbooks;
    private Button academicDissertation;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_categories, container, false);
        electronicbooks=root.findViewById(R.id.electronicbooks);
        academicDissertation=root.findViewById(R.id.academicDissertation);
        return root;
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        electronicbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), Electronicbooks.class);
                startActivity(intent);
            }
        });
        Intent intent1;
        academicDissertation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), AcademicDissertations.class);
                startActivity(intent);
            }
        });

    }





}