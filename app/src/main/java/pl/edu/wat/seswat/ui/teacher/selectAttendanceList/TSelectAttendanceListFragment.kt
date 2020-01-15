package pl.edu.wat.seswat.ui.teacher.selectAttendanceList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.database.FirestoreDataFunctions
import pl.edu.wat.seswat.ui.teacher.TeacherData
import pl.edu.wat.seswat.ui.teacher.TeacherMenuActivity



class TSelectAttendanceListFragment : Fragment(), View.OnClickListener {


    lateinit var recycler: RecyclerView
    lateinit var spinner: Spinner
    lateinit var adapterAttendance: RecyclerViewAdapterSelectAttendanceList
    lateinit var spinnerAdapter: ArrayAdapter<String>
    lateinit var data: TeacherData
    lateinit var mAuth: FirebaseAuth
    var subjectList: ArrayList<String> = ArrayList()
    val TAG = "SelectAttendanceListFr"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        data = (this.activity as TeacherMenuActivity).data

        adapterAttendance = RecyclerViewAdapterSelectAttendanceList(data.allAttendanceLists,data.selectedAttendanceList,this.context)


        subjectList=ArrayList()
        if(data.allSubjects.value!=null)
        for (subject in data.allSubjects.value!!){
            subjectList.add(subject.name)
        }
        spinnerAdapter = ArrayAdapter(this.context!!, R.layout.support_simple_spinner_dropdown_item, subjectList)



        data.allAttendanceLists.observe(this, Observer {
            adapterAttendance.setList(it)
        })

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_t_select_attendance_list, container, false)

        spinner = root.findViewById(R.id.spinner_lecture)


//        if(data.allSubjects.value!=null){
//            for (subject in data.allSubjects.value!!){
//                subjectList.add(subject.name.toString())
//                Log.d("DUPA",subject.name.toString())
//            }
//        }
        recycler = root.findViewById(R.id.recycler_view_select_list)

        data.allSubjects.observe(this, Observer {
            Log.d("DUPA","DUPA")
            subjectList=ArrayList()
            for (subject in it){
                subjectList.add(subject.name)
            }
            spinnerAdapter = ArrayAdapter(this.context!!, R.layout.support_simple_spinner_dropdown_item, subjectList)
            spinner.adapter=spinnerAdapter
            recycler.setAdapter(adapterAttendance)
            recycler.setLayoutManager(LinearLayoutManager(this.context))
        })

        //spinner.adapter = ArrayAdapter(this.context!!, R.layout.support_simple_spinner_dropdown_item, subjectList)




        root.findViewById<Button>(R.id.refresh_select_attendance_list).setOnClickListener(this)


//        initRecyclerView()
//        updateRecyclerView()




//        val textView: TextView = root.findViewById(R.id.text_home)
//        TSelectAttendanceListViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.refresh_select_attendance_list) {
            mAuth.currentUser?.uid?.let { data.updateAllAttendanceLists(it)
            }
            data.updateSelectedAttendanceList()
            data.updateAllSubjects()


        }
    }

//    fun initRecyclerView(){
//        var mAuth = FirebaseAuth.getInstance()
//        recycler.setAdapter(adapterAttendance)
//        recycler.setLayoutManager(LinearLayoutManager(this.context))
//
//        mAuth.currentUser?.uid?.let { FirestoreDataFunctions().getAllAttendanceListOfTeacher(it).observe(this, Observer {
//            Log.d(TAG," FirestoreDataFunctions().getAllAttendanceListOfTeacher(it).observe")
//            adapterAttendance?.setList(it)
//        })
//
//        }
//
//
//
//    }
//
//
//    fun updateRecyclerView(){
//        adapterAttendance =
//            RecyclerViewAdapterSelectAttendanceList(MutableLiveData(),(this.activity as TeacherMenuActivity).selectedAttendanceList,this.context)
//        recycler.setAdapter(adapterAttendance)
//        recycler.setLayoutManager(LinearLayoutManager(this.context))
//
//    }
}