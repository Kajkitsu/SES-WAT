package pl.edu.wat.seswat.ui.student

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.spartons.qrcodegeneratorreader.ScanQrCodeActivity
import pl.edu.wat.seswat.R
import pl.edu.wat.seswat.database.FirestoreDataFunctions
import pl.edu.wat.seswat.ui.teacher.TeacherData


class StudentMenuActivity : AppCompatActivity(){


    lateinit var data: StudentData
    val TAG = "StudentMenuActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_bottom_menu)
        val navView: BottomNavigationView = findViewById(R.id.nav_view_student)

        val navController = findNavController(R.id.nav_host_fragment_student)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_add_present,
                R.id.navigation_all_attendance,
                R.id.navigation_settings
            )
        )

        initStudentData()
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



    }


    fun initStudentData(){
        var mAuth = FirebaseAuth.getInstance()
        data= StudentData(
            FirestoreDataFunctions().getAllAttendanceListOfStudentLD(mAuth.currentUser?.uid!!),
            FirestoreDataFunctions().getAllSubjectList()
        )
    }




}
