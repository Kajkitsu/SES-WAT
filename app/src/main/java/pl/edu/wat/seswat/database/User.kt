package pl.edu.wat.seswat.database

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var userID: String = "brak",
    var name: String = "brak",
    var surname: String = "brak",
    @field:JvmField
    var isTeacher: Boolean = false,
    var email: String = "brak"
)

