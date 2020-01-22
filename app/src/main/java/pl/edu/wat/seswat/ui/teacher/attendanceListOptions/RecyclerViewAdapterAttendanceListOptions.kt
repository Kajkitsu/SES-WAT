package pl.edu.wat.seswat.ui.teacher.attendanceListOptions

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.HttpsCallableResult
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.database.AttendenceList
import pl.edu.wat.seswat.database.User
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewAdapterAttendanceListOptions(
    nullableAttendenceList: AttendenceList?,
    var studentsList: ArrayList<User>?
) : RecyclerView.Adapter<RecyclerViewAdapterAttendanceListOptions.ViewHolder>() {

    private var attendenceList: AttendenceList
    private lateinit var cancelConfirmationStudent: (code:String, userID: String) -> Task<HttpsCallableResult>
    private lateinit var confirmStudent: (code:String, userID: String) -> Task<HttpsCallableResult>
    private val TAG = "AdapterAttendanceListOptions"

    init {
        if(nullableAttendenceList==null){
            attendenceList=AttendenceList()
        }
        else{
            attendenceList=nullableAttendenceList
            attendenceList.attendence.sortBy{ it.timeOfAdd }
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_t_attendance_list_options, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameAndSurnameTextView: TextView = itemView.findViewById(R.id.text_view_name_and_surname)
        var dateTextView: TextView = itemView.findViewById(R.id.text_view_date)
        var isConfirmed: ImageView = itemView.findViewById(R.id.is_confirmed_image_view)
        var parent: ConstraintLayout = itemView.findViewById(R.id.item_list_options)

    }

    @SuppressLint("SetTextI18n", "LongLogTag")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called.")



        if(attendenceList.attendence.get(position).timeOfAdd != Timestamp(Date(0))){
            val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
            val date = attendenceList.attendence.get(position).timeOfAdd.toDate()
            holder.dateTextView.text = "Data: "+ dateFormat.format(date).toString()
        }
        else{
            holder.dateTextView.text = "Lista jeszcze nie była otwarta"
        }


        holder.nameAndSurnameTextView.text = "Imie i Nazwisko: "+ getNameAndSurname(attendenceList.attendence.get(position).userID)



        if(attendenceList.attendence[position].confirmed){
            holder.isConfirmed.setImageResource(R.drawable.ic_alarm_on_black_24dp)
        }
        else{
            holder.isConfirmed.setImageResource(R.drawable.ic_alarm_off_black_24dp)
        }



        holder.parent.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                Log.d(TAG, "onClick: clicked on: " + position)
                if(attendenceList.attendence.get(position).confirmed){
                    Log.w("DUPA","dupa123beforeinvoke")
                    cancelConfirmationStudent?.invoke(attendenceList.code,attendenceList.attendence.get(position).userID).addOnFailureListener{
                        Log.w("DUPA","dupa123invoke"+it.cause.toString())
                        notifyItemChanged(position)
                    }
                }
                else{
                    Log.w("DUPA","dupa123beforeinvoke")
                    confirmStudent?.invoke(attendenceList.code,attendenceList.attendence.get(position).userID).addOnFailureListener{
                        Log.w("DUPA","dupa123invoke"+it.cause.toString())
                        notifyItemChanged(position)
                    }
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return attendenceList.attendence.size
    }

    fun getNameAndSurname(userID: String) :String{
        var returnValue = "brak "+(userID.substring(0,4))
        if(studentsList!=null)
        for (user in studentsList!!){
            if(user.userID==userID){
                returnValue=user.surname+" "+user.name
            }
        }
        return returnValue
    }

    fun setList(attendenceList: AttendenceList, studentsList: ArrayList<User>?) {
        this.attendenceList = attendenceList
        this.studentsList = studentsList
        this.attendenceList.attendence.sortBy{ it.timeOfAdd }
        notifyDataSetChanged()
    }

    fun setConfirmStudentFunction(function: (code:String, userID: String) -> Task<HttpsCallableResult>) {
        confirmStudent=function

    }

    fun setCancleConfirmationStudentFunction(function: (code:String, userID: String) -> Task<HttpsCallableResult>) {
        cancelConfirmationStudent=function

    }

//    fun setConfirmStudentFunction(function: () -> Task<HttpsCallableResult>) {
//        confirmStudent=function
//    }


}