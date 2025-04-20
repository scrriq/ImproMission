package com.example.impromission

import android.Manifest
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.impromission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bindingClass: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        redirectionToSettings()
        restartNotificationListener(this)
        val filter = IntentFilter("NEW_SMS_RECEIVED")
        registerReceiver(smsReceiver, filter)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeHolder, NotificationListFragment.newInstance())
            .commit()
    }

    private val smsReceiver = object: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?){
            val sender = intent?.getStringExtra("sender") ?: "Неизвестный"
            val message = intent?.getStringExtra("message") ?: "Нет текста"
            Log.d("MainActivityMessage", "Новосое сообщение от $sender, текст: $message")
        }
    }

    // перезапуск процесса
    private fun restartNotificationListener(context: Context) {
        val componentName = ComponentName(context, MyNotificationListener::class.java)
        val pm = context.packageManager
        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)
        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)
    }


    // проверка доступов к рахным системам Android
    private fun redirectionToSettings(){
        if(!isNotificationAccessEnabled(this)){
            val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            startActivity(intent)
        }else{
            Toast.makeText(this, "Предоставлен доступ к уведомлениям", Toast.LENGTH_SHORT).show()
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS), 1)
        }else{
            Toast.makeText(this, "Предосталвен доступ к SMS ", Toast.LENGTH_SHORT).show()
        }
    }

    // Функция проверки включенного разрешения к SMS сообещниям
    private fun isNotificationAccessEnabled(context: Context) : Boolean{
        val pkgName = context.packageName
        val enabledListeners = Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
        return enabledListeners?.contains(pkgName) == true
    }

    //Отправление тоста в случае получения доступа к SMS сообщениям
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Разрешение получено!", Toast.LENGTH_SHORT).show()
        }
    }
}