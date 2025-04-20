package com.example.impromission

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    @ColumnInfo(name = "packageName") подумать нужны ли тут Column Info
    val packageName: String,
    val title: String,
    val text: String,
    val bigText: String,
    val infoText: String,
    val subText: String,
//    val timestamp: Date = Date()
)

