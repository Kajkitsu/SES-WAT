package pl.edu.wat.seswat.database

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreDataFunctions(){
    val TAG = "FirestoreDataFunctions"

    fun getMyAttendanceList(userID: String): MutableLiveData<ArrayList<AttendenceList>> {
        var myLDAttendanceList = MutableLiveData<ArrayList<AttendenceList>>()
        var myAttendanceList = ArrayList<AttendenceList>()
        val db = FirebaseFirestore.getInstance()

        Log.d(TAG, "getMyAttendanceList()")
        db.collection("list")
            .get().addOnSuccessListener {
            lists ->
            for (list in lists) {
                list.reference.collection("attendence").document(userID).get()
                    .addOnSuccessListener {
                        doc ->
                        doc.reference.parent.parent?.get()?.addOnSuccessListener {
                                myList->
                            Log.d(TAG, "${myList.id} => ${myList.data}")
                            myAttendanceList.add(myList.toObject(AttendenceList::class.java)!!)
                            myLDAttendanceList.value =myAttendanceList
                            Log.d(TAG, "${myLDAttendanceList.value}")
                        }

                    }
                }
            }
        return myLDAttendanceList
        }
    fun getAllAttendanceListOfStudent(userID: String, myLDAttendenceList:MutableLiveData<ArrayList<AttendenceList>> = MutableLiveData()): MutableLiveData<ArrayList<AttendenceList>> {
        var myAttendanceList = ArrayList<AttendenceList>()
        val db = FirebaseFirestore.getInstance()

        Log.d(TAG, "AttendanceListOfStudent()")
        db.collectionGroup("attendence").whereEqualTo("userID", userID).get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                Log.d(TAG, "AttendanceListOfStudent.value  ${queryDocumentSnapshots.toString()}")
                if (queryDocumentSnapshots.isEmpty)
                    Log.d(TAG, "AttendanceListOfStudent.value is empty")
                for(queryDocument in queryDocumentSnapshots){
                    Log.d(TAG, "AttendanceListOfStudent.queryDocument.value  ${queryDocument.toString()}")
                    val mAttendenceOnThisList = queryDocument.toObject(Attendence::class.java)
                    queryDocument.reference.parent.parent?.get()?.addOnSuccessListener { docList ->
                        var myAttendence = docList.toObject(AttendenceList::class.java)!!
                        myAttendence.attendence = ArrayList()
                        myAttendence.attendence.add(mAttendenceOnThisList)

                        Log.d(TAG, "AttendanceListOfStudent ${docList.toObject(AttendenceList::class.java)}")

                        myAttendanceList.add(myAttendence)
                        myLDAttendenceList.value =myAttendanceList
                        Log.d(TAG, "AttendanceListOfStudent.value  ${myLDAttendenceList.value}")
                    }

                }
            }.addOnFailureListener{
                Log.w(TAG,"db.collectionGroup(\"attendence\").whereEqualTo(\"userID\", userID).get()",it)
            }

        return myLDAttendenceList
    }



    fun getAllAttendanceListOfTeacher(teacherID: String, myLDAttendenceList: MutableLiveData<ArrayList<AttendenceList>> = MutableLiveData()): MutableLiveData<ArrayList<AttendenceList>> {
        var myAttendanceList = ArrayList<AttendenceList>()
        val db = FirebaseFirestore.getInstance()

        Log.d(TAG, "getAllAttendanceListOfTeacher()")
        db.collection("list").whereEqualTo("teacherID",teacherID)
            .get().addOnSuccessListener {
                    documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    var tmpList = document.toObject(AttendenceList::class.java)
                    tmpList.attendence=ArrayList<Attendence>()
                    document.reference.collection("attendence").get().addOnSuccessListener {
                        studentsList ->
                        for (studentDoc in studentsList){
                            Log.d(TAG, "${studentDoc.id} => ${studentDoc.data}")
                            tmpList.attendence.add(studentDoc.toObject(Attendence::class.java))
                        }
                        myAttendanceList.add(tmpList)
                        myLDAttendenceList.value =myAttendanceList
                        Log.d(TAG, "getAllAttendanceListOfTeacher = ${myLDAttendenceList.value}")

                    }
                }
            }
        return myLDAttendenceList
    }

    fun getAllSubjectList(myLiveDataSubjectList: MutableLiveData<ArrayList<Subject>> = MutableLiveData()): MutableLiveData<ArrayList<Subject>> {
        var subjectList = ArrayList<Subject>()
        val db = FirebaseFirestore.getInstance()

        Log.d(TAG, "getAllSubjecList()")
        db.collection("subject")
            .get().addOnSuccessListener {
                    documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    subjectList.add(document.toObject(Subject::class.java))
                    myLiveDataSubjectList.value=subjectList
                }
            }
        return myLiveDataSubjectList
    }

    fun getAllSubjectStringList(): MutableLiveData<ArrayList<String>> {
        var myLiveDataSubjectList = MutableLiveData<ArrayList<String>>()
        var subjectList = ArrayList<String>()
        val db = FirebaseFirestore.getInstance()

        Log.d(TAG, "getAllSubjecList()")
        db.collection("subject")
            .get().addOnSuccessListener {
                    documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    subjectList.add(document.toObject(Subject::class.java).name.toString())
                    myLiveDataSubjectList.value=subjectList
                }
            }
        return myLiveDataSubjectList
    }


    fun getUserData(userUID: String?): MutableLiveData<User> {
        var userDataLD: MutableLiveData<User> = MutableLiveData()
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("users").document(userUID.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    userDataLD.value=(document.toObject(User::class.java))
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
        return userDataLD
    }

    fun getSelectedAttendanceList(code: String, attendenceListLD:MutableLiveData<AttendenceList> = MutableLiveData()): MutableLiveData<AttendenceList> {
        val db = FirebaseFirestore.getInstance()

        Log.d(TAG, "getSelectedAttendenceList()")
        db.collection("list").document(code).get().addOnSuccessListener { document ->
            Log.d(TAG, "${document.id} => ${document.data}")
            var attendanceList = document.toObject(AttendenceList::class.java)
            attendanceList!!.attendence = ArrayList<Attendence>()
            document.reference.collection("attendence").get().addOnSuccessListener { studentsList ->
                for (studentDoc in studentsList) {
                    Log.d(TAG, "${studentDoc.id} => ${studentDoc.data}")
                    attendanceList.attendence.add(studentDoc.toObject(Attendence::class.java))
                }
            }
            attendenceListLD.value=attendanceList
            Log.d(TAG, "${attendenceListLD.value}")
        }
        return attendenceListLD
    }

}