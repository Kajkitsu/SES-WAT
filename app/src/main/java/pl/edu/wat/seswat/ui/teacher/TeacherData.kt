package pl.edu.wat.seswat.ui.teacher

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.IgnoreExtraProperties
import pl.edu.wat.seswat.database.AttendanceList
import pl.edu.wat.seswat.database.FirestoreDataFunctions
import pl.edu.wat.seswat.database.Subject
import kotlin.collections.ArrayList

@IgnoreExtraProperties
class TeacherData(
    var selectedAttendanceList: MutableLiveData<AttendanceList>,
    var allAttendanceLists: MutableLiveData<ArrayList<AttendanceList>>,
    var allSubjects: MutableLiveData<ArrayList<Subject>>
){
    fun updateAllSubjects(){
        FirestoreDataFunctions().getAllSubjectList(allSubjects)
    }

    fun updateSelectedAttendanceList(){
        selectedAttendanceList.value?.code?.let {
            FirestoreDataFunctions().getSelectedAttendanceList(it,selectedAttendanceList)
        }
    }

    fun updateAllAttendanceLists(teacherID: String){
        FirestoreDataFunctions().getAllAttendanceListOfTeacher(teacherID,allAttendanceLists)
    }

    fun getConfiremdNOStudents(): Int {
        var list= this.selectedAttendanceList.value?.attendance
        var NumberOfConfirmed=0
        if (list != null) {
            for(attendance in list){
                if(attendance.confirmed) NumberOfConfirmed++
            }
        }
        return NumberOfConfirmed
    }


}