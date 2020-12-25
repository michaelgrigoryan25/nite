package com.michaelgrigoryan.nite.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        @ColumnInfo(name = "note") val note: String,
        @ColumnInfo(name = "time") val time: String
)
