package com.michaelgrigoryan.nite.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.michaelgrigoryan.nite.models.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): List<Note>

    @Insert
    fun createNote(vararg note: Note)
}