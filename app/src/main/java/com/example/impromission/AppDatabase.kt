package com.example.impromission

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.impromission.notification.db.NotificationDao
import com.example.impromission.notification.db.NotificationEntity
import com.example.impromission.tasks.db.TaskDao
import com.example.impromission.tasks.db.TaskEntity


@Database(entities = [TaskEntity::class, NotificationEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {


    abstract fun taskDao() : TaskDao
    abstract fun notificationDao(): NotificationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDb(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "app-database"
            )
                .fallbackToDestructiveMigration() // для разработки (удалить при релизе)
                .build()
                .also { INSTANCE = it }
        }
    }

}