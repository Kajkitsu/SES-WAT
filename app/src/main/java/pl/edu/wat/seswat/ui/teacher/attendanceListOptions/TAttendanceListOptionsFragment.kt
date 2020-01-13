package pl.edu.wat.seswat.ui.teacher.attendanceListOptions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.database.FirestoreDataFunctions
import pl.edu.wat.seswat.database.Subject
import pl.edu.wat.seswat.ui.settings.SettingsViewModel

class TAttendanceListOptionsFragment : Fragment() {

    private lateinit var tAttendanceListOptionsViewModel: TAttendanceListOptionsViewModel
    private var TAG = "TAttendanceListOptionsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tAttendanceListOptionsViewModel =
            ViewModelProviders.of(this).get(TAttendanceListOptionsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_t_attendance_list_options, container, false)
//        val textView: TextView = root.findViewById(R.id.text_notifications)
//        settingsViewModel.text.observe(this, Observer {
//            textView.text = it
//        })


        return root
    }
}