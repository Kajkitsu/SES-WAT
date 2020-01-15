package pl.edu.wat.seswat.ui.teacher.attendanceListOptions

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.database.AttendanceList
import java.text.SimpleDateFormat

class RecyclerViewAdapterAttendanceListOptions(
    private var attendanceList: AttendanceList
) : RecyclerView.Adapter<RecyclerViewAdapterAttendanceListOptions.ViewHolder>() {

    private val TAG = "AdapterAttendanceListOptions"


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

        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        val date = attendanceList.attendance.get(position).timeOfAdd.toDate()

        holder.dateTextView.text = "Data: "+ dateFormat.format(date).toString()

        holder.nameAndSurnameTextView.text = "Imie i Nazwisko: "+ (attendanceList.attendance.get(position).userID)



        if(attendanceList.attendance.get(position).confirmed){
            holder.isConfirmed.setImageResource(R.drawable.ic_alarm_on_black_24dp)
        }
        else{
            holder.isConfirmed.setImageResource(R.drawable.ic_alarm_off_black_24dp)
        }



        holder.parent.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                Log.d(TAG, "onClick: clicked on: " + position)
                attendanceList.attendance.get(position).confirmed=!attendanceList.attendance.get(position).confirmed
                //TODO 1!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1!!!!!!!!1!!!!!!!!!!!!!!!!!!!!!!
                notifyItemChanged(position)

//                val intent = Intent(mContext, GalleryActivity::class.java)
//                intent.putExtra("image_url", mDate.get(position))
//                intent.putExtra("image_name", mSubjectName.get(position))
//                mContext.startActivity(intent)
            }
        })
    }

    override fun getItemCount(): Int {
        return attendanceList.attendance.size
    }

    fun setList(attendanceList: AttendanceList) {
        this.attendanceList = attendanceList
        notifyDataSetChanged()
    }





}