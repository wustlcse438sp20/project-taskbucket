package com.example.databasefinal

import androidx.annotation.NonNull
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

    @Query("SELECT * from event_table WHERE (year = :year AND month = :month AND day = :day) OR (year = :year2 AND month = :month2 AND day=:day2) OR (year = :year3 AND month= :month3 AND day = :day3) ")
    fun getEventsByThreeDay(year : Int, month: Int, day : Int,
                            year2: Int, month2: Int, day2: Int,
                            year3:Int, month3:Int, day3:Int): LiveData<List<Event>>

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

    @Query("UPDATE event_table SET year = :year, month = :month, day = :day, week_number = :week_number , week_day = :week_day , name = :name , start = :start , `end` = :end , description = :description , week_day = :week_day , project_id = :project_id WHERE id=:id ")
    fun updateEvent(id: Int, name: String,
                    month: Int?,
                    day: Int?,
                    year: Int,
                    start: Int?,
                    end: Int?,
                    description: String?,
                    week_day: Int?,
                    week_number: Int?, // 1 for week 1 (1-7), 2 for week 2 (8-14), etc.
                    project_id: Int?)

    @Query("SELECT DISTINCT year FROM event_table WHERE year != NULL")
    fun getAllYears() : LiveData<List<Int>>

    @Query("SELECT * FROM event_table WHERE id = :id")
    fun getOneEvent(id: Int) : LiveData<List<Event>>


    // Project table
    @Query("SELECT * from project_table ORDER BY name ASC")
    fun getAllProjects(): LiveData<List<Project>>
    @Query("SELECT * FROM project_table WHERE id = :id")
    fun getOneProject(id: Int) : LiveData<List<Project>>

    @Query("SELECT MAX(id) from project_table")
    fun getLastId() : LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProject(project: Project) : Long
    @Query("DELETE FROM project_table WHERE id = :id ")
    fun deleteOneProject(id : Int)
}