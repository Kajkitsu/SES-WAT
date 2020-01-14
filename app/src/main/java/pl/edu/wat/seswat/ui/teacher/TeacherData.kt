package pl.edu.wat.seswat.ui.teacher

import androidx.lifecycle.MutableLiveData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import pl.edu.wat.seswat.database.Attendance
import pl.edu.wat.seswat.database.AttendanceList
import pl.edu.wat.seswat.database.FirestoreDataFunctions
import pl.edu.wat.seswat.database.Subject
import java.util.*
import kotlin.collections.ArrayList

@IgnoreExtraProperties
class TeacherData(
    var selectedAttendanceList: MutableLiveData<AttendanceList>,
    var allAttendanceLists: MutableLiveData<ArrayList<AttendanceList>>,
    var allSubjects: MutableLiveData<ArrayList<Subject>>
){
    fun updateAllSubjects(){
        allSubjects=FirestoreDataFunctions().getAllSubjectList()
    }
    fun updateAllAttendanceLists(teacherID: String){
        allAttendanceLists= FirestoreDataFunctions().getTeacherAttendanceList(teacherID)
    }

}