package es.ua.eps.exercice4

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_database")
class UserEntity(
        newNombre_usuario : String,
        newPassword : String,
        newNombre_completo : String,
        newEmail : String)  {

    @PrimaryKey(autoGenerate = true)
    private var ID = 0

    @ColumnInfo(name = "nombre_usuario")
    private var nombre_usuario = newNombre_usuario

    @ColumnInfo(name = "password")
    private var password = newPassword

    @ColumnInfo(name = "nombre_completo")
    private var nombre_completo = newNombre_completo

    @ColumnInfo(name = "email")
    private var email = newEmail
        constructor(
            newID : Int,
            newNombre_usuario : String,
            newPassword : String,
            newNombre_completo : String,
            newEmail : String) : this(newNombre_usuario, newPassword, newNombre_completo, newEmail) {
            this.ID = newID
        }

    fun setID(newID : Int) {
        ID = newID
    }

    fun setUserName(newUserName : String){
        nombre_usuario = newUserName
    }

    fun setPassword(newPassword: String){
        password = newPassword
    }

    fun setCompleteName(newNombre_completo: String){
        nombre_completo = newNombre_completo
    }

    fun getEmail(newEmail: String){
        email = newEmail
    }

    fun getID() : Int {
        return ID
    }

    fun getUserName() : String {
        return nombre_usuario
    }

    fun getPassword() : String {
        return password
    }

    fun getCompleteName() : String {
        return nombre_completo
    }

    fun getEmail() : String {
        return email
    }

    }