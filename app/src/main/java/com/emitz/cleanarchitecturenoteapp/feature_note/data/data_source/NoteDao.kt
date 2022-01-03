package com.emitz.cleanarchitecturenoteapp.feature_note.data.data_source

import androidx.room.*
import com.emitz.cleanarchitecturenoteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

/*
* Data Access Object is used to interact with Database using Query
* it needs to be either an interface or abstract class
* It is recommended to have multiple DAO class depending on table they touch */

@Dao
interface NoteDao {

    @Query("Select * from note")
    fun getNote():Flow<List<Note>>

    @Query("Select * from note where :id")
    suspend fun getNoteById(id:Int):Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note:Note)

    @Delete
    suspend fun deleteNote(note:Note)
}