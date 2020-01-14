package pl.edu.wat.seswat.database

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class Attendance(
    @field:JvmField
    var confirmed: Boolean = false,
    var timeOfAdd: Timestamp = Timestamp(Date(0)),
    var userID: String = "brak"
)

