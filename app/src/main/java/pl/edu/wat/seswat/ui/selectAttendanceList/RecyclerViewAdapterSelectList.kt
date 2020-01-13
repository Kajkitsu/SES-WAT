package pl.edu.wat.seswat



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
import android.widget.Toast
import java.text.SimpleDateFormat


class RecyclerViewAdapterSelectList(
    attendanceList: MutableLiveData<ArrayList<AttendanceList>>,
    context: Context?
) : RecyclerView.Adapter<RecyclerViewAdapterSelectList.ViewHolder>() {

    private var attendanceList: ArrayList<AttendanceList>? = attendanceList.value
    private var mContext: Context? = context
    private var checkedPosition = -1

    private val TAG = "AdapterSelectList"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_teacher_list, parent, false)
        return ViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called.")

        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val date = attendanceList?.get(position)?.startDate?.toDate()

        holder.dateTextView.text = "Data: "+ dateFormat.format(date).toString()
        holder.subjectNameTextView.text = "Przedmiot: "+ (attendanceList?.get(position)?.subjectShortName)

        if(attendanceList?.get(position)?.isOpen!!){
            holder.studentsNumber.text = "Liczba studentow: "+ attendanceList?.get(position)?.attendance?.size.toString()+"os. - otwarta"
        }
        else{
            holder.studentsNumber.text = "Liczba studentow: "+ attendanceList?.get(position)?.attendance?.size.toString()+"os. - zamknieta"
        }

        if (checkedPosition == -1)
        {
            holder.isSelectedImageView.visibility = View.GONE
        }
        else {
            if (checkedPosition == position) {
                holder.isSelectedImageView.visibility = View.VISIBLE
            } else {
                holder.isSelectedImageView.visibility = View.GONE
            }
        }



        holder.parent.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {

                if (checkedPosition != position) {
                    checkedPosition = position
                    Log.d(TAG, "onClick: clicked on: " + (attendanceList?.get(position)?.subjectShortName)+"  cp:"+checkedPosition)

//                    Toast.makeText(mContext, attendanceList!!.get(position).subjectShortName, Toast.LENGTH_SHORT).show()
                    notifyDataSetChanged()

                }



//                val intent = Intent(mContext, GalleryActivity::class.java)
//                intent.putExtra("image_url", mDate.get(position))
//                intent.putExtra("image_name", mSubjectName.get(position))
//                mContext.startActivity(intent)
            }
        })
    }

    override fun getItemCount(): Int {
        if (attendanceList==null) return 0
        return attendanceList?.size!!
    }

    fun setList(attendanceList: ArrayList<AttendanceList>) {
        this.attendanceList = attendanceList
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
////
//
//class RecyclerViewAdapterSelecatList(private val context:Context, private var employees:MutableLiveData<AttendanceList>?):RecyclerView.Adapter(),
//    Parcelable {
// // if checkedPosition = -1, there is no default selection
//    // if checkedPosition = 0, 1st item is selected by default
//    private var checkedPosition = 0
//
// val selected:Employee?
//get() = if (checkedPosition != -1) {
//    employees!!.get(checkedPosition)
//} else null
//
//    constructor(parcel: Parcel) : this(
//        TODO("context"),
//        TODO("employees")
//    ) {
//        checkedPosition = parcel.readInt()
//    }
//
//
//
//override fun onCreateViewHolder(viewGroup:ViewGroup, i:Int):SingleViewHolder {
//val view = LayoutInflater.from(context).inflate(R.layout.item_employee, viewGroup, false)
//return SingleViewHolder(view)
//}
//
//override fun onBindViewHolder(singleViewHolder:SingleViewHolder, position:Int) {
//singleViewHolder.bind(employees!![position])
//}
//
//override fun getItemCount():Int {
//return employees!!.size
//}
//
//internal inner class SingleViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
//
//private val textView:TextView
//private val imageView:ImageView
//
//init{
//textView = itemView.findViewById(R.id.textView)
//imageView = itemView.findViewById(R.id.imageView)
//}
//
// fun bind(employee:Employee) {
//
//}
//textView.setText(employee.getName())
//
//itemView.setOnClickListener {
//    imageView.visibility = View.VISIBLE
//    if (checkedPosition != adapterPosition) {
//        notifyItemChanged(checkedPosition)
//        checkedPosition = adapterPosition
//    }
//}
// }
//}
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeInt(checkedPosition)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<RecyclerViewAdapterSelectList> {
//        override fun createFromParcel(parcel: Parcel): RecyclerViewAdapterSelectList {
//            return RecyclerViewAdapterSelectList(parcel)
//        }
//
//        override fun newArray(size: Int): Array<RecyclerViewAdapterSelectList?> {
//            return arrayOfNulls(size)
//        }
//    }
//}