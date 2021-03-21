package com.michaelgrigoryan.nite.database

import androidx.room.*
import com.michaelgrigoryan.nite.models.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): MutableList<Note>

    @Insert
    fun createNote(vararg note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}