package pl.edu.wat.seswat.ui.settings

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
import pl.edu.wat.seswat.ui.teacher.TeacherMenuActivity

class SettingsFragment : Fragment() {

    private var TAG = "SettingsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)


        val buttonLogout: Button = root.findViewById(R.id.button_logout)
        buttonLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            this.activity?.finish()
        }


        var allSubjectList : MutableLiveData<ArrayList<Subject>> = MutableLiveData()
        val buttonTest0: Button = root.findViewById(R.id.button_test_0)
        buttonTest0.setOnClickListener {
            var data = (this.activity as TeacherMenuActivity).data

            allSubjectList = FirestoreDataFunctions().getAllSubjectList()
            Log.d(TAG,"FirestoreDataFunctions().getAllSubjecList()")

        }
        val buttonTest1: Button = root.findViewById(R.id.button_test_1)
        buttonTest1.setOnClickListener {
            var data = (this.activity as TeacherMenuActivity).data
            Log.d(TAG,"allSubjectList: "+data.allSubjects.value)
        }



        return root
    }
}