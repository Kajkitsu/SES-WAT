package pl.edu.wat.seswat.ui.student.allAttendancesList


import android.annotation.SuppressLint
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.database.AttendenceList
import pl.edu.wat.seswat.database.Subject
import java.text.SimpleDateFormat


class RecyclerViewAdapterAllAttendance(
    var attendenceListOfList: ArrayList<AttendenceList>,
    var subjectList: ArrayList<Subject>
) : RecyclerView.Adapter<RecyclerViewAdapterAllAttendance.ViewHolder>() {

    private val TAG = "RecyclerViewAdapterAllAttendance"


    fun setList(attendenceList: ArrayList<AttendenceList>, subjectList: ArrayList<Subject> ) {
        this.attendenceListOfList = attendenceList
        this.subjectList = subjectList
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_s_all_attendance, parent, false)
        return ViewHolder(view)
    }

    fun getSubjectName(subjectShortName: String): String {
        for (subject in subjectList) {
            if(subject.shortName==subjectShortName) return subject.name
        }
        return "brak przedmiotu";

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called.")


        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        val date = attendenceListOfList[position].attendence[0].timeOfAdd.toDate()

        holder.dateTextView.text = "Data: "+dateFormat.format(date).toString()
        holder.subjectNameTextView.text = "Przedmiot: "+getSubjectName(attendenceListOfList[position].subjectShortName)
        if(attendenceListOfList[position].attendence[0].confirmed) holder.isConfirmedImageView.setImageResource(R.drawable.ic_alarm_on_black_24dp)
        else holder.isConfirmedImageView.setImageResource(R.drawable.ic_alarm_off_black_24dp)


//        holder.parentLayout.setOnClickListener(object : View.OnClickListener() {
//            fun onClick(view: View) {
//                Log.d(TAG, "onClick: clicked on: " + mSubjectName.get(position))
//
//                Toast.makeText(mContext, mSubjectName.get(position), Toast.LENGTH_SHORT).show()
//
//                val intent = Intent(mContext, GalleryActivity::class.java)
//                intent.putExtra("image_url", mDate.get(position))
//                intent.putExtra("image_name", mSubjectName.get(position))
//                mContext.startActivity(intent)
//            }
//        })
    }

    override fun getItemCount(): Int {
        return attendenceListOfList.size
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var subjectNameTextView: TextView = itemView.findViewById(R.id.text_view_subject_name)
        var dateTextView: TextView = itemView.findViewById(R.id.text_view_date)
        var isConfirmedImageView: ImageView = itemView.findViewById(R.id.is_confirmed_image_view)
        var parent: LinearLayout = itemView.findViewById(R.id.item_present_layout)

    }

}