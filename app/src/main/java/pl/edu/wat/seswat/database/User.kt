package pl.edu.wat.seswat.database

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var userID: String? = null,
    var name: String? = null,
    var surname: String? = null,
    @field:JvmField
    var isTeacher: Boolean? = null,
    var email: String? = null
)

