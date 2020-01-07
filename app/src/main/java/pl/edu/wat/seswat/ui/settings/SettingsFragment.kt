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

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private var TAG = "SettingsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
            ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        settingsViewModel.text.observe(this, Observer {
            textView.text = it
        })


        val buttonLogout: Button = root.findViewById(R.id.button_logout)
        buttonLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            this.activity?.finish()
        }


        var allSubjectList : MutableLiveData<ArrayList<Subject>> = MutableLiveData()
        val buttonTest0: Button = root.findViewById(R.id.button_test_0)
        buttonTest0.setOnClickListener {
            allSubjectList = FirestoreDataFunctions().getAllSubjectList()
            Log.d(TAG,"FirestoreDataFunctions().getAllSubjecList()")

        }
        val buttonTest1: Button = root.findViewById(R.id.button_test_1)
        buttonTest1.setOnClickListener {
            Log.d(TAG,"allSubjectList: "+allSubjectList.value)
        }



        return root
    }
}