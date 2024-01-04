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
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NavUtils
import es.ua.eps.exercice4.databinding.ActivityMainBinding
import es.ua.eps.exercice4.databinding.ActivityUserManagementBinding

class UserManagementActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityUserManagementBinding

    lateinit var userDropdown : Spinner

    lateinit var newUserButton: Button
    lateinit var updateUserButton: Button
    lateinit var deleteUserButton: Button
    lateinit var listUserButton: Button
    lateinit var backButton : Button

    var userPositionInArray : Int = 0
    private lateinit var sqliteHelper: UsersSQLiteHelper
    private lateinit var users: ArrayList<UserModel>


    companion object{
        const val USER_ID = "USER_ID"
        const val USER_USERNAME = "USER_USERNAME"
        const val USER_PASS = "USER_PASS"
        const val USER_NAME = "USER_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityUserManagementBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "User Management"

        sqliteHelper = UsersSQLiteHelper(this)

        userDropdown = viewBinding.selectUser
        newUserButton = viewBinding.newUserButton
        updateUserButton = viewBinding.updateUserButton
        deleteUserButton = viewBinding.deleteUserButton
        listUserButton = viewBinding.listUserButton
        backButton = viewBinding.backButton

        refreshUsers()

        newUserButton.setOnClickListener{
            val intent = Intent(this@UserManagementActivity, NewUserActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        updateUserButton.setOnClickListener{
            val intent = Intent(this@UserManagementActivity, UpdateUserActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra(USER_ID, users[userPositionInArray].getID())
            intent.putExtra(USER_USERNAME, users[userPositionInArray].getUserName())
            intent.putExtra(USER_PASS, users[userPositionInArray].getPassword())
            intent.putExtra(USER_NAME, users[userPositionInArray].getCompleteName())
            startActivity(intent)
        }

        listUserButton.setOnClickListener{
            val intent = Intent(this@UserManagementActivity, ListUserActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        backButton.setOnClickListener{
           finish()
        }

        deleteUserButton.setOnClickListener {
            deleteActiveUser()
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
            R.id.createBU -> {
                BackUpManager.createBackup(
                    this,
                    Intent(
                        Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                        Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                    )
                )
            }
            R.id.restoreBU -> {
                BackUpManager.restoreBackup(this)
                refreshUsers()
            }
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

    fun refreshUsers()
    {
        users = sqliteHelper.getUsers()

        val userIDS = ArrayList<Int>()
        val userUsernames = ArrayList<String>()
        val userPasswords = ArrayList<String>()
        val userCompleteNames = ArrayList<String>()
        val userEmails = ArrayList<String>()

        for(user in users)
        {
            userIDS.add(user.getID())
            userUsernames.add(user.getUserName())
            userPasswords.add(user.getPassword())
            userCompleteNames.add(user.getCompleteName())
            userEmails.add(user.getEmail())
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, userUsernames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        userDropdown.adapter = adapter

        userDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                userPositionInArray = position

            }
            override fun onNothingSelected(parent: AdapterView<*>?){}
        }
    }

    fun deleteActiveUser() {
        if (users.size > 0) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete User")
            builder.setMessage("Do you really want to delete the selected user?")
            builder.setCancelable(true)
            val userID= users[userPositionInArray].getID()
            builder.setPositiveButton("OK") { dialog, _ ->
                contentResolver.delete(UserContentProvider.CONTENT_URI, "_ID = ?", arrayOf(userID.toString()))
                refreshUsers()
                dialog.dismiss()
            }
            builder.setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }
            val alert = builder.create()
            alert.show()
        }
        else{
            Toast.makeText(this, "There aren't users registered", Toast.LENGTH_SHORT).show()
        }
    }
}