package com.daclink.gymlog.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.daclink.gymlog.database.entities.GymLog;


import java.util.List;


@Dao
public interface GymLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GymLog gymLog);

    @Query("SELECT * from " + GymLogDatabase.GYM_LOG_TABLE)
    List<GymLog> getAllRecords();
}
