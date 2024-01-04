package es.ua.eps.exercice4

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_database")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
     var id : Long = 0,

    @ColumnInfo(name = "nombre_usuario")
     var nombreUsuario : String,

    @ColumnInfo(name = "password")
     var password : String,

    @ColumnInfo(name = "nombre_completo")
     var nombreCompleto : String,

    @ColumnInfo(name = "email")
     var email : String
){

    fun setField_id(newID : Long) {
        id = newID
    }

    fun setField_userName(newUserName : String){
        nombreUsuario = newUserName
    }

    fun setField_password(newPassword: String){
        password = newPassword
    }

    fun setField_completeName(newNombre_completo: String){
        nombreCompleto = newNombre_completo
    }

    fun setField_email(newEmail: String){
        email = newEmail
    }

    fun getField_id() : Long {
        return id
    }

    fun getField_userName() : String {
        return nombreUsuario
    }

    fun getField_password() : String {
        return password
    }

    fun getField_completeName() : String {
        return nombreCompleto
    }

    fun getField_email() : String {
        return email
    }

}