package es.ua.eps.exercice4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Spinner
import es.ua.eps.exercice4.databinding.ActivityListUserBinding
import es.ua.eps.exercice4.databinding.ActivityUserManagementBinding

class ListUserActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityListUserBinding

    lateinit var userDropdown : Spinner

    lateinit var backButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityListUserBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "List Users"


        backButton = viewBinding.closeButton

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

        }

        return super.onOptionsItemSelected(item)
    }
}