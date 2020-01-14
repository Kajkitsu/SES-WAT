package pl.edu.wat.seswat.ui.teacher

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.database.Attendance
import pl.edu.wat.seswat.database.AttendanceList
import pl.edu.wat.seswat.database.FirestoreDataFunctions


class TeacherMenuActivity : AppCompatActivity(){


    val TAG = "TeacherMenuActivity"
    lateinit var data: TeacherData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.teacher_bottom_menu)
        val navView: BottomNavigationView = findViewById(R.id.nav_view_teacher)

        val navController = findNavController(R.id.nav_host_fragment_teacher)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_select_list,
                R.id.navigation_attendance_list_options,
                R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        initTeacherData()

    }

    fun initTeacherData(){

        var mAuth = FirebaseAuth.getInstance()
        data=TeacherData(
            MutableLiveData(),
            FirestoreDataFunctions().getTeacherAttendanceList(mAuth.currentUser?.uid!!),
            FirestoreDataFunctions().getAllSubjectList()
        )
//        data.allSubjects.observe(this, Observer {
//            Log.d("DUPA",it.toString())
//        })
//        data.allAttendanceLists.observe(this, Observer {
//            Log.d("DUPA",it.toString())
//        })
    }







}
