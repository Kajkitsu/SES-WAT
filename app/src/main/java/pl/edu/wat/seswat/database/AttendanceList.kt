package pl.edu.wat.seswat.database

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class AttendanceList(
    var code: String? = null,
    @field:JvmField
    var isOpen: Boolean? = null,
    var startDate: Timestamp? = null,
    var stopDate: Timestamp? = null,
    var subjectID: String? = null,
    var subjectShortName: String? = null,
    var teacherID: String? = null,
    var type: String? = null,
    var attendance: ArrayList<Attendance>? = null
)

