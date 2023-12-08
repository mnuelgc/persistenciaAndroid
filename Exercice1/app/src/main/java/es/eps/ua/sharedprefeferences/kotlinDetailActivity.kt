package es.eps.ua.sharedprefeferences

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import es.eps.ua.sharedprefeferences.databinding.ActivityKotlinDetailBinding

class kotlinDetailActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityKotlinDetailBinding

    lateinit var buttonBack : Button

    lateinit var textDetailKotlin : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityKotlinDetailBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)
        buttonBack = viewBinding.exitButtonKotlin

        textDetailKotlin = viewBinding.textDetailKotlin

        val prefs: SharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE)

        textDetailKotlin.text =
            UtilsKotlin.decrypt(prefs.getString("textField", "Texto por defecto"))
        textDetailKotlin.textSize = UtilsKotlin.decrypt(prefs.getString("sizeText", "32")).toFloat()

        buttonBack.setOnClickListener {
            val intentBack =
                Intent(this@kotlinDetailActivity, KotlinIntroduceDataActivity::class.java)
            intentBack.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intentBack)
        }
    }


}