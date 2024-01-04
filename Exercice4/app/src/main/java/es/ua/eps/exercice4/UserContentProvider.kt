package es.ua.eps.exercice4

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.database.sqlite.SQLiteDatabase

class UserContentProvider : ContentProvider() {

    companion object{
        private const val TABLE_NAME = "Usuarios"

        val PROVIDER_NAME = "es.ua.eps.exercice4/UserContentProvider"
        val URL = "content://$PROVIDER_NAME/$TABLE_NAME"
        val CONTENT_URI = Uri.parse(URL)
        val id = "_ID"
        val username = "username"
        val password = "password"
        val nombreCompleto = "nombre_completo"
        val email = "email"
    }

    lateinit var appDataBase: SQLiteDatabase

    override fun onCreate(): Boolean {
        var helper = UsersSQLiteHelper(getContext())
        appDataBase = helper.writableDatabase

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return appDataBase.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder)
    }

    override fun getType(p0: Uri): String? {
        return "vnd.android.cursor.dir/vnd.es.eps.ua.exercice4.provider.$TABLE_NAME"
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        appDataBase.insert(TABLE_NAME, null, p1)
        context?.contentResolver?.notifyChange(p0, null)
        return p0
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        var count = appDataBase.delete(TABLE_NAME,  p1, p2)
        context?.contentResolver?.notifyChange(p0, null)
        return count
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        var count = appDataBase.update(TABLE_NAME, p1, p2, p3)
        context?.contentResolver?.notifyChange(p0, null)
        return count
    }
}