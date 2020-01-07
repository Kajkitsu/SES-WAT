package pl.edu.wat.seswat.database

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreDataFunctions(){
    val TAG = "FirestoreDataFunctions"

    fun getMyAttendanceList(userID: String): MutableLiveData<ArrayList<AttendanceList>> {
        var myLDAttendanceList = MutableLiveData<ArrayList<AttendanceList>>()
        var myAttendanceList = ArrayList<AttendanceList>()
        val db = FirebaseFirestore.getInstance()

        Log.d(TAG, "getMyAttendanceList()")
        db.collection("list")
            .get().addOnSuccessListener {
            lists ->
            for (list in lists) {
                list.reference.collection("attendance").document(userID).get()
                    .addOnSuccessListener {
                        doc ->
                        doc.reference.parent.parent?.get()?.addOnSuccessListener {
                                myList->
                            Log.d(TAG, "${myList.id} => ${myList.data}")
                            myAttendanceList.add(myList.toObject(AttendanceList::class.java)!!)
                            myLDAttendanceList.value =myAttendanceList
                            Log.d(TAG, "${myLDAttendanceList.value}")
                        }

                    }
                }
            }
        return myLDAttendanceList
        }
    fun getMyAttendanceList2(userID: String): MutableLiveData<ArrayList<AttendanceList>> {
        var myLDAttendanceList = MutableLiveData<ArrayList<AttendanceList>>()
        var myAttendanceList = ArrayList<AttendanceList>()
        val db = FirebaseFirestore.getInstance()

        Log.d(TAG, "getMyAttendanceList2()")
        db.collectionGroup("attendance").whereEqualTo("userID", userID).get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                for(queryDocument in queryDocumentSnapshots){
                    queryDocument.reference.parent.parent?.get()?.addOnSuccessListener { docList ->
                        Log.d(TAG, "${docList.toObject(AttendanceList::class.java)}")
                        myAttendanceList.add(docList.toObject(AttendanceList::class.java)!!)
                        myLDAttendanceList.value =myAttendanceList
                        Log.d(TAG, "${myLDAttendanceList.value}")
                    }

                }
            }

        return myLDAttendanceList
    }



    fun getTeacherAttendanceList(teacherID: String): MutableLiveData<ArrayList<AttendanceList>> {
        var myLDAttendanceList = MutableLiveData<ArrayList<AttendanceList>>()
        var myAttendanceList = ArrayList<AttendanceList>()
        val db = FirebaseFirestore.getInstance()

        Log.d(TAG, "getTeacherAttendanceList()")
        db.collection("list").whereEqualTo("teacherID",teacherID)
            .get().addOnSuccessListener {
                    documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    var tmpList = document.toObject(AttendanceList::class.java)
                    tmpList.attendance=ArrayList<Attendance>()
                    document.reference.collection("attendance").get().addOnSuccessListener {
                        studentsList ->
                        for (studentDoc in studentsList){
                            Log.d(TAG, "${studentDoc.id} => ${studentDoc.data}")
                            tmpList.attendance!!.add(studentDoc.toObject(Attendance::class.java))
                        }
                        myAttendanceList.add(tmpList)
                        myLDAttendanceList.value =myAttendanceList
                        Log.d(TAG, "${myLDAttendanceList.value}")

                    }
                }
            }
        return myLDAttendanceList
    }

    fun getAllSubjectList(): MutableLiveData<ArrayList<Subject>> {
        var myLiveDataSubjectList = MutableLiveData<ArrayList<Subject>>()
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






}