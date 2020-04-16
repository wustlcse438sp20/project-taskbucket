package com.example.databasefinal

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context


// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Event::class), version = 2, exportSchema = false)
abstract class EventDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: EventDatabase? = null

        fun getDatabase(context: Context): EventDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventDatabase::class.java,
                    "event_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}