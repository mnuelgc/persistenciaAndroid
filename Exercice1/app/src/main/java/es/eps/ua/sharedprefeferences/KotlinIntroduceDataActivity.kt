package es.eps.ua.sharedprefeferences

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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


        val prefs : SharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE)

        val prefText = UtilsKotlin.decrypt(prefs.getString("textField", "Texto por defecto"))
        val sizeText = UtilsKotlin.decrypt(prefs.getString("sizeText", "32")).toFloat()

        textResult.text = prefText
        sizeResult.text = sizeText.toString()



        editTextField.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
/*                if (s != "") {
                    textResult.text = editTextField?.text
                }
 */
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {}
        })
        
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var value = seekBar?.progress
                if (value == 0)
                    value = 1

                tamanyoLabel.text = "${getString(R.string.tamano)} (${value}/50)"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        buttonApply.setOnClickListener {
            val intentCreateChatRoom = Intent(this@KotlinIntroduceDataActivity, kotlinDetailActivity::class.java)

            val prefs : SharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("textField", UtilsKotlin.encrypt(editTextField.text.toString()))

            var value = seekBar?.progress
            if (value == 0)
                value = 1

            editor.putString("sizeText", UtilsKotlin.encrypt(value!!.toString()))
            editor.apply()

            startActivity(intentCreateChatRoom)
        }

        buttonExit.setOnClickListener {
            finish()
        }

        buttonSwap.setOnClickListener{
            val intentSwap = Intent(this@KotlinIntroduceDataActivity, JavaIntroduceDataActivity::class.java)
            startActivity(intentSwap)
        }
    }

}