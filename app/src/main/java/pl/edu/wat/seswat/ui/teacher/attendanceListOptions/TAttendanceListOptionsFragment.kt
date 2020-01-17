package pl.edu.wat.seswat.ui.teacher.attendanceListOptions

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.database.AttendenceList
import pl.edu.wat.seswat.ui.teacher.TeacherData
import pl.edu.wat.seswat.ui.teacher.TeacherMenuActivity

class TAttendanceListOptionsFragment : Fragment(), View.OnClickListener {


    private var TAG = "TAttendanceListOptionsFragment"
    lateinit var data: TeacherData
    lateinit var recyclerViewAdapterAttendanceListOptions: RecyclerViewAdapterAttendanceListOptions
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = (this.activity as TeacherMenuActivity).data
        mAuth = FirebaseAuth.getInstance()
        if(data.selectedAttendenceList.value!=null){
            recyclerViewAdapterAttendanceListOptions = RecyclerViewAdapterAttendanceListOptions(data.selectedAttendenceList.value!!)
        }
        else{
            recyclerViewAdapterAttendanceListOptions = RecyclerViewAdapterAttendanceListOptions(AttendenceList())
        }
    }



    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_t_attendance_list_options, container, false)
        val textView: TextView = root.findViewById(R.id.textView_choose_attendance_list)
        val layout: ConstraintLayout = root.findViewById(R.id.layoutConstraint)

        root.findViewById<Button>(R.id.refresh_attendance_list_options_button).setOnClickListener(this)


        data.selectedAttendenceList.observe(this, Observer {
            if(it!=null){
                textView.visibility=View.INVISIBLE
                layout.visibility=View.VISIBLE

                recyclerViewAdapterAttendanceListOptions.setList(it)

                root.findViewById<RecyclerView>(R.id.recycler_view_present_list).setAdapter(recyclerViewAdapterAttendanceListOptions)
                root.findViewById<RecyclerView>(R.id.recycler_view_present_list).setLayoutManager(LinearLayoutManager(this.context))


                root.findViewById<TextView>(R.id.textView_code).text="Kod: "+it.code
                root.findViewById<TextView>(R.id.textView_no_students).text="Liczba studentów: "+it.attendence.size
                root.findViewById<TextView>(R.id.textView_no_confirmed_students).text="Liczba potwierdzonych studentów: "+data.getConfiremdNOStudents()
                if(it.open){
                    root.findViewById<Switch>(R.id.switch_list).text="Otwarte"
                    root.findViewById<Switch>(R.id.switch_list).setChecked(true)
                }
                else{
                    root.findViewById<Switch>(R.id.switch_list).text="Zamknięte"
                    root.findViewById<Switch>(R.id.switch_list).setChecked(false)
                }

            }
            else{
                layout.visibility=View.INVISIBLE
                textView.visibility=View.VISIBLE

            }
        })

        return root
    }

    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.refresh_attendance_list_options_button) {
            Log.d("DUPA","CYCKI")
            mAuth.currentUser?.uid?.let { data.updateAllAttendanceLists(it)
            }
            data.updateSelectedAttendanceList()
            data.updateAllSubjects()


        }
    }
}