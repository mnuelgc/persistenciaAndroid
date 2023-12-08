package es.ua.eps.exercice2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import es.eps.ua.sharedprefeferences.R

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        supportFragmentManager.commit {
            setReorderingAllowed(true)

            //  add<ProbeFragment>(R.id.fragmentContainer)
        }

    }
}