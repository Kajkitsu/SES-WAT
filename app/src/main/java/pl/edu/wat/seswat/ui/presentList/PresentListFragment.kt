package pl.edu.wat.seswat.ui.presentList


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.RecyclerViewAdapter
import pl.edu.wat.seswat.database.Lecture
import pl.edu.wat.seswat.database.Present

class PresentListFragment : Fragment(), View.OnClickListener {

    private lateinit var presentListViewModel: PresentListViewModel
    private var TAG = "PresentListFragment"
    lateinit var recycler: RecyclerView
    lateinit var adapter: RecyclerViewAdapter
    var lecture = ArrayList<Lecture>()
    var present = ArrayList<Present>()
    var mAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    lateinit var mRefreshButton: Button
    lateinit var mTestButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presentListViewModel =
            ViewModelProviders.of(this).get(PresentListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_present_list, container, false)
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        presentListViewModel.text.observe(this, Observer {
//            textView.text = it
//        })


        mRefreshButton = root.findViewById(R.id.refresh_button)
        mTestButton = root.findViewById(R.id.button_test_1)

        // Buttons
        root.findViewById<View>(R.id.refresh_button).setOnClickListener(this)
        root.findViewById<View>(R.id.button_test_1).setOnClickListener(this)
        recycler = root.findViewById(R.id.recycler_view_present_list)

        updatePresentList()

        return root
    }



    fun updateRecyclerView(){
        adapter = RecyclerViewAdapter(present,lecture)
        recycler.setAdapter(adapter)
        recycler.setLayoutManager(LinearLayoutManager(this.context))
    }

    fun updatePresentList(){
        Log.d(TAG, "updatePresentList()")
            db.collection("presents")
                .whereEqualTo("userID", mAuth.currentUser?.uid).get().addOnSuccessListener { documents ->
                    present = ArrayList<Present>()
                    for (document in documents) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        present.add(document.toObject(Present::class.java))
                        getSubjectName(document.toObject(Present::class.java).lectureID!!)
                    }
                    updateRecyclerView()
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }

    }

    fun getSubjectName(classesID:String){
        Log.d(TAG, "getSubjectName()")

        db.collection("lectures").document(classesID).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    lecture.add(document.toObject(Lecture::class.java)!!)
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    updateRecyclerView()
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

    }

//    fun setPresentList(){
//        Log.d(TAG, "setPresentList()")
//        db.collection("presents")
//            .whereEqualTo("userID", mAuth.currentUser?.uid).get().addOnSuccessListener { documents ->
//                present = ArrayList<Present>()
//                for (document in documents) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
//                    present.add(document.toObject(Present::class.java))
//
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents: ", exception)
//            }
//
//    }

    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.refresh_button) {
            updatePresentList()
        } else if(i == R.id.button_test_1){
            Log.d(TAG, "${present}")
            Log.d(TAG, "count =${adapter.itemCount}")
        }
    }





}