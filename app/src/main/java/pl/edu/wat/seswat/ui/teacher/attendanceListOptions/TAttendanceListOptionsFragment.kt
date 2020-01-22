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
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import kotlinx.android.synthetic.main.fragment_t_attendance_list_options.*
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.database.AttendenceList
import pl.edu.wat.seswat.database.FunctionCaller
import pl.edu.wat.seswat.ui.teacher.TeacherData
import pl.edu.wat.seswat.ui.teacher.TeacherMenuActivity
import java.util.*
import kotlin.collections.HashMap

class TAttendanceListOptionsFragment : Fragment(), View.OnClickListener {


    private var TAG = "AttendanceListOptions"
    lateinit var data: TeacherData
    lateinit var recyclerViewAdapterAttendanceListOptions: RecyclerViewAdapterAttendanceListOptions
    lateinit var refreshAttendanceListButton: Button
    lateinit var openOrCloseSwitch: Switch
    lateinit var mAuth: FirebaseAuth
    lateinit var mFunctions: FirebaseFunctions
    lateinit var mRoot: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = (this.activity as TeacherMenuActivity).data
        mAuth = FirebaseAuth.getInstance()
        mFunctions = FirebaseFunctions.getInstance()

        initRecyclerView()



    }

    fun initRecyclerView(){

        recyclerViewAdapterAttendanceListOptions = RecyclerViewAdapterAttendanceListOptions(data.selectedAttendenceList.value,data.allStudents.value)

        recyclerViewAdapterAttendanceListOptions.setConfirmStudentFunction{ code,userID ->
            FunctionCaller(FirebaseFunctions.getInstance()).confirmStudent(code,userID)
                .addOnSuccessListener{
                    Toast.makeText(this.context,(it.data as HashMap<*, *>)["status"].toString(),Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener{
                    Toast.makeText(this.context,"Błąd: "+it.cause,Toast.LENGTH_LONG).show()
                }
        }
        recyclerViewAdapterAttendanceListOptions.setCancleConfirmationStudentFunction{ code,userID ->
            FunctionCaller(FirebaseFunctions.getInstance()).cancelConfirmationStudent(code,userID)
                .addOnSuccessListener{
                    updateData()
                    Toast.makeText(this.context,(it.data as HashMap<*, *>)["status"].toString(),Toast.LENGTH_LONG).show()
                }.addOnFailureListener{
                    Toast.makeText(this.context,"Błąd: "+it.cause,Toast.LENGTH_LONG).show()
                }
        }



    }



    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_t_attendance_list_options, container, false)
        mRoot = root

        refreshAttendanceListButton = root.findViewById(R.id.refresh_attendance_list_options_button)
        openOrCloseSwitch = root.findViewById(R.id.switch_open_or_close)

        refreshAttendanceListButton.setOnClickListener(this)
        openOrCloseSwitch.setOnClickListener(this)


        setAdapterAndRecyclerView(root)

        data.selectedAttendenceList.observe(this, Observer {
            Log.d(TAG,"selectedAttendenceList.observe"+it.attendence.toString())
            setAdapterAndRecyclerView(root)
        })
        data.allAttendenceLists.observe(this, Observer {
            Log.d(TAG,"allAttendenceLists.observe"+it.toString())
            data.updateAttendenceList()
        })

        return root
    }

    private fun setAdapterAndRecyclerView(
        root: View
    ) {
        val textView: TextView = root.findViewById(R.id.textView_choose_attendance_list)
        val layout: ConstraintLayout = root.findViewById(R.id.layoutConstraint)
        var selectedAttendenceList = data.selectedAttendenceList.value
        if(selectedAttendenceList!=null){
            textView.visibility=View.INVISIBLE
            layout.visibility=View.VISIBLE


            recyclerViewAdapterAttendanceListOptions.setList(selectedAttendenceList,data.allStudents.value)
            root.findViewById<RecyclerView>(R.id.recycler_view_present_list).setAdapter(recyclerViewAdapterAttendanceListOptions)
            root.findViewById<RecyclerView>(R.id.recycler_view_present_list).setLayoutManager(LinearLayoutManager(this.context))


            root.findViewById<TextView>(R.id.textView_code).text="Kod: "+selectedAttendenceList.code
            root.findViewById<TextView>(R.id.textView_no_students).text="Liczba studentów: "+selectedAttendenceList.attendence.size
            root.findViewById<TextView>(R.id.textView_no_confirmed_students).text="Liczba potwierdzonych studentów: "+data.getConfiremdNOStudents()
            if(selectedAttendenceList.open){
                openOrCloseSwitch.text="Otwarte"
                openOrCloseSwitch.setChecked(true)
            }
            else{
                openOrCloseSwitch.text="Zamknięte"
                openOrCloseSwitch.setChecked(false)
            }

        }
        else{
            layout.visibility=View.INVISIBLE
            textView.visibility=View.VISIBLE

        }

    }

    fun updateData(){
      //  setAdapterAndRecyclerView(mRoot)
    }



    fun closeList(code: String){
        mFunctions.getHttpsCallable("closeList").call(
            hashMapOf(
                "code" to code
            )

        ).addOnSuccessListener {
            Log.d(TAG, it.data.toString())
            Toast.makeText(this.context,"Sukces", Toast.LENGTH_LONG).show()
            openOrCloseSwitch.isEnabled=true
        }.addOnFailureListener {
            Toast.makeText(this.context,"Błąd: "+it.cause, Toast.LENGTH_LONG).show()
            Log.w(TAG,"addUserToListFunction:failure"+it.cause)
        }


    }

    fun openList(code: String){
        mFunctions.getHttpsCallable("openList").call(
            hashMapOf(
                "code" to code
            )

        ).addOnSuccessListener {
            Log.d(TAG, it.data.toString())
            Toast.makeText(this.context,"Sukces", Toast.LENGTH_LONG).show()
            updateData()
            openOrCloseSwitch.isEnabled=true
        }.addOnFailureListener {
            Toast.makeText(this.context,"Błąd "+it.cause, Toast.LENGTH_LONG).show()
            Log.w(TAG,"addUserToListFunction:failure"+it.cause)
        }
    }



    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.refresh_attendance_list_options_button) {
            updateData()
        }
        else if(i == R.id.switch_open_or_close &&  data.selectedAttendenceList.value!=null){
            openOrCloseSwitch.isEnabled=false
            Log.d(TAG,"DUPA")
            if(switch_open_or_close.text=="Otwarte"){
                closeList(data.selectedAttendenceList.value!!.code)
            }
            else{
                openList(data.selectedAttendenceList.value!!.code)
            }
        }
    }
}