package es.ua.eps.exercice4

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
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import es.ua.eps.exercice4.databinding.ActivityNewUserBinding
import es.ua.eps.exercice4.databinding.ActivityUpdateUserBinding
import kotlinx.coroutines.launch

class NewUserActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityNewUserBinding

    lateinit var newLoginUserNameEditText: EditText
    lateinit var newPasswordEditText: EditText
    lateinit var newUserNameEditText: EditText

    lateinit var newButton: Button
    lateinit var backButton: Button


    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityNewUserBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "New User"

        db = BackUpManager.getDataBase(this)

        newLoginUserNameEditText = viewBinding.loginUserNameEditText
        newPasswordEditText = viewBinding.createPasswordEditText
        newUserNameEditText = viewBinding.createUserNameEditText

        newButton = viewBinding.newButton
        backButton = viewBinding.closeButton

        newButton.setOnClickListener {
            newUser()
            cleanFields()
        }

        backButton.setOnClickListener {
            val intent = Intent(this, UserManagementActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    fun newUser(){
        val userName = newLoginUserNameEditText.text.toString()
        val pass = newPasswordEditText.text.toString()
        val userCompleteName = newUserNameEditText.text.toString()

        if (userName.isEmpty() || pass.isEmpty() || userCompleteName.isEmpty()) {
            Toast.makeText(this, "Can't create user with  empty fields", Toast.LENGTH_SHORT).show()
        } else {
            lifecycleScope.launch {
                val user = UserEntity(0,userName, pass, userCompleteName, userCompleteName)
                db.userDao().insert(user)
            }
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

    fun cleanFields() {
        newLoginUserNameEditText.setText("")
        newPasswordEditText.setText("")
        newUserNameEditText.setText("")
    }
}