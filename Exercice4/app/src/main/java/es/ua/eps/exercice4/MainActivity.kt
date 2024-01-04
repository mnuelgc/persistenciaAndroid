package es.ua.eps.exercice4

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NavUtils
import androidx.room.Room
import es.ua.eps.exercice4.databinding.ActivityMainBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityMainBinding

    lateinit var usernameEditText : EditText
    lateinit var passwordEditText : EditText

    lateinit var loginButton: Button
    lateinit var closeButton: Button

    private lateinit var db : AppDatabase

    companion object {
        const val USER_NAME = "USER_NAME"
        const val USER_COMPLETE_NAME = "USER_COMPLETE_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        db = BackUpManager.getDataBase(this)

        supportActionBar?.title = "SQLite"

        usernameEditText = viewBinding.userNameEditText
        passwordEditText = viewBinding.passwordEditText

        loginButton = viewBinding.loginButton

        loginButton.setOnClickListener {

            val user = db.userDao().login(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )

            if (user != null) {
                val intent = Intent(this, UserDataActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                intent.putExtra(USER_NAME, user.getField_userName())
                intent.putExtra(USER_COMPLETE_NAME, user.getField_completeName())
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Cant login wrong data", Toast.LENGTH_SHORT).show()
            }
        }


        closeButton = viewBinding.closeButton
        closeButton.setOnClickListener{
            finish();
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()

        when(id)
        {

            R.id.manageUsers ->  {
                val intent = Intent(this, UserManagementActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                return true
            }
            R.id.createBU -> BackUpManager.createBackup(
                this,
                Intent(
                    Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                    Uri.parse("package:" + BuildConfig.APPLICATION_ID)))
            R.id.restoreBU -> BackUpManager.restoreBackup(this)
        }


        return super.onOptionsItemSelected(item)
    }

    override fun onRestart() {
        super.onRestart()
        if (Build.VERSION.SDK_INT >= 30) {
            if (BackUpManager.checkExternalStorage) {
                if (Environment.isExternalStorageManager()) {
                    //CREATE BACKUP
                    BackUpManager.backupSave(this)
                }
            }
        }
    }


}