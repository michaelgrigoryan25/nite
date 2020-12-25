package com.michaelgrigoryan.nite.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.michaelgrigoryan.nite.models.Note

@Database(entities = [Note::class], version = 2)
abstract class Database: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
