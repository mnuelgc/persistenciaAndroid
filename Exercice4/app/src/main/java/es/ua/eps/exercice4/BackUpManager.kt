package es.ua.eps.exercice4

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

object BackUpManager {

    var checkExternalStorage = false
    public fun createBackup(context : Context, intent : Intent) {
        if(checkPermission(context, intent)){backupSave(context)}
        else{
            Toast.makeText(context,"Need to acept permissions", Toast.LENGTH_SHORT).show()}
    }
    public fun restoreBackup(context : Context){
        val backupDir = File(Environment.getExternalStorageDirectory(), "BackUps")
        val backupFile = File(backupDir, "UserDB")

        if (backupFile.exists()) {
            val dbPath = context.getDatabasePath("UserDB").absolutePath
            try {
                copyFile(backupFile, File(dbPath))
                Toast.makeText(context, "Database Restored", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(context, "Can't restore Database", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Back up doesn't exists", Toast.LENGTH_SHORT).show()
        }
    }

    public fun checkPermission(context : Context, intent : Intent): Boolean {
        if (Build.VERSION.SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                val uri: Uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                startActivity(context, Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri), null)
                checkExternalStorage = true
                return false
            } else {
                return true
            }
        }
        return true
    }

    public fun backupSave(context : Context){
        val db = context.getDatabasePath("UserDB")
        if (db.exists()) {
            val backupDir = File(Environment.getExternalStorageDirectory(), "BackUps")
            if (!backupDir.exists()) {
                backupDir.mkdirs()
            }
            val backupFile = File(backupDir, "UserDB")
            try {
                copyFile(db, backupFile)
                Toast.makeText(context, "BackUp done", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(context, "Error doing BackUp", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Database doesn't exist", Toast.LENGTH_SHORT).show()
        }
    }

    public fun copyFile(originFile: File, destFile: File) {
        val originChannel = FileInputStream(originFile).channel
        val destChannel = FileOutputStream(destFile).channel

        try {
            originChannel.transferTo(0, originChannel.size(), destChannel)
        } finally {
            originChannel.close()
            destChannel.close()
        }
    }
}