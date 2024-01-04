package es.ua.eps.exercice6login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import es.ua.eps.exercice6login.databinding.ActivityUserDataBinding

class UserDataActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityUserDataBinding

    lateinit var userDataNameValue : TextView
    lateinit var userDataUserNameValue : TextView

    lateinit var backButton: Button

    companion object {
        const val USER_NAME = "USER_NAME"
        const val USER_COMPLETE_NAME = "USER_COMPLETE_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityUserDataBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        backButton = viewBinding.userDataBackButton
        backButton.setOnClickListener{
            finish();
        }

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userDataNameValue = viewBinding.userDataNameValue
        userDataUserNameValue = viewBinding.userDataUserNameValue


        userDataNameValue.text = intent.getStringExtra(USER_NAME)
        userDataUserNameValue.text = intent.getStringExtra(USER_COMPLETE_NAME)

        supportActionBar?.title = "User Data"
    }


}