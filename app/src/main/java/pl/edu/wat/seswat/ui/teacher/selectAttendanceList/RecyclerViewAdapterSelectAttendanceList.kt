package pl.edu.wat.seswat.ui.teacher.selectAttendanceList



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import pl.edu.wat.seswat.database.AttendanceList
import java.util.ArrayList
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.LinearLayout
import pl.edu.wat.seswat.R
import java.text.SimpleDateFormat


class RecyclerViewAdapterSelectAttendanceList(
    attendanceListArrayListLD: MutableLiveData<ArrayList<AttendanceList>>,
    var selectedAttendanceList: MutableLiveData<AttendanceList>,
    context: Context?
) : RecyclerView.Adapter<RecyclerViewAdapterSelectAttendanceList.ViewHolder>() {

    private var attendanceListArrayList: ArrayList<AttendanceList>? = attendanceListArrayListLD.value
    private var mContext: Context? = context
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

        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val date = attendanceListArrayList?.get(position)?.startDate?.toDate()

        holder.dateTextView.text = "Data: "+ dateFormat.format(date).toString()
        holder.subjectNameTextView.text = "Przedmiot: "+ (attendanceListArrayList?.get(position)?.subjectShortName)

        if(attendanceListArrayList?.get(position)?.isOpen!!){
            holder.studentsNumber.text = "Liczba studentow: "+ attendanceListArrayList?.get(position)?.attendance?.size.toString()+"os. - otwarta"
        }
        else{
            holder.studentsNumber.text = "Liczba studentow: "+ attendanceListArrayList?.get(position)?.attendance?.size.toString()+"os. - zamknieta"
        }

        if(attendanceListArrayList?.get(position)==selectedAttendanceList.value){
            holder.isSelectedImageView.visibility = View.VISIBLE
            checkedPosition=position
        }
        else{
            holder.isSelectedImageView.visibility = View.GONE
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
                    Log.d(TAG, "onClick: clicked on: " + (attendanceListArrayList?.get(position)?.subjectShortName)+"  cp:"+checkedPosition)
//                  Toast.makeText(mContext, attendanceListArrayListLD!!.get(position).subjectShortName, Toast.LENGTH_SHORT).show()
                    notifyItemChanged(checkedPosition)
                    notifyItemChanged(oldPosition)
                    selectedAttendanceList.value = attendanceListArrayList?.get(position)
                }
//                val intent = Intent(mContext, GalleryActivity::class.java)
//                intent.putExtra("image_url", mDate.get(position))
//                intent.putExtra("image_name", mSubjectName.get(position))
//                mContext.startActivity(intent)
            }
        })
    }

    override fun getItemCount(): Int {
        if (attendanceListArrayList==null) return 0
        return attendanceListArrayList?.size!!
    }

    fun setList(attendanceList: ArrayList<AttendanceList>) {
        this.attendanceListArrayList = attendanceList
        notifyDataSetChanged()
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var subjectNameTextView: TextView = itemView.findViewById(R.id.text_view_subject_name)
        var dateTextView: TextView = itemView.findViewById(R.id.text_view_date)
        var studentsNumber: TextView = itemView.findViewById(R.id.text_view_number)
        var isSelectedImageView: ImageView = itemView.findViewById(R.id.is_confirmed_image_view)
        var parent: LinearLayout = itemView.findViewById(R.id.item_teacher_list)

    }

}
