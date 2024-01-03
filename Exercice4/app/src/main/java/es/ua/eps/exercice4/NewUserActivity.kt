package es.ua.eps.exercice4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import es.ua.eps.exercice4.databinding.ActivityNewUserBinding
import es.ua.eps.exercice4.databinding.ActivityUpdateUserBinding

class NewUserActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityNewUserBinding

    lateinit var usernameEditText: EditText
    lateinit var passwordEditText: EditText

    lateinit var updateButton: Button
    lateinit var backButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityNewUserBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "New User"

        updateButton = viewBinding.newButton
        backButton = viewBinding.closeButton

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

        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.manageUsers -> {
                val intent = Intent(this, UserManagementActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}