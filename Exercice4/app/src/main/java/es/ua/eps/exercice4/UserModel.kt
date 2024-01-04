package es.ua.eps.exercice4

class UserModel(
                newNombre_usuario : String,
                newPassword : String,
                newNombre_completo : String,
                newEmail : String)  {
    private var ID = 0
    private var nombre_usuario = newNombre_usuario
    private var password = newPassword
    private var nombre_completo = newNombre_completo
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

    fun toPrint() : String
    {
        var string = ""

        string += "${ID.toString()} ${nombre_usuario} ${password} ${nombre_completo} ${email}"
        return string
    }
}