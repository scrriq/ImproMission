package com.example.impromission

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [NotificationEntity::class], version = 1)
abstract class NotificationDatabase : RoomDatabase() {

    abstract fun notificationDao(): NotificationDao

    companion object{
        @Volatile private var INSTANCE: NotificationDatabase? = null

        fun getDb(context: Context) : NotificationDatabase = Companion.INSTANCE?: synchronized(this){
            return Room.databaseBuilder(
                context,
                NotificationDatabase::class.java,
                "notifications-db"
            ).build().also { INSTANCE = it }

        }
    }

}