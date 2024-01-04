package es.ua.eps.exercice4

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
public interface UserDao {

    @Query("SELECT * FROM user_database")
    fun getAllUsers() : ArrayList<UserEntity>

    @Update
    fun update(user : UserEntity)

    @Insert
    fun insert(user : UserEntity)

    @Delete
    fun delete(user: UserEntity)
    @Query("SELECT * FROM user_database WHERE nombre_usuario = :userName AND password =:password")
    fun login(userName: String, password:String): UserEntity?


}