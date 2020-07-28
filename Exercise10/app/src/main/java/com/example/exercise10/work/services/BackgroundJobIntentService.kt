package com.example.exercise10.work.services

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.example.exercise10.work.JOB_ID
import com.example.exercise10.work.MAX_VALUE
import com.example.exercise10.work.PROGRESS_KEY_INTENT
import com.example.exercise10.work.PROGRESS_UPDATE_INTENT

class BackgroundJobIntentService : JobIntentService() {

    companion object {
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, BackgroundJobIntentService::class.java, JOB_ID, intent)
        }
    }

    override fun onHandleWork(intent: Intent) {
        for (i in 0..MAX_VALUE) {
            showProgressNumber(i)
            Thread.sleep(100)
        }
    }

    private fun showProgressNumber(progress: Int) {
        val broadcastIntent = Intent(PROGRESS_UPDATE_INTENT)
        broadcastIntent.putExtra(PROGRESS_KEY_INTENT, progress)
        sendBroadcast(broadcastIntent)
    }
}