package com.loopie.uvtfitnesstracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.loopie.uvtfitnesstracker.models.Exercise;
import com.loopie.uvtfitnesstracker.models.ExerciseReps;

import java.util.List;

@Dao
public interface ExerciseRepsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ExerciseReps exercisereps);

    @Query("SELECT * from exercise_reps_table WHERE ex_id=:exerciseID")
    LiveData<List<ExerciseReps>> getFilteredExerciseReps(long exerciseID);

    @Query("SELECT * from exercise_reps_table")
    LiveData<List<ExerciseReps>> getExerciseReps();
}