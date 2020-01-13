package pl.edu.wat.seswat.ui.teacher.selectAttendanceList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.database.FirestoreDataFunctions

class TSelectAttendanceListFragment : Fragment() {

    private lateinit var tSelectAttendanceListViewModel: TSelectAttendanceListViewModel
    lateinit var recycler: RecyclerView
    var adapterAttendance: RecyclerViewAdapterSelectAttendanceList = RecyclerViewAdapterSelectAttendanceList(MutableLiveData(),this.context)
    val TAG = "SelectAttendanceListFr"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tSelectAttendanceListViewModel =
            ViewModelProviders.of(this).get(TSelectAttendanceListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_t_select_attendance_list, container, false)

        val spinnerSubject: Spinner = root.findViewById(R.id.spinner_lecture)
        recycler = root.findViewById(R.id.recycler_view_select_list)



        FirestoreDataFunctions().getAllSubjectList().observe(this, Observer {
            if(it!=null) {
                Log.d(TAG, it.toString())

                val listArray: ArrayList<String> = ArrayList()
                for (subject in it){
                    listArray.add(subject.name.toString())
                }

                val spinnerAdapter: ArrayAdapter<String> =
                    ArrayAdapter(this.context!!, R.layout.support_simple_spinner_dropdown_item, listArray)
                spinnerSubject.adapter = spinnerAdapter
            }
        })

        initRecyclerView()



//        val textView: TextView = root.findViewById(R.id.text_home)
//        TSelectAttendanceListViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        return root
    }

    fun initRecyclerView(){
        var mAuth = FirebaseAuth.getInstance()
        recycler.setAdapter(adapterAttendance)
        recycler.setLayoutManager(LinearLayoutManager(this.context))

        mAuth.currentUser?.uid?.let { FirestoreDataFunctions().getTeacherAttendanceList(it).observe(this, Observer {
            Log.d(TAG," FirestoreDataFunctions().getTeacherAttendanceList(it).observe")
            adapterAttendance.setList(it)
        })

        }



    }


    fun updateRecyclerView(){
        adapterAttendance =
            RecyclerViewAdapterSelectAttendanceList(MutableLiveData(),this.context)
        recycler.setAdapter(adapterAttendance)
        recycler.setLayoutManager(LinearLayoutManager(this.context))

    }
}