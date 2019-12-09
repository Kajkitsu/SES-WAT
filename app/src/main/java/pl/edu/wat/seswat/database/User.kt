package pl.edu.wat.seswat.database

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var userID: String?,
    var name: String?,
    var surname: String?,
    var isTeacher: Boolean?,
    var email: String?
)

