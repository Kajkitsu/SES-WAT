package pl.edu.wat.seswat.ui.student.addAttendance

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.ui.student.StudentData
import pl.edu.wat.seswat.ui.student.StudentMenuActivity

class SAddAttendanceFragment : Fragment(), View.OnClickListener {

    lateinit var addAttendanceButton: Button
    lateinit var codeInputEditText: TextInputEditText
    lateinit var mFunctions: FirebaseFunctions
    lateinit var mAuth: FirebaseAuth
    lateinit var data: StudentData
    val TAG = "SAddAttendanceFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_s_add_attendance, container, false)

        addAttendanceButton = root.findViewById(R.id.add_attendance_button)
        addAttendanceButton.setOnClickListener(this)
        codeInputEditText = root.findViewById(R.id.code_input_edit_text)


        mFunctions = FirebaseFunctions.getInstance()
        mAuth = FirebaseAuth.getInstance()
        data = (this.activity as StudentMenuActivity).data




        return root
    }

    fun addAttendance(code: String){
        mFunctions.getHttpsCallable("signToList").call(
            hashMapOf(
                "code" to code
            )
        ).addOnSuccessListener {
            Log.d(TAG, it.toString())
            Log.d(TAG,it.data.toString())
            Toast.makeText(this.context,"Sukces", Toast.LENGTH_LONG).show()
            mAuth.currentUser?.uid?.let { data.updateAllAttendanceLists(it) }
            data.updateAllSubjects()
        }.addOnFailureListener {
            Toast.makeText(this.context,"Failure", Toast.LENGTH_LONG).show()
            Log.w(TAG,"addAttendance:failure",it)
        }

    }




    override fun onClick(v: View?) {
        if (v != null) {
            if(v.id == R.id.add_attendance_button){
                addAttendance(codeInputEditText.text.toString())
            }
        }
    }

}