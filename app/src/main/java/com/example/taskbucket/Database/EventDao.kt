package com.example.databasefinal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.time.DayOfWeek
//import java.time.String

//define the DAO for data access to the table
@Dao
interface EventDao {
    @Query("SELECT * from event_table ORDER BY name ASC")
    fun getAllEvents(): LiveData<List<Event>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(event: Event) : Long

    @Query("DELETE FROM event_table WHERE id = :id ")
    fun deleteOne(id : Int)

    @Query("SELECT * from event_table WHERE year = :year")
    fun getEventsByYear(year : Int): LiveData<List<Event>>
    @Query("SELECT * from event_table WHERE year = NULL")
    fun getEventsNoBucket(): LiveData<List<Event>>
    @Query("SELECT * from event_table WHERE year = :year AND month = :month")
    fun getEventsByMonth(year : Int, month: Int): LiveData<List<Event>>
    @Query("SELECT * from event_table WHERE year = :year AND month = :month AND day = :day")
    fun getEventsByDay(year : Int, month: Int, day : Int): LiveData<List<Event>>
    @Query("SELECT * from event_table WHERE year = :year AND month = :month AND week_number = :week_Int")
    fun getEventsByWeek(year : Int, month: Int, week_Int : Int): LiveData<List<Event>>

    @Query("SELECT * from event_table WHERE project_id != NULL")
    fun getEventsByProject(): LiveData<List<Event>>

    @Query("SELECT * from event_table WHERE year < :year OR (year = :year AND month < :month) OR (year = :year AND month = :month AND day < :day)")
    fun getOld(year: Int, day: Int, month: Int): LiveData<List<Event>>

    @Query("DELETE from event_table WHERE year < :year OR (year = :year AND month < :month) OR (year = :year AND month = :month AND day < :day)")
    fun deleteOld(year: Int, day: Int, month: Int)

    @Query("DELETE from event_table WHERE year = :year")
    fun deleteEventsByYear(year : Int)
    @Query("DELETE from event_table WHERE year = :year AND month = :month")
    fun deleteEventsByMonth(year : Int, month: Int)
    @Query("DELETE from event_table WHERE year = :year AND month = :month AND day = :day")
    fun deleteEventsByDay(year : Int, month: Int, day : Int)
    @Query("DELETE from event_table WHERE year = :year AND month = :month AND week_number = :week_Int")
    fun deleteEventsByWeek(year : Int, month: Int, week_Int : Int)

    @Query("UPDATE event_table SET year = :year AND month = :month AND day = :day AND week_number = :week_Int AND week_day = :week_day WHERE id=:id ")
    fun updateEvent(id: Int, year: Int?, month: Int?, day: Int?, week_Int: Int?, week_day: Int?)

    @Query("SELECT DISTINCT year FROM event_table WHERE year != NULL")
    fun getAllYears() : LiveData<List<Int>>

}