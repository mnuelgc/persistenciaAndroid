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
import android.widget.TextView
import androidx.core.app.NavUtils
import es.ua.eps.exercice4.databinding.ActivityMainBinding
import es.ua.eps.exercice4.databinding.ActivityUserDataBinding

class UserDataActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityUserDataBinding

    lateinit var userDataNameValue : TextView
    lateinit var userDataUserNameValue : TextView

    lateinit var backButton: Button

    companion object {
        const val USER_NAME = "USER_NAME"
        const val USER_COMPLETE_NAME = "USER_COMPLETE_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityUserDataBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        backButton = viewBinding.userDataBackButton
        backButton.setOnClickListener{
            finish();
        }

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userDataNameValue = viewBinding.userDataNameValue
        userDataUserNameValue = viewBinding.userDataUserNameValue


        userDataNameValue.text = intent.getStringExtra(USER_NAME)
        userDataUserNameValue.text = intent.getStringExtra(USER_COMPLETE_NAME)

        supportActionBar?.title = "User Data"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()

        when(id)
        {
            android.R.id.home ->{
                finish()
                return true
            }
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