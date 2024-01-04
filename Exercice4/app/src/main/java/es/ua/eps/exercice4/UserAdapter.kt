package es.ua.eps.exercice4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var users: ArrayList<UserEntity> = ArrayList()

    fun addItems(items: ArrayList<UserEntity>){
        this.users = items
        notifyDataSetChanged()
    }

    class UserViewHolder(view: View): RecyclerView.ViewHolder(view){
        private var userName = view.findViewById<TextView>(R.id.userLabel)
        private var userfullName = view.findViewById<TextView>(R.id.fullnameLabel)

        fun bindView(user : UserEntity){
            userName.text = user.getField_userName()
            userfullName.text = user.getField_completeName()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
    )

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bindView(user)
    }

}