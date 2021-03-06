package com.loopie.uvtfitnesstracker.fragments;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.loopie.uvtfitnesstracker.R;
import com.loopie.uvtfitnesstracker.adapters.ExerciseListAdapter;
import com.loopie.uvtfitnesstracker.adapters.ExerciseRepsListAdapter;
import com.loopie.uvtfitnesstracker.models.Exercise;
import com.loopie.uvtfitnesstracker.models.ExerciseReps;
import com.loopie.uvtfitnesstracker.views.ExerciseRepsViewModel;
import com.loopie.uvtfitnesstracker.views.ExerciseViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SetsRepsFragment extends Fragment {
    private ExerciseRepsViewModel mExerciseRepsViewModel;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.in_exercise_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        MaterialToolbar toolbar = getActivity().findViewById(R.id.topAppBar);
        String exName = arguments.getString("exName");
        toolbar.setTitle(exName);
        long exerciseID = arguments.getLong("exID");
        RecyclerView recyclerView = getActivity().findViewById(R.id.repsRecyclerView);
        final ExerciseRepsListAdapter adapter = new ExerciseRepsListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        mExerciseRepsViewModel = new ViewModelProvider(getActivity()).get(ExerciseRepsViewModel.class);
        mExerciseRepsViewModel.getFilteredExerciseReps(exerciseID, today.getTime()).observe(getViewLifecycleOwner(), new Observer<List<ExerciseReps>>() {
            @Override
            public void onChanged(@Nullable final List<ExerciseReps> exerciseReps) {
                // Update the cached copy of the words in the adapter.
                adapter.setExerciseReps(exerciseReps);
            }
        });

        MaterialButton addRep = view.findViewById(R.id.addRep);
        MaterialButton subtractRep = view.findViewById(R.id.subtractRep);
        MaterialButton addWeight = view.findViewById(R.id.addWeight);
        MaterialButton subtractWeight = view.findViewById(R.id.subtractWeight);
        TextInputEditText reps = view.findViewById(R.id.Reps);
        TextInputEditText weight = view.findViewById(R.id.weight);
        MaterialButton finishSetBtn = view.findViewById(R.id.finishSetBtn);
        MaterialButton historyExerciseBtn = view.findViewById(R.id.historyExerciseBtn);
        addRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String repsText = reps.getText().toString();
                if (repsText.matches("")) {
                    reps.setText("1");
                } else {
                    int repsCount = Integer.parseInt(repsText) + 1;
                    reps.setText(String.valueOf(repsCount));
                }
            }
        });
        subtractRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String repsText = reps.getText().toString();
                if (repsText.matches("")) {
                    reps.setText("0");
                } else {
                    int repsCount = Integer.parseInt(repsText);
                    if (Integer.parseInt(repsText) > 0) {
                        reps.setText(String.valueOf(repsCount - 1));
                    }
                }
            }
        });
        addWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String weightText = weight.getText().toString();
                if (weightText.matches("")) {
                    weight.setText("2.5");
                } else {
                    double weightCount = Double.parseDouble(weightText) + 2.5;
                    weight.setText(String.valueOf(weightCount));
                }
            }
        });
        subtractWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String weightText = weight.getText().toString();
                if (weightText.matches("")) {
                    weight.setText("0");
                } else {
                    double weightCount = Double.parseDouble(weightText) - 2.5;
                    if (Double.parseDouble(weightText) > 0) {
                        weight.setText(String.valueOf(weightCount));
                    }
                }
            }
        });
        finishSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String repCount = reps.getText().toString();
                String weightCount = weight.getText().toString();
                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY, 0);
                today.set(Calendar.MINUTE, 0);
                today.set(Calendar.SECOND, 0);
                today.set(Calendar.MILLISECOND, 0);
                ExerciseReps newSet = new ExerciseReps(exerciseID, repCount, weightCount, today.getTime());
                mExerciseRepsViewModel.insert(newSet);
            }
        });
        historyExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HistoryRepsFragment();
                Bundle arguments = new Bundle();
                arguments.putLong("exID", exerciseID);
                arguments.putString("exName", exName);
                fragment.setArguments(arguments);
                FragmentManager fragmentManager = ((FragmentActivity) getView().getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_main, fragment)
                        .addToBackStack("tag")
                        .commit();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}