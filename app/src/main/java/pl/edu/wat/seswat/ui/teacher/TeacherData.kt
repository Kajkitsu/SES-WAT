package pl.edu.wat.seswat.ui.teacher

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.IgnoreExtraProperties
import pl.edu.wat.seswat.database.AttendenceList
import pl.edu.wat.seswat.database.FirestoreDataFunctions
import pl.edu.wat.seswat.database.Subject
import kotlin.collections.ArrayList

@IgnoreExtraProperties
class TeacherData(
    var selectedAttendenceList: MutableLiveData<AttendenceList>,
    var allAttendenceLists: MutableLiveData<ArrayList<AttendenceList>>,
    var allSubjects: MutableLiveData<ArrayList<Subject>>
){
    fun updateAllSubjects(){
        FirestoreDataFunctions().getAllSubjectList(allSubjects)
    }

    fun updateSelectedAttendanceList(){
        selectedAttendenceList.value?.code?.let {
            FirestoreDataFunctions().getSelectedAttendanceList(it,selectedAttendenceList)
        }
    }

    fun updateAllAttendanceLists(teacherID: String){
        FirestoreDataFunctions().getAllAttendanceListOfTeacher(teacherID,allAttendenceLists)
    }

    fun getConfiremdNOStudents(): Int {
        var list= this.selectedAttendenceList.value?.attendence
        var NumberOfConfirmed=0
        if (list != null) {
            for(attendance in list){
                if(attendance.confirmed) NumberOfConfirmed++
            }
        }
        return NumberOfConfirmed
    }


}