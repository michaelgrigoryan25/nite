package com.michaelgrigoryan.nite.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.michaelgrigoryan.nite.models.Note

@Database(entities = [Note::class], version = 5)
abstract class Database: RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: com.michaelgrigoryan.nite.database.Database? = null

        fun setup(context: Context): com.michaelgrigoryan.nite.database.Database {
            val TEMP = INSTANCE
            if (TEMP != null) return TEMP

            synchronized(this) {
                val NEW = Room.databaseBuilder(
                    context.applicationContext,
                    com.michaelgrigoryan.nite.database.Database::class.java,
                    "nite-db"
                )
                    // This line will be replaced if gone to production
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = NEW
                return NEW
            }
        }
    }
}