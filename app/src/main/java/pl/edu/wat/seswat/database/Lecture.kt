package pl.edu.wat.seswat.database

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Lecture(
    var lectureID: String? = null,
    var teacherID: String? = null,
    var subjectName: String? = null,
    var numberOfPresentStudents: Int? = null,
    var code: Int? = null,
    var isOpenEntries: Boolean? = null,
    var timeOfStartClasses: String? = null,
    var type: String? = null
)

