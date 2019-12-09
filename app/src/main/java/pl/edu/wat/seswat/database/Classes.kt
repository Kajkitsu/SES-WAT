package pl.edu.wat.seswat.database

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Classes(
    var classesID: String?,
    var teacherID: String?,
    var subjectName: String?,
    var numberOfPresentStudents: Int?,
    var code: Int?,
    var isOpenEntries: Boolean?,
    var timeOfStartClasses: String?,
    var type: String?
)

