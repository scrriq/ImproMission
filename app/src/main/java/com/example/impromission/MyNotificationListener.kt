package com.example.impromission

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyNotificationListener : NotificationListenerService() {

    private val dao by lazy {
        NotificationDatabase.getDb(applicationContext).notificationDao()
    }


    // Отслеживание push уведомлений
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val packageName = sbn.packageName
        val extras = sbn.notification.extras

        val entity = NotificationEntity(
            packageName = sbn.packageName,
            title = extras.getString(Notification.EXTRA_TITLE, "Нет заголовка"),
            text = extras.getString(Notification.EXTRA_TEXT, "Нет текста"),
            bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString() ?: "Нет big Text",
            infoText = extras.getCharSequence(Notification.EXTRA_INFO_TEXT)?.toString() ?: "Нет info Text",
            subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT)?.toString() ?: "Нет Sub Text"
        )

        // Запускаем вставку в фоновом потоке,
        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(entity)
        }





//        Log.d("NotificationListener", "Extras: ${extras.keySet()}")
//
//        // Основной текст Notification
//        val title = extras.getString(Notification.EXTRA_TITLE, "Нет заголовка")
//        val text = extras.getString(Notification.EXTRA_TEXT, "Нет текста")
//
//        // Развернутый текст notification
//        val bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString() ?:"Нет bigText"
//        val infoText = extras.getCharSequence(Notification.EXTRA_INFO_TEXT)?.toString() ?:"Нет infoText"
//        val subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT)?.toString() ?:"Нет subText"
//
//        Log.d("NotificationInfo", "Приложение: $packageName," +
//                " Заголовок: $title, Текст: $text, BigText: $bigText, InfoText: $infoText ,SubText: $subText")
//
//
//        Log.d("NotificationInfo1", "From: $packageName, Title: $title, Text: $text")
    }

    // Отслеживание удаленных push
    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        Log.d("NotificationRemoved", "Removed: ${sbn.packageName}")
    }

    // Проверка работы сервиса
    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d("NotificationListenerInfo", "Сервис запущен и работает")
    }
}