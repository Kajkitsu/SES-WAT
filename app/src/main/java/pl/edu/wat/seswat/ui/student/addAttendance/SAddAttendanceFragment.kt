package pl.edu.wat.seswat.ui.student.addAttendance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.ui.teacher.selectAttendanceList.TSelectAttendanceListViewModel

class SAddAttendanceFragment : Fragment() {

    private lateinit var tSelectAttendanceListViewModel: SAddAttendanceViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tSelectAttendanceListViewModel =
            ViewModelProviders.of(this).get(SAddAttendanceViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_s_add_attendance, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        addPresentViewModelAttendance.text.observe(this, Observer {
//            textView.text = it
//        })
        return root
    }
}