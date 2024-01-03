package es.ua.eps.exercice4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityUserManagementBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "User Management"


        newUserButton = viewBinding.newUserButton
        updateUserButton = viewBinding.updateUserButton
        deleteUserButton = viewBinding.deleteUserButton
        listUserButton = viewBinding.listUserButton
        backButton = viewBinding.backButton

        newUserButton.setOnClickListener{
            val intent = Intent(this@UserManagementActivity, NewUserActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }


        updateUserButton.setOnClickListener{
            val intent = Intent(this@UserManagementActivity, UpdateUserActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
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

        }

        return super.onOptionsItemSelected(item)
    }
}