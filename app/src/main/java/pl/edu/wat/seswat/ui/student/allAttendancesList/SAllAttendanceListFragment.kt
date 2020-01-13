package pl.edu.wat.seswat.ui.student.allAttendancesList


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.database.Lecture
import pl.edu.wat.seswat.database.Present

class SAllAttendanceListFragment : Fragment(), View.OnClickListener {

    private lateinit var sAllAttendanceListViewModel: SAllAttendanceListViewModel
    private var TAG = "SAllAttendanceListFragment"
    lateinit var recycler: RecyclerView
    lateinit var adapterAllAttendance: RecyclerViewAdapterAllAttendance
    var lecture = ArrayList<Lecture>()
    var present = ArrayList<Present>()
    var mAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    lateinit var mRefreshButton: Button
    lateinit var mTestButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sAllAttendanceListViewModel =
            ViewModelProviders.of(this).get(SAllAttendanceListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_s_all_attendance_list, container, false)
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        SAllAttendanceListViewModel.text.observe(this, Observer {
//            textView.text = it
//        })


        mRefreshButton = root.findViewById(R.id.refresh_button)

        // Buttons
        root.findViewById<View>(R.id.refresh_button).setOnClickListener(this)
        recycler = root.findViewById(R.id.recycler_view_present_list)

        updatePresentList()

        return root
    }



    fun updateRecyclerView(){
        adapterAllAttendance =
            RecyclerViewAdapterAllAttendance(present, lecture)
        recycler.setAdapter(adapterAllAttendance)
        recycler.setLayoutManager(LinearLayoutManager(this.context))
    }

    fun updatePresentList(){
        Log.d(TAG, "updatePresentList()")
            db.collection("presents")
                .whereEqualTo("userID", mAuth.currentUser?.uid).get().addOnSuccessListener { documents ->
                    present = ArrayList<Present>()
                    for (document in documents) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        present.add(document.toObject(Present::class.java))
                        getSubjectName(document.toObject(Present::class.java).lectureID!!)
                    }
                    updateRecyclerView()
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }

    }

    fun getSubjectName(classesID:String){
        Log.d(TAG, "getSubjectName()")

        db.collection("lectures").document(classesID).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    lecture.add(document.toObject(Lecture::class.java)!!)
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    updateRecyclerView()
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

    }

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

    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.refresh_button) {
            updatePresentList()
        }
    }





}