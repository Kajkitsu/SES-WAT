package pl.edu.wat.seswat.ui.addAttendance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import pl.edu.wat.seswat.R

class AddAttendanceFragment : Fragment() {

    private lateinit var addPresentViewModelAttendance: SelectAttendanceListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addPresentViewModelAttendance =
            ViewModelProviders.of(this).get(SelectAttendanceListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_attendance, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        addPresentViewModelAttendance.text.observe(this, Observer {
//            textView.text = it
//        })
        return root
    }
}