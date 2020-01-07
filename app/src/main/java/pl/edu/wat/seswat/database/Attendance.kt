package pl.edu.wat.seswat.database

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Attendance(
    @field:JvmField
    var confirmed: Boolean? = null,
    var timeOfAdd: Timestamp? = null,
    var userID: String? = null
)

