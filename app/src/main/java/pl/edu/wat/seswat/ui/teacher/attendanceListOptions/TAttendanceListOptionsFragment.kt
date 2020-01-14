package pl.edu.wat.seswat.ui.teacher.attendanceListOptions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.database.FirestoreDataFunctions
import pl.edu.wat.seswat.database.Subject
import pl.edu.wat.seswat.ui.teacher.TeacherMenuActivity

class TAttendanceListOptionsFragment : Fragment() {


    private var TAG = "TAttendanceListOptionsFragment"



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_t_attendance_list_options, container, false)
//        val textView: TextView = root.findViewById(R.id.text_notifications)
//        settingsViewModel.text.observe(this, Observer {
//            textView.text = it
//        })

        val textView: TextView = root.findViewById(R.id.textView_choose_attendance_list)
        val layout: ConstraintLayout = root.findViewById(R.id.layoutConstraint)

        (this.activity as TeacherMenuActivity).data.selectedAttendanceList.observe(this, Observer {
            if(it!=null){
                textView.visibility=View.INVISIBLE
                layout.visibility=View.VISIBLE
            }
            else{
                layout.visibility=View.INVISIBLE
                textView.visibility=View.VISIBLE
            }
        })


        return root
    }
}