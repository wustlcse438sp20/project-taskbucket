package com.example.databasefinal

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.Month

//create the sql table
@Entity(tableName = "event_table")
data class Event(
    //@PrimaryKey(autoGenerate = true) @NonNull val id: Number,
    @NonNull val name: String,
    val month: Month?,
    val day: Number?,
    @NonNull val year: Number,
    val start: Number?,
    val end: Number?,
    val description: String?,
    val week_day: DayOfWeek?,
    val week_number: Number? // 1 for week 1 (1-7), 2 for week 2 (8-14), etc.
) {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0
}