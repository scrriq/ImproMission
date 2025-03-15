package com.example.testproject1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log

class smsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == "android.provicer.Telephony.SMS_RECEIVED"){
            val bundle = intent.extras
            if(bundle != null){
                val pdus = bundle["pdus"] as Array<*>
                for(pdu in pdus){
                    val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
                    val sender = smsMessage.originatingAddress
                    val messageBody = smsMessage.messageBody

                    Log.d("SMSReceiver", "От $sender, Текст: $messageBody")

                    val smsIntent = Intent("NEW_SMS_RECEIVED")
                    smsIntent.putExtra("sender", sender)
                    smsIntent.putExtra("message", messageBody)
                    context?.sendBroadcast(smsIntent)
                }
            }
        }
    }

}