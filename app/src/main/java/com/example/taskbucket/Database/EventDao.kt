package com.example.databasefinal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.time.Month

//define the DAO for data access to the table
@Dao
interface EventDao {
    @Query("SELECT * from event_table ORDER BY name ASC")
    fun getAllEvents(): LiveData<List<Event>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(event: Event)

    @Query("DELETE FROM event_table WHERE id = :id ")
    fun deleteOne(id : Number)

    @Query("SELECT * from event_table WHERE year = :year")
    fun getEventsByYear(year : Number): LiveData<List<Event>>
    @Query("SELECT * from event_table WHERE year = :year AND month = :month")
    fun getEventsByMonth(year : Number, month : Month): LiveData<List<Event>>
    @Query("SELECT * from event_table WHERE year = :year AND month = :month AND day = :day")
    fun getEventsByDay(year : Number, month : Month, day : Number): LiveData<List<Event>>
    @Query("SELECT * from event_table WHERE year = :year AND month = :month AND week_number = :week_number")
    fun getEventsByWeek(year : Number, month : Month, week_number : Number): LiveData<List<Event>>

    @Query("SELECT * from event_table WHERE year < :year OR (year = :year AND month < :month) OR (year = :year AND month = :month AND day < :day)")
    fun getOld(year: Number, day: Number, month: Month): LiveData<List<Event>>

    @Query("DELETE from event_table WHERE year < :year OR (year = :year AND month < :month) OR (year = :year AND month = :month AND day < :day)")
    fun deleteOld(year: Number, day: Number, month: Month)

    @Query("DELETE from event_table WHERE year = :year")
    fun deleteEventsByYear(year : Number)
    @Query("DELETE from event_table WHERE year = :year AND month = :month")
    fun deleteEventsByMonth(year : Number, month : Month)
    @Query("DELETE from event_table WHERE year = :year AND month = :month AND day = :day")
    fun deleteEventsByDay(year : Number, month : Month, day : Number)
    @Query("DELETE from event_table WHERE year = :year AND month = :month AND week_number = :week_number")
    fun deleteEventsByWeek(year : Number, month : Month, week_number : Number)

}