package es.ua.eps.exercice4

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import es.ua.eps.exercice4.databinding.ActivityListUserBinding
import es.ua.eps.exercice4.databinding.ActivityUserManagementBinding

class ListUserActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityListUserBinding

    lateinit var userDropdown : Spinner

    lateinit var backButton : Button

    private lateinit var recyclerView: RecyclerView
    private var adapter:UserAdapter ?= null

    private lateinit var db : AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityListUserBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "List Users"


        backButton = viewBinding.closeButton

        recyclerView = viewBinding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter()
        recyclerView.adapter = adapter

        db = BackUpManager.getDataBase(this)
  /*      val userList = sqLiteHelper.getUsers()
        adapter?.addItems(userList)
*/
        val userList = db.userDao().getAllUsers()
        adapter?.addItems(userList)


        backButton.setOnClickListener{
            finish()
        }
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