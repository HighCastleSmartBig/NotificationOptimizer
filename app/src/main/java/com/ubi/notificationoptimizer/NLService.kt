package com.ubi.notificationoptimizer

/**
 * Created by ogata.fumitoshi on 2015/03/26.
 */

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat

class NotificationService : NotificationListenerService() {

    private val fileName = "Notification.csv"

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        //通知が更新
        val post = "posted"
        val packageName = sbn.packageName
        val postDate = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(sbn.postTime)
        val ticket = sbn.notification.tickerText
        val message: String
        val category = sbn.notification.category

        //messageが空の時謎の通知がいっぱい読み込まれちゃう
        if (ticket != null) {
            message = ticket.toString()
            writeCsvData(fileName, "$post,$packageName,$postDate,$message,$category")
        } else {

        }

    }

    override fun onNotificationRemoved(sbn: StatusBarNotification, rankingMap: RankingMap) {

        //通知が削除
        val remove = "removed"
        val packageName = sbn.packageName
        val postDate = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(sbn.postTime)
        val ticket = sbn.notification.tickerText
        val message: String
        val category = sbn.notification.category

        if (ticket != null) {
            message = ticket.toString()
            writeCsvData(fileName, "$remove,$packageName,$postDate,$message,$category")
        } else {

        }


    }

    private fun writeCsvData(fileName: String, log: String) {
        val filePath = this.getExternalFilesDir(null)!!.absolutePath + "/" + fileName
        println(filePath)
        // String filePath = Environment.getDataDirectory() + "/" + fileName;
        // Log.d(TAG, filePath);
        try {
            val outputFile = File(filePath)
            if (!outputFile.exists()) {
                outputFile.createNewFile()
            }
            val fileWriter = FileWriter(outputFile, true)
            fileWriter.write(log + "\n")
            fileWriter.close()
        } catch (e: IOException) {
        }

    }

}
