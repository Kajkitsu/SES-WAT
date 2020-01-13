package pl.edu.wat.seswat.database

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.*
import kotlin.collections.ArrayList

@IgnoreExtraProperties
data class AttendanceList(
    var code: String? = null,
    @field:JvmField
    var isOpen: Boolean = false,
    var startDate: Timestamp? = Timestamp(Date(0)),
    var stopDate: Timestamp? = Timestamp(Date(0)),
    var subjectID: String? = null,
    var subjectShortName: String? = null,
    var teacherID: String? = null,
    var type: String? = null,
    var attendance: ArrayList<Attendance>? = null
)

