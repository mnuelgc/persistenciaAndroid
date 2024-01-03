package es.ua.eps.exercice4

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UsersSQLiteHelper(context : Context)  : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UserDB"
        private const val TABLE_NAME = "Usuarios"
        private const val ID = "ID"
        private const val NOMBRE_USUARIO = "username"
        private const val PASSWORD = "password"
        private const val NOMBRE_COMPLETO = "nombre_completo"
        private const val EMAIL = "Email"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_USERS_TABLE = "CREATE TABLE ${TABLE_NAME}" +
                "(${ID} INTEGER PRIMARY KEY AUTOINCREMENT" +
                " ,${NOMBRE_USUARIO} TEXT" +
                " ,${PASSWORD} TEXT" +
                " ,${NOMBRE_COMPLETO} TEXT" +
                " ,${EMAIL} TEXT" +
                    ")"
        db?.execSQL(CREATE_USERS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(oldVersion == 1) {
            db?.execSQL("ALTER TABLE ${TABLE_NAME} ADD ${EMAIL} TEXT")
        }
    }
    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME}") // Hardcore way!
        onCreate(db)

    }

    fun addUser(user : UserModel) : Long {
        val db = writableDatabase
        val contentValues = ContentValues()

        contentValues.put(NOMBRE_USUARIO, user.getUserName())
        contentValues.put(PASSWORD, user.getPassword())
        contentValues.put(NOMBRE_COMPLETO, user.getCompleteName())
        contentValues.put(NOMBRE_USUARIO, user.getUserName())
        contentValues.put(EMAIL, user.getUserName())

        val success = db.insert(TABLE_NAME,null, contentValues)
        db.close()
        return success
    }

    fun updateUser(user : UserModel) : Int {
        val db = writableDatabase
        val contentValues = ContentValues()

        contentValues.put(NOMBRE_USUARIO, user.getUserName())
        contentValues.put(PASSWORD, user.getPassword())
        contentValues.put(NOMBRE_COMPLETO, user.getCompleteName())
        contentValues.put(NOMBRE_USUARIO, user.getUserName())
        contentValues.put(EMAIL, user.getUserName())

        val success = db.update(TABLE_NAME,contentValues, "id=${user.getID()}", null)
        db.close()
        return success
    }

    fun deleteUser(userId :Int) : Int {
        val db = writableDatabase

        val success = db.delete(TABLE_NAME, "id=$userId", null)
        db.close()
        return success
    }

    fun getUsers() : ArrayList<UserModel>{
        val userList : ArrayList<UserModel> = ArrayList()
        val selectQuery = "Select * FROM $TABLE_NAME"
        val db = this.readableDatabase

        val cursor : Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        }
        catch (e : Exception)
        {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return userList
        }

        if (cursor.moveToFirst())
        {
            do{
                val id = cursor.getInt(0)
                val userName = cursor.getString(1)
                val pass = cursor.getString(2)
                val completeName = cursor.getString(3)
                val email = cursor.getString(4)

                val user = UserModel(id, userName, pass, completeName, email)

                userList.add(user)
            }while(cursor.moveToNext())
        }
        cursor.close()
        return userList
    }

    fun login(username: String, password: String): UserModel? {
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE username='$username'"
        val db = this.writableDatabase
        val cursor: Cursor?
        var user : UserModel? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return user
        }

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(ID))
                val userName = cursor.getString(cursor.getColumnIndexOrThrow(NOMBRE_USUARIO))
                val passInDB = cursor.getString(cursor.getColumnIndexOrThrow(PASSWORD))
                val userCompleteName = cursor.getString(cursor.getColumnIndexOrThrow(NOMBRE_COMPLETO))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(EMAIL))
                if (password == passInDB) {
                    user = UserModel(id, userName, passInDB, userCompleteName, email)
                }
            } while (cursor.moveToNext())
        }

        cursor.close()
        return user
    }

}