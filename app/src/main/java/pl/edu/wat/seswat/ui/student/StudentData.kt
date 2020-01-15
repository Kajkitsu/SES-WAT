package pl.edu.wat.seswat.ui.student

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.IgnoreExtraProperties
import pl.edu.wat.seswat.database.AttendanceList
import pl.edu.wat.seswat.database.FirestoreDataFunctions
import pl.edu.wat.seswat.database.Subject
import kotlin.collections.ArrayList

@IgnoreExtraProperties
class StudentData(
    var allAttendanceLists: MutableLiveData<ArrayList<AttendanceList>>,
    var allSubjects: MutableLiveData<ArrayList<Subject>>
){
    fun updateAllSubjects(){
        FirestoreDataFunctions().getAllSubjectList(allSubjects)
    }

    fun updateAllAttendanceLists(teacherID: String){
        FirestoreDataFunctions().getAllAttendanceListOfTeacher(teacherID,allAttendanceLists)
    }


}