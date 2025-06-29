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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.impromission.databinding.ActivityMainBinding
import com.example.impromission.notification.MyNotificationListener
import com.example.impromission.notification.NotificationListFragment
import com.example.impromission.tasks.TasksFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        redirectionToSettings()
        restartNotificationListener(this)
//        val filter = IntentFilter("NEW_SMS_RECEIVED")
//        registerReceiver(smsReceiver, filter)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.navController

        // Привязываем BottomNavigationView к NavController
        binding.bNav.setupWithNavController(navController)

        binding.bNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.item1 -> {
                    navController.navigate(R.id.tasksFragment)
                }

                R.id.item3 -> {
                    navController.navigate(R.id.notificationsFragment)
                }
                R.id.item4->{
                    navController.navigate(R.id.mainPageAccount)
                }
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
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