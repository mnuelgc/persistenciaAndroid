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
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import es.ua.eps.exercice4.databinding.ActivityMainBinding
import es.ua.eps.exercice4.databinding.ActivityUpdateUserBinding
import es.ua.eps.exercice4.databinding.ActivityUserDataBinding

class UpdateUserActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityUpdateUserBinding

    lateinit var usernameEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var userCompleteNamedEditText: EditText

    lateinit var updateButton: Button
    lateinit var backButton: Button

    private lateinit var sqliteHelper: UsersSQLiteHelper

    companion object {
        const val USER_ID = "USER_ID"
        const val USER_USERNAME = "USER_USERNAME"
        const val USER_PASS = "USER_PASS"
        const val USER_NAME = "USER_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        sqliteHelper = UsersSQLiteHelper(this)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "Update User"

        usernameEditText = viewBinding.updateLoginUserNameEditText
        passwordEditText = viewBinding.updatePasswordEditText
        userCompleteNamedEditText = viewBinding.updateUserNameEditText

        updateButton = viewBinding.updateButton
        backButton = viewBinding.closeButton

        printUserInformation()

        updateButton.setOnClickListener {
            updateUser()
        }

        backButton.setOnClickListener {
            val intent = Intent(this, UserManagementActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()

        when (id) {
            android.R.id.home -> {
                val intent = Intent(this, UserManagementActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                return true
            }

            R.id.manageUsers -> {
                val intent = Intent(this, UserManagementActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                return true
            }

            R.id.createBU -> BackUpManager.createBackup(
                this,
                Intent(
                    Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                    Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                )
            )

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

    fun printUserInformation() {

        usernameEditText.setText(intent.getStringExtra(USER_USERNAME).toString())
        passwordEditText.setText(intent.getStringExtra(USER_PASS).toString())
        userCompleteNamedEditText.setText(intent.getStringExtra(USER_NAME).toString())

    }

    fun updateUser() {
        val userID = intent.getIntExtra(USER_ID, -1)
        val userName = usernameEditText.text.toString()
        val pass = passwordEditText.text.toString()
        val userCompleteName = userCompleteNamedEditText.text.toString()

        if (userName.isEmpty() || pass.isEmpty() || userCompleteName.isEmpty()) {
            Toast.makeText(this, "Error can't update if fields are empty", Toast.LENGTH_SHORT)
                .show()
        } else {
            if (userID != -1) {
                var contentValue = ContentValues()
                contentValue.put(UserContentProvider.id,userID)
                contentValue.put(UserContentProvider.username, userName)
                contentValue.put(UserContentProvider.password, pass)
                contentValue.put(UserContentProvider.nombreCompleto, userCompleteName)
                contentValue.put(UserContentProvider.email, userCompleteName)

                try{
                    contentResolver.update(UserContentProvider.CONTENT_URI, contentValue,"_ID= ?", arrayOf(userID.toString()))
                    Toast.makeText(this, "User updated", Toast.LENGTH_SHORT).show()
                }catch( e: Exception)
                {
                    Toast.makeText(this, "Error can't update", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Error can't update", Toast.LENGTH_SHORT).show()
            }
        }

    }
}