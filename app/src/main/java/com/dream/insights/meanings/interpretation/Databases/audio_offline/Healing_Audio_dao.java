package com.dream.insights.meanings.interpretation.Databases.audio_offline;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface Healing_Audio_dao {


    @Query("SELECT CASE WHEN EXISTS (SELECT 1 FROM healing_audio WHERE healing_id = :healingId) THEN 1 ELSE 0 END")
    boolean doesHealingAudioExist(int healingId);

    @Query("SELECT path FROM healing_audio WHERE healing_id = :healingId")
    String getPathByHealingId(int healingId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Healing_Audio item);

    @Delete
    void delete(Healing_Audio item);

    @Update
    void update(Healing_Audio item);
}
