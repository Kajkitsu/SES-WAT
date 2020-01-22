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
import com.spartons.qrcodegeneratorreader.ScanQrCodeActivity
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.database.FirestoreDataFunctions
import pl.edu.wat.seswat.ui.student.StudentData
import pl.edu.wat.seswat.ui.student.StudentMenuActivity
import pl.edu.wat.seswat.ui.teacher.TeacherMenuActivity
import android.content.Intent
import android.app.Activity




class SAddAttendanceFragment : Fragment(), View.OnClickListener {

    lateinit var addAttendanceButton: Button
    lateinit var codeInputEditText: EditText
    lateinit var mFunctions: FirebaseFunctions
    lateinit var mAuth: FirebaseAuth
    lateinit var data: StudentData
    lateinit var scanQRCodeButton: Button
    val TAG = "SAddAttendanceFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_s_add_attendance, container, false)

        addAttendanceButton = root.findViewById(R.id.add_attendance_button)
        codeInputEditText = root.findViewById(R.id.code_input_edit_text)
        scanQRCodeButton = root.findViewById(R.id.scan_qr_code_button)

        addAttendanceButton.setOnClickListener(this)
        scanQRCodeButton.setOnClickListener(this)


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
            Toast.makeText(this.context,(it.data as HashMap<*, *>)["status"].toString(),Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(this.context,"Failure", Toast.LENGTH_LONG).show()
            Log.w(TAG,"addAttendance:failure",it)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                val result = data!!.getStringExtra("result")
                codeInputEditText.setText(result.toString())
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult




    override fun onClick(v: View?) {
        if (v != null) {
            if(v.id == R.id.add_attendance_button){
                addAttendance(codeInputEditText.text.toString())
            }
            else if(v.id == R.id.scan_qr_code_button){
                val intent = Intent(activity, ScanQrCodeActivity::class.java)
                startActivityForResult(intent, 1);

            }
        }

    }

}