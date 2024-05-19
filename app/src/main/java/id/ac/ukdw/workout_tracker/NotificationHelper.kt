package id.ac.ukdw.workout_tracker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class NotificationHelper(private val co: Context, private val msg: String) {
    private val CHANNEL_ID = "massage_id"
    private val NOTIFICATION_ID = 123

    fun notifyUser(jenisWorkout: String, date: Long) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(co, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Izin belum diberikan, tidak dapat mengirim notifikasi
                return
            }
        }

        createNotificationChannel()

        val sendIntent = Intent(co, TestActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("JenisWorkout", jenisWorkout)
            putExtra("Date", date)
        }
        val pendingIntent = PendingIntent.getActivity(co, 0, sendIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val icon = BitmapFactory.decodeResource(co.resources, R.drawable.app_logo)
        val notification = NotificationCompat.Builder(co, CHANNEL_ID)
            .setSmallIcon(R.drawable.app_logo)
            .setLargeIcon(icon)
            .setContentTitle("Berhasil Menyelesaikan Set Workout!!")
            .setContentText(msg)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)  // Set priority to high
            .setDefaults(NotificationCompat.DEFAULT_ALL)    // Ensure all default settings are applied
            .setCategory(NotificationCompat.CATEGORY_MESSAGE) // Set category for a message type notification
            .build()

        // Tambahkan log ini
        Log.d("NotificationHelper", "Notifikasi akan dikirim")

        NotificationManagerCompat.from(co).notify(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_ID
            val description = "Channel description"
            val importance = NotificationManager.IMPORTANCE_HIGH  // Set importance to high
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                this.description = description
            }
            val notificationManager = co.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
