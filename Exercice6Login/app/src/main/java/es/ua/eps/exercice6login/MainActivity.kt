package es.ua.eps.exercice6login

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NavUtils
import es.ua.eps.exercice6login.databinding.ActivityMainBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityMainBinding

    lateinit var usernameEditText : EditText
    lateinit var passwordEditText : EditText

    lateinit var loginButton: Button
    lateinit var closeButton: Button

    companion object {
        const val USER_NAME = "USER_NAME"
        const val USER_COMPLETE_NAME = "USER_COMPLETE_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        supportActionBar?.title = "SQLite"

        usernameEditText = viewBinding.userNameEditText
        passwordEditText = viewBinding.passwordEditText

        loginButton = viewBinding.loginButton

        loginButton.setOnClickListener {
            val u = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            var loginSuccessful = false
            if(u.isNotEmpty() && password.isNotEmpty()){
                val users = getUsers()
                for (user in users) {
                    val username = user.getAsString("username")
                    val storedPassword = user.getAsString("password")
                    val userCompleteName = user.getAsString("nombre_completo")
                    val email = user.getAsString("email")

                    println("$u -> $username")
                    println("$password -> $storedPassword")
                    if (u.equals(username) && password.equals(storedPassword)) {
                        println("Correcto")

                        val intentUser = Intent(this@MainActivity, UserDataActivity::class.java)
                        intentUser.putExtra(USER_NAME, u)
                        intentUser.putExtra(USER_COMPLETE_NAME, userCompleteName)
                        startActivity(intentUser)   
                        loginSuccessful = true
                        break
                    }
                }
                if (!loginSuccessful) {
                    Toast.makeText(this, "Error usuario/password incorrectos", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Por favor rellene los campos obligatorios", Toast.LENGTH_SHORT).show()
            }
        }

        closeButton = viewBinding.closeButton
        closeButton.setOnClickListener{
            finish();
        }

    }


    private fun getUsers(): ArrayList<ContentValues>{
        val uri = Uri.parse("content://es.ua.eps.exercice4/Usuarios")
        val cursor = contentResolver.query(
            uri,
            arrayOf("_ID","username","password","nombre_completo","email"),
            null,
            null,
            null
        )

        val users = ArrayList<ContentValues>()
        cursor?.let {

            val idColumnIndex = it.getColumnIndex("_ID")
            val usernameColumnIndex = it.getColumnIndex("username")
            val passwordColumnIndex = it.getColumnIndex("password")
            val completenameColumnIndex = it.getColumnIndex("nombre_completo")
            val emailColumnIndex = it.getColumnIndex("email")

            while (it.moveToNext()) {
                println("Dent")

                println("idIndx $idColumnIndex idUserName $usernameColumnIndex id pass $passwordColumnIndex idcomplete" +
                        " $completenameColumnIndex" )
                // Verificar que los índices sean válidos
                if (idColumnIndex >= 0 && usernameColumnIndex >= 0 &&
                    passwordColumnIndex >= 0 && usernameColumnIndex >= 0 &&
                    emailColumnIndex >= 0
                ) {

                    println("Harvey")

                    // Obtener los datos usando los índices
                    val cv = ContentValues()
                    cv.put("_ID", it.getInt(idColumnIndex))
                    cv.put("username", it.getString(usernameColumnIndex))
                    cv.put("password", it.getString(passwordColumnIndex))
                    cv.put("nombre_completo", it.getString(completenameColumnIndex))
                    cv.put("email", it.getString(emailColumnIndex))
                    users.add(cv)
                }
            }
        }
        cursor?.close()
        return users

    }




}