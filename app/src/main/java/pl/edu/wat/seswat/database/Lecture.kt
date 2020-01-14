package pl.edu.wat.seswat.database

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Lecture(
    var lectureID: String = "brak",
    var teacherID: String = "brak",
    var subjectName: String = "brak",
    var numberOfPresentStudents: Int = 0,
    var code: Int = 0,
    var isOpenEntries: Boolean = false,
    var timeOfStartClasses: String = "brak",
    var type: String = "brak"
)

