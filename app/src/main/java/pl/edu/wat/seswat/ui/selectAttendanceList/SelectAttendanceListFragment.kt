package pl.edu.wat.seswat.ui.addAttendance

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.database.FirestoreDataFunctions

class SelectAttendanceListFragment : Fragment() {

    private lateinit var selectAttendanceListViewModel: SelectAttendanceListViewModel
    val TAG = "SelectAttendanceListFr"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        selectAttendanceListViewModel =
            ViewModelProviders.of(this).get(SelectAttendanceListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_select_attendance_list, container, false)

        val spinnerSubject: Spinner = root.findViewById(R.id.spinner_lecture)
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

//        val textView: TextView = root.findViewById(R.id.text_home)
//        selectAttendanceListViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        return root
    }
}