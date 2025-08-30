package com.joshdev.smartpocket.src.workers

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.joshdev.smartpocket.src.database.AppDatabaseSingleton
import com.joshdev.smartpocket.src.database.entity.invoice.Invoice
import kotlinx.coroutines.delay

class AddInvoiceWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun getForegroundInfo(): ForegroundInfo {
        val notificationId = 1500
        val notification = createNotification()
        return ForegroundInfo(
            notificationId,
            notification,
            ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
        )
    }

    private fun createNotification(): Notification {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            "smartpocket_background_notification",
            "Smart Pocket - Background Service",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val notificationBuilder = NotificationCompat.Builder(applicationContext, channel.id)
            .setContentTitle("Guardando factura")
            .setContentText("Por favor, espere mientras se guarda el documento.")
            .setSmallIcon(R.drawable.stat_notify_sync)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        return notificationBuilder.build()
    }

    override suspend fun doWork(): Result {
        setForeground(getForegroundInfo())

        val database = AppDatabaseSingleton.getInstance(applicationContext)
        val invoiceDao = database.invoiceDao()

        val invoice = Invoice(
            name = inputData.getString("INVOICE_NAME") ?: return Result.failure(),
            author = inputData.getString("INVOICE_AUTHOR") ?: return Result.failure(),
            creationDate = inputData.getString("INVOICE_CREATION_DATE") ?: return Result.failure(),
            modificationDate = inputData.getString("INVOICE_MODIFICATION_DATE")
                ?: return Result.failure(),
            total = inputData.getString("INVOICE_TOTAL")?.toDoubleOrNull()
        )

        delay(30000)

        try {
            invoiceDao.insert(invoice)
            return Result.success()
        } catch (e: Exception) {
            println(e.message)
            return Result.failure()
        }
    }
}
