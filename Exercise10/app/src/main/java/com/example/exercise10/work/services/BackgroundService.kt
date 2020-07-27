package com.example.exercise10.work.services

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.SystemClock
import com.example.exercise10.work.PROGRESS_UPDATE_ACTION
import com.example.exercise10.work.PROGRESS_VALUE_KEY

class BackgroundService : Service() {

    private var isCancelled = false
    private var backgroundThread: Thread? = null
    private val handler =  Handler(Looper.getMainLooper())
    private val runnable = Runnable {
        backgroundThread = object : Thread("Handler_executor_thread") {
            override fun run() {
                doInBackground()
            }
        }
        backgroundThread!!.start()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handler.post(runnable)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        isCancelled = true
        backgroundThread?.interrupt()
    }

    private fun doInBackground() {
        for (i in 0..100) {
            if (isCancelled) { return }
            showProgressNumber(i)
            SystemClock.sleep(100)
        }
    }

    private fun showProgressNumber(progress: Int) {
        val broadcastIntent = Intent(PROGRESS_UPDATE_ACTION)
        broadcastIntent.putExtra(PROGRESS_VALUE_KEY, progress)
        sendBroadcast(broadcastIntent)
    }
}