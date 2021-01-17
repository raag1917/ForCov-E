package com.raag.forcove.google

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService: FirebaseMessagingService(){

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Looper.prepare()

        Handler(Looper.getMainLooper()).post{
            Toast.makeText(baseContext, remoteMessage.notification?.title, Toast.LENGTH_LONG).show()
        }
        Looper.loop()
    }

}