package es.ua.eps.exercice4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import es.ua.eps.exercice4.databinding.ActivityMainBinding
import es.ua.eps.exercice4.databinding.ActivityUpdateUserBinding
import es.ua.eps.exercice4.databinding.ActivityUserDataBinding

class UpdateUserActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityUpdateUserBinding

    lateinit var usernameEditText : EditText
    lateinit var passwordEditText : EditText

    lateinit var updateButton: Button
    lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "Update User"

        updateButton = viewBinding.updateButton
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