package pl.edu.wat.seswat


import android.annotation.SuppressLint
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import pl.edu.wat.seswat.database.Lecture
import pl.edu.wat.seswat.database.Present





class RecyclerViewAdapter(
    present: ArrayList<Present>,
    aClasses: ArrayList<Lecture>
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var present: ArrayList<Present> = present
    private var lecture: ArrayList<Lecture> = aClasses

    private val TAG = "RecyclerViewAdapter"



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_attendance, parent, false)
        return ViewHolder(view)
    }

    fun getSubjectName(lectureID: String): String {
        for (objectLectures in lecture) {
            if(objectLectures.lectureID==lectureID) return objectLectures.subjectName.toString()
        }
        return "brak przedmiotu";

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called.")

        holder.dateTextView.text = "Data: "+present[position].timeOfAddToList
        holder.subjectNameTextView.text = "Przedmiot: "+getSubjectName(present[position].lectureID!!)
        if(present[position].confirmed!!) holder.isConfirmedImageView.setImageResource(R.drawable.ic_alarm_on_black_24dp)
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
        return present.size
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var subjectNameTextView: TextView = itemView.findViewById(R.id.text_view_subject_name)
        var dateTextView: TextView = itemView.findViewById(R.id.text_view_date)
        var isConfirmedImageView: ImageView = itemView.findViewById(R.id.is_confirmed_image_view)
        var parent: LinearLayout = itemView.findViewById(R.id.item_present_layout)

    }

}