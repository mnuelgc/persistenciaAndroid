package es.eps.ua.sharedprefeferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import es.eps.ua.sharedprefeferences.databinding.ActivityKotlinDetailBinding
import es.eps.ua.sharedprefeferences.databinding.ActivityKotlinIntroduceDataBinding

class kotlinDetailActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityKotlinDetailBinding

    lateinit var buttonBack : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityKotlinDetailBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)
        buttonBack = viewBinding.exitButtonKotlin

        buttonBack.setOnClickListener {
            finish()
        }
    }
}