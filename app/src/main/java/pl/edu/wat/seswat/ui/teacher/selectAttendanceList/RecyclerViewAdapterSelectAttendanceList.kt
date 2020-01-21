package pl.edu.wat.seswat.ui.teacher.selectAttendanceList



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import pl.edu.wat.seswat.database.AttendenceList
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.Timestamp
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.ui.teacher.TeacherData
import java.text.SimpleDateFormat
import java.util.*


class RecyclerViewAdapterSelectAttendanceList(
    var teacherData: TeacherData
) : RecyclerView.Adapter<RecyclerViewAdapterSelectAttendanceList.ViewHolder>() {

    private var attendenceListArrayList: ArrayList<AttendenceList>? = teacherData.allAttendenceLists.value
    private var checkedPosition = -1

    private val TAG = "AdapterSelectList"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_t_select_attendance_lists, parent, false)
        return ViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called.")

        if(attendenceListArrayList?.get(position)?.startDate != Timestamp(Date(0))){
            val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
            val date = attendenceListArrayList?.get(position)?.startDate!!.toDate()
            holder.dateTextView.text = "Data: "+ dateFormat.format(date).toString()
        }
        else{
            holder.dateTextView.text = "Lista jeszcze nie była otwarta"
        }




        holder.subjectNameTextView.text = "Przedmiot: "+ (attendenceListArrayList?.get(position)?.subjectShortName)

        if(attendenceListArrayList?.get(position)?.open!!){
            holder.studentsNumber.text = "Liczba studentow: "+ attendenceListArrayList?.get(position)?.attendence?.size.toString()+"os. - otwarta"
        }
        else{
            holder.studentsNumber.text = "Liczba studentow: "+ attendenceListArrayList?.get(position)?.attendence?.size.toString()+"os. - zamknieta"
        }

        if(attendenceListArrayList?.get(position)==teacherData.selectedAttendenceList.value){
            holder.isSelectedImageView.visibility = View.VISIBLE
            holder.view.visibility= View.VISIBLE
            Log.d("DUPA",holder.view.background.toString()+holder.view.visibility.toString())
            checkedPosition=position
        }
        else{
            holder.isSelectedImageView.visibility = View.GONE
            holder.view.visibility= View.GONE
        }

//        if (checkedPosition == -1)
//        {
//
//        }
//        else {
//            if (checkedPosition == position) {
//
//            } else {
//                holder.isSelectedImageView.visibility = View.GONE
//            }
//        }



        holder.parent.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {

                if (checkedPosition != position) {
                    val oldPosition = checkedPosition
                    checkedPosition = position
                    Log.d(TAG, "onClick: clicked on: " + (attendenceListArrayList?.get(position)?.subjectShortName)+"  cp:"+checkedPosition)
//                  Toast.makeText(mContext, attendenceListArrayListLD!!.get(position).subjectShortName, Toast.LENGTH_SHORT).show()
                    teacherData.selectedAttendenceList.value=attendenceListArrayList?.get(position)
                    notifyItemChanged(checkedPosition)
                    notifyItemChanged(oldPosition)
                }
//                val intent = Intent(mContext, GalleryActivity::class.java)
//                intent.putExtra("image_url", mDate.get(position))
//                intent.putExtra("image_name", mSubjectName.get(position))
//                mContext.startActivity(intent)
            }
        })
    }

    override fun getItemCount(): Int {
        if (attendenceListArrayList==null) return 0
        return attendenceListArrayList?.size!!
    }

    fun setList(attendenceList: ArrayList<AttendenceList>) {
        this.attendenceListArrayList = attendenceList
        notifyDataSetChanged()
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var subjectNameTextView: TextView = itemView.findViewById(R.id.text_view_subject_name)
        var dateTextView: TextView = itemView.findViewById(R.id.text_view_date)
        var studentsNumber: TextView = itemView.findViewById(R.id.text_view_number)
        var isSelectedImageView: ImageView = itemView.findViewById(R.id.is_confirmed_image_view)
        var view: View = itemView.findViewById(R.id.view_background)
        var parent: ConstraintLayout = itemView.findViewById(R.id.item_teacher_list)

    }

}
