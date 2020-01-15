package pl.edu.wat.seswat.database

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.*
import kotlin.collections.ArrayList

@IgnoreExtraProperties
data class AttendanceList(
    var code: String = "brak",
    @field:JvmField
    var open: Boolean = false,
    var startDate: Timestamp = Timestamp(Date(0)),
    var stopDate: Timestamp = Timestamp(Date(0)),
    var subjectID: String = "brak",
    var subjectShortName: String = "brak",
    var teacherID: String = "brak",
    var type: String = "brak",
    var attendance: ArrayList<Attendance> = ArrayList()
)

