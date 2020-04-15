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
    val month: Int? = null,
    val day: Int? = null,
    @NonNull val year: Int,
    val start: Int? = null,
    val end: Int? = null,
    val description: String? = null,
    val week_day: Int? = null,
    val week_number: Int? = null, // 1 for week 1 (1-7), 2 for week 2 (8-14), etc.
    val project_id: Int? = null
) {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0
}