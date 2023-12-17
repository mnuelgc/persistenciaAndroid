package es.ua.eps.exercice2

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.graphics.toColorInt
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.preference.PreferenceManager
import com.google.android.material.navigation.NavigationView
import es.ua.eps.exercice2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer : DrawerLayout
    private lateinit var toggle : ActionBarDrawerToggle
    lateinit var viewBinding : ActivityMainBinding
    lateinit var editTextField : EditText
    lateinit var textView: TextView
    lateinit var previewButton: Button
    lateinit var closeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = viewBinding.drawerLayout
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Main"



        val navigationView :NavigationView  = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val menuElement = navigationView.menu[0]
        menuElement.isChecked = true

        editTextField = viewBinding.editTextLabel
        textView = viewBinding.textViewField
        previewButton = viewBinding.previewButton
        closeButton = viewBinding.closeButton


        previewButton.setOnClickListener{
            val text = editTextField.text.toString()

            textView.text = text

            Preview(textView)
        }

        closeButton.setOnClickListener{
            finish()
        }

    }

    fun Preview(textView : TextView)
    {
        val sp  = PreferenceManager.getDefaultSharedPreferences(this)

        val size = sp.getString("size", "15")?.toFloat()
        val FgColor = sp.getString("fgcolor_key", "#FF000000")?.toColorInt()
        val BgColor = sp.getString("bgcolor_key", "#FFFFFFFF")?.toColorInt()
        val isBold = sp.getBoolean("bold", false)
        val isItalic = sp.getBoolean("italic", false)
        val alpha = sp.getInt("PAlpha", 100)
        val rotation = sp.getInt("PRotation", 0)

        textView.textSize = size!!
        textView.setTextColor(FgColor!!)
        textView.setBackgroundColor(BgColor!!)

        if(isBold)
        {
            textView.setTypeface(null, Typeface.BOLD)

            if (isItalic)
            {
                textView.setTypeface(null, Typeface.BOLD_ITALIC)
            }
        }else if (isItalic){
            textView.setTypeface(null, Typeface.ITALIC)
        }
        else{
            textView.setTypeface(null, Typeface.NORMAL)
        }

        textView.alpha = alpha/100f
        textView.rotation = (rotation).toFloat()

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_item_two ->{
                var settingIntent : Intent = Intent(this@MainActivity, SettingActivity::class.java)
                settingIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(settingIntent)
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}