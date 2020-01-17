package pl.edu.wat.seswat.ui.student.allAttendancesList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.ui.student.StudentData
import pl.edu.wat.seswat.ui.student.StudentMenuActivity

class SAllAttendanceListFragment : Fragment(), View.OnClickListener {


    private var TAG = "SAllAttendanceListFragment"
    lateinit var recycler: RecyclerView
    lateinit var adapterAllAttendance: RecyclerViewAdapterAllAttendance
    lateinit var mAuth: FirebaseAuth
    lateinit var data: StudentData
    lateinit var mRefreshButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        data = (this.activity as StudentMenuActivity).data
        if(data.allAttendenceLists.value==null) data.allAttendenceLists.value= ArrayList()
        if(data.allSubjects.value==null) data.allSubjects.value=ArrayList()
        adapterAllAttendance = RecyclerViewAdapterAllAttendance(data.allAttendenceLists.value!!, data.allSubjects.value!!)

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


        data.allAttendenceLists.observe(this, Observer {
            adapterAllAttendance.setList(it, data.allSubjects.value!!)
            recycler.setAdapter(adapterAllAttendance)
            recycler.setLayoutManager(LinearLayoutManager(this.context))
        })
        data.allSubjects.observe(this, Observer {
            adapterAllAttendance.setList(data.allAttendenceLists.value!!, it)
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
}