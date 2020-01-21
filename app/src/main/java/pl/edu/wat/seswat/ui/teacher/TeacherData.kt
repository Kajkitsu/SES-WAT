package pl.edu.wat.seswat.ui.teacher

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.IgnoreExtraProperties
import pl.edu.wat.seswat.database.AttendenceList
import pl.edu.wat.seswat.database.FirestoreDataFunctions
import pl.edu.wat.seswat.database.Subject
import pl.edu.wat.seswat.database.User
import pl.edu.wat.seswat.ui.teacher.selectAttendanceList.RecyclerViewAdapterSelectAttendanceList
import kotlin.collections.ArrayList

@IgnoreExtraProperties
class TeacherData(
    var allAttendenceLists: MutableLiveData<ArrayList<AttendenceList>>,
    var allSubjects: MutableLiveData<ArrayList<Subject>>,
    var allStudents: MutableLiveData<ArrayList<User>>
){
    var selectedAttendenceList: MutableLiveData<AttendenceList> = MutableLiveData()

    init {
        selectedAttendenceList.value=null
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

    fun updateAttendenceList() {
        if(allAttendenceLists.value!=null){
            for (tmpAttedenceList in allAttendenceLists.value!!){
                if(tmpAttedenceList.code == selectedAttendenceList.value?.code) selectedAttendenceList.value=tmpAttedenceList
            }
        }

    }


}