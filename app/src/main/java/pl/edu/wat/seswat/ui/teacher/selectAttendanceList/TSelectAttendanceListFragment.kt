package pl.edu.wat.seswat.ui.teacher.selectAttendanceList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.ui.teacher.TeacherData
import pl.edu.wat.seswat.ui.teacher.TeacherMenuActivity



class TSelectAttendanceListFragment : Fragment(), View.OnClickListener {


    lateinit var recycler: RecyclerView
    lateinit var createNewListButton: Button
    lateinit var refreshListsButton: Button
    lateinit var spinner: Spinner
    lateinit var adapterAttendance: RecyclerViewAdapterSelectAttendanceList
    lateinit var spinnerAdapter: ArrayAdapter<String>
    lateinit var data: TeacherData
    lateinit var mAuth: FirebaseAuth
    lateinit var mFunctions: FirebaseFunctions
    var subjectList: ArrayList<String> = ArrayList()
    val TAG = "SelectAttendanceListFr"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        mFunctions = FirebaseFunctions.getInstance()
        data = (this.activity as TeacherMenuActivity).data

        adapterAttendance = RecyclerViewAdapterSelectAttendanceList(data.allAttendenceLists,data.selectedAttendenceList,this.context)


        subjectList=ArrayList()
        if(data.allSubjects.value!=null)
        for (subject in data.allSubjects.value!!){
            subjectList.add(subject.name)
        }

        spinnerAdapter = ArrayAdapter(this.context!!, R.layout.support_simple_spinner_dropdown_item, subjectList)


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_t_select_attendance_list, container, false)

        spinner = root.findViewById(R.id.spinner_lecture)
        recycler = root.findViewById(R.id.recycler_view_select_list)
        createNewListButton = root.findViewById(R.id.create_new_list)
        refreshListsButton = root.findViewById(R.id.refresh_select_attendance_list)

        refreshListsButton.setOnClickListener(this)
        createNewListButton.setOnClickListener(this)


//        if(data.allSubjects.value!=null){
//            for (subject in data.allSubjects.value!!){
//                subjectList.add(subject.name.toString())
//                Log.d("DUPA",subject.name.toString())
//            }
//        }


        data.allSubjects.observe(this, Observer {
            Log.d("DUPA","DUPA")
            subjectList=ArrayList()
            for (subject in it){
                subjectList.add(subject.name)
            }
            spinnerAdapter = ArrayAdapter(this.context!!, R.layout.support_simple_spinner_dropdown_item, subjectList)
            spinner.adapter=spinnerAdapter

        })
        data.allAttendenceLists.observe(this, Observer {
            adapterAttendance.setList(it)
            recycler.setAdapter(adapterAttendance)
            recycler.setLayoutManager(LinearLayoutManager(this.context))
        })

        //spinner.adapter = ArrayAdapter(this.context!!, R.layout.support_simple_spinner_dropdown_item, subjectList)

        return root
    }

    fun createNewList(name:String){
        mFunctions.getHttpsCallable("createAttendanceList").call(
            hashMapOf(
                "name" to name
            )

        ).addOnSuccessListener {
            Log.d(TAG, it.toString())
            Toast.makeText(this.context,"Sukces",Toast.LENGTH_LONG).show()
            mAuth.currentUser?.uid?.let { data.updateAllAttendanceLists(it)
            }
            data.updateSelectedAttendanceList()
        }.addOnFailureListener {
            Toast.makeText(this.context,"Failure",Toast.LENGTH_LONG).show()
            Log.w(TAG,"addUserToListFunction:failure",it)
        }
    }

    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.refresh_select_attendance_list) {
            mAuth.currentUser?.uid?.let { data.updateAllAttendanceLists(it)
            }
            data.updateSelectedAttendanceList()
            data.updateAllSubjects()
        } else if (i == R.id.create_new_list){
            createNewList(spinner.selectedItem.toString())
        }
    }

}
