package com.project.pillrem;
/**
 * Created by Sulthan Nizarudin on 06-12-2020.
 */
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView contactView = getView().findViewById(R.id.medlist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        contactView.setLayoutManager(linearLayoutManager);
        contactView.setHasFixedSize(true);
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<Medicine> allmed = db.getAllMedicine();
        if (allmed.size() > 0) {
            contactView.setVisibility(View.VISIBLE);
            MedicineAdapter mAdapter = new MedicineAdapter(getActivity(), allmed);
            contactView.setAdapter(mAdapter);
        }
        else {
            contactView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "You have added no medicines. Add them by clicking on '+'", Toast.LENGTH_LONG).show();
        }

        FloatingActionButton fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });


    }
}