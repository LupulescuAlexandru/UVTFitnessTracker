package com.loopie.uvtfitnesstracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.loopie.uvtfitnesstracker.models.ExerciseProgramsMany;
import com.loopie.uvtfitnesstracker.models.ProgramsWithExercises;

import java.util.List;

@Dao
public interface ExerciseProgramsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ExerciseProgramsMany exerciseprograms);

    @Transaction
    @Query("SELECT exercise_table.name from exercise_table JOIN exercise_programs_table ON exercise_table.id_exercise = exercise_programs_table.id_exercise")
    LiveData<List<String>> getProgramsWithExercises();
}