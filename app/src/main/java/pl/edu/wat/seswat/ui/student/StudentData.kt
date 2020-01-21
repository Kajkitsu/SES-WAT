package pl.edu.wat.seswat.ui.student

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.IgnoreExtraProperties
import pl.edu.wat.seswat.database.AttendenceList
import pl.edu.wat.seswat.database.FirestoreDataFunctions
import pl.edu.wat.seswat.database.Subject
import kotlin.collections.ArrayList

@IgnoreExtraProperties
class StudentData(
    var allAttendenceLists: MutableLiveData<ArrayList<AttendenceList>>,
    var allSubjects: MutableLiveData<ArrayList<Subject>>
)