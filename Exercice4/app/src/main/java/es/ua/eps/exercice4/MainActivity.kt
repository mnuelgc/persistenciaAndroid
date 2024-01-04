package es.ua.eps.exercice4

import android.content.ContentValues
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

    private lateinit var sqliteHelper : UsersSQLiteHelper

    companion object {
        const val USER_NAME = "USER_NAME"
        const val USER_COMPLETE_NAME = "USER_COMPLETE_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        sqliteHelper = UsersSQLiteHelper(this)

        supportActionBar?.title = "SQLite"

        usernameEditText = viewBinding.userNameEditText
        passwordEditText = viewBinding.passwordEditText

        loginButton = viewBinding.loginButton

        loginButton.setOnClickListener {
            val us = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            var loginSuccessful = false
            if(us.isNotEmpty() && password.isNotEmpty()){
                val users = getUsers()
                println("users -> ${users.size}")

                for (user in users) {
                    val username = user.getAsString(UserContentProvider.username)
                    val storedPassword = user.getAsString(UserContentProvider.password)
                    val userCompleteName = user.getAsString(UserContentProvider.nombreCompleto)

                    println("us ${us} -> $username")
                    println("pas $password -> $storedPassword")
                    if (us.equals(username) && password.equals(storedPassword)) {
                        val intentUser = Intent(this@MainActivity, UserDataActivity::class.java)
                        intentUser.putExtra(USER_NAME, username)
                        intentUser.putExtra(USER_COMPLETE_NAME, userCompleteName)
                        startActivity(intentUser)
                        loginSuccessful = true
                        break
                    }
                }
                if (!loginSuccessful) {
                    Toast.makeText(this, "Error in data", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Error blank fields", Toast.LENGTH_SHORT).show()
            }
        }

        closeButton = viewBinding.closeButton
        closeButton.setOnClickListener{
            finish();
        }

    }


private fun getUsers(): ArrayList<ContentValues>{
    val cursor = contentResolver.query(
        UserContentProvider.CONTENT_URI,
        arrayOf(UserContentProvider.id, UserContentProvider.username, UserContentProvider.password, UserContentProvider.nombreCompleto, UserContentProvider.email),
        null,
        null,
        UserContentProvider.id
    )

    val users = ArrayList<ContentValues>()

    cursor?.let {

        val idColumnIndex = it.getColumnIndex(UserContentProvider.id)
        val usernameColumnIndex = it.getColumnIndex(UserContentProvider.username)
        val passwordColumnIndex = it.getColumnIndex(UserContentProvider.password)
        val completenameColumnIndex = it.getColumnIndex(UserContentProvider.nombreCompleto)

        while (it.moveToNext()) {
            println("Dent")

            println("idIndx $idColumnIndex idUserName $usernameColumnIndex id pass $passwordColumnIndex idcomplete" +
                    " $completenameColumnIndex" )


            // Verificar que los índices sean válidos
            if (idColumnIndex >= 0 && usernameColumnIndex >= 0 &&
                passwordColumnIndex >= 0 && completenameColumnIndex >= 0
            ) {
                println("Harvey")

                // Obtener los datos usando los índices
                val cv = ContentValues()
                cv.put(UserContentProvider.id, it.getInt(idColumnIndex))
                cv.put(UserContentProvider.username, it.getString(usernameColumnIndex))
                cv.put(UserContentProvider.password, it.getString(passwordColumnIndex))
                cv.put(UserContentProvider.nombreCompleto, it.getString(completenameColumnIndex))
                users.add(cv)
            }
        }
    }
    cursor?.close()
    return users

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