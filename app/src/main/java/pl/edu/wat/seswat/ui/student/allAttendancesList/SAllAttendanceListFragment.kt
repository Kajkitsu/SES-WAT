package pl.edu.wat.seswat.ui.student.allAttendancesList


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.database.Lecture
import pl.edu.wat.seswat.database.Present
import pl.edu.wat.seswat.ui.student.StudentData
import pl.edu.wat.seswat.ui.student.StudentMenuActivity
import pl.edu.wat.seswat.ui.teacher.selectAttendanceList.RecyclerViewAdapterSelectAttendanceList

class SAllAttendanceListFragment : Fragment(), View.OnClickListener {


    private var TAG = "SAllAttendanceListFragment"
    lateinit var recycler: RecyclerView
    lateinit var adapterAllAttendance: RecyclerViewAdapterAllAttendance
    var lecture = ArrayList<Lecture>()
    var present = ArrayList<Present>()
    var mAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    lateinit var mRefreshButton: Button
    lateinit var data: StudentData
    lateinit var mTestButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        data = (this.activity as StudentMenuActivity).data

        adapterAllAttendance = RecyclerViewAdapterAllAttendance(data.allAttendanceLists.value!!, data.allSubjects.value!!)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_s_all_attendance_list, container, false)


        mRefreshButton = root.findViewById(R.id.refresh_all_attendance_list_button)
        recycler = root.findViewById(R.id.recycler_view_present_list)



        root.findViewById<View>(R.id.refresh_all_attendance_list_button).setOnClickListener(this)


        data.allAttendanceLists.observe(this, Observer {
            adapterAllAttendance.setList(it, data.allSubjects.value!!)
            recycler.setAdapter(adapterAllAttendance)
            recycler.setLayoutManager(LinearLayoutManager(this.context))
        })
        data.allSubjects.observe(this, Observer {
            adapterAllAttendance.setList(data.allAttendanceLists.value!!, it)
            recycler.setAdapter(adapterAllAttendance)
            recycler.setLayoutManager(LinearLayoutManager(this.context))
        })




        return root
    }

    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.refresh_all_attendance_list_button) {
            mAuth.currentUser?.uid?.let { data.updateAllAttendanceLists(it) }
            data.updateAllSubjects()
        }
    }

//    fun updatePresentList(){
//        Log.d(TAG, "updatePresentList()")
//            db.collection("presents")
//                .whereEqualTo("userID", mAuth.currentUser?.uid).get().addOnSuccessListener { documents ->
//                    present = ArrayList<Present>()
//                    for (document in documents) {
//                        Log.d(TAG, "${document.id} => ${document.data}")
//                        present.add(document.toObject(Present::class.java))
//                        getSubjectName(document.toObject(Present::class.java).lectureID)
//                    }
//                    updateRecyclerView()
//                }
//                .addOnFailureListener { exception ->
//                    Log.w(TAG, "Error getting documents: ", exception)
//                }
//
//    }
//
//    fun getSubjectName(classesID:String){
//        Log.d(TAG, "getSubjectName()")
//
//        db.collection("lectures").document(classesID).get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    lecture.add(document.toObject(Lecture::class.java)!!)
//                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
//                    updateRecyclerView()
//                } else {
//                    Log.d(TAG, "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d(TAG, "get failed with ", exception)
//            }
//
//    }

//    fun setPresentList(){
//        Log.d(TAG, "setPresentList()")
//        db.collection("presents")
//            .whereEqualTo("userID", mAuth.currentUser?.uid).get().addOnSuccessListener { documents ->
//                present = ArrayList<Present>()
//                for (document in documents) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
//                    present.add(document.toObject(Present::class.java))
//
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents: ", exception)
//            }
//
//    }


}