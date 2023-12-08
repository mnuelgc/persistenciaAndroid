package es.eps.ua.sharedprefeferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import es.eps.ua.sharedprefeferences.databinding.ActivityKotlinIntroduceDataBinding

class KotlinIntroduceDataActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityKotlinIntroduceDataBinding

    lateinit var buttonApply : Button
    lateinit var buttonExit : Button
    lateinit var buttonSwap : Button

    lateinit var editTextField : EditText
    lateinit var tamanyoLabel : TextView
    lateinit var seekBar : SeekBar
    lateinit var sizeResult : TextView
    lateinit var textResult : TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityKotlinIntroduceDataBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        buttonApply = viewBinding.applyChangesKotlin
        buttonExit = viewBinding.exitButtonKotlin
        buttonSwap = viewBinding.swapLanguageButtonKotlin

        editTextField = viewBinding.editTextField
        tamanyoLabel = viewBinding.siezeLabelText
        seekBar = viewBinding.seekBarKotlinId
        sizeResult = viewBinding.sizeResult
        textResult = viewBinding.textResult



        textResult.text = editTextField?.text
        sizeResult.text = seekBar?.progress.toString()


        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var value = seekBar?.progress
                if (value == 0)
                    value = 1

                sizeResult.text = seekBar?.progress.toString()
                tamanyoLabel.text = "${getString(R.string.tamano)} (${value}/50)"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        buttonApply.setOnClickListener {
            val intentCreateChatRoom = Intent(this@KotlinIntroduceDataActivity, kotlinDetailActivity::class.java)
            startActivity(intentCreateChatRoom)
        }

        buttonExit.setOnClickListener {
            finish()
        }
    }
}