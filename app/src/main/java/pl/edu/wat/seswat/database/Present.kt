package pl.edu.wat.seswat.database

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Present(
    var presentID: String?,
    var classID: String?,
    var userID: String?,
    var timeOfAddToList: String?,
    var confirmed: Boolean?
)

