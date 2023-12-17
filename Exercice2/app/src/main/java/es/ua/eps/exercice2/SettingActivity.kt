package es.ua.eps.exercice2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.text.HtmlCompat
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import es.ua.eps.exercice2.databinding.ActivityMainBinding
import es.ua.eps.exercice2.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    lateinit var viewBinding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = viewBinding.drawerLayout
        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Settings"

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val menuElement = navigationView.menu[1]
        menuElement.isChecked = true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_one -> {
                finish()
                /*
                Toast.makeText(this, "MAIN", Toast.LENGTH_SHORT).show()
                var settingIntent: Intent =
                    Intent(this@SettingActivity, MainActivity::class.java)
                settingIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(settingIntent)

                 */
            }

            R.id.nav_item_two -> {
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}