package pl.edu.wat.seswat.database

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreDataFunctions(){
    val TAG = "FirestoreDataFunctions"

    fun getAllAttendanceListOfStudentLD(userID: String, myLDAttendenceList:MutableLiveData<ArrayList<AttendenceList>> = MutableLiveData()): MutableLiveData<ArrayList<AttendenceList>> {
        var myAttendanceList = ArrayList<AttendenceList>()
        val db = FirebaseFirestore.getInstance()

        Log.d(TAG, "AttendanceListOfStudent()")
        db.collectionGroup("attendence").whereEqualTo("userID", userID).addSnapshotListener{
            queryDocumentSnapshots, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if(queryDocumentSnapshots!=null){
                myAttendanceList = ArrayList<AttendenceList>()
                Log.d(TAG, "AttendanceListOfStudent.value  ${queryDocumentSnapshots.toString()}")
                if (queryDocumentSnapshots.isEmpty)
                    Log.d(TAG, "AttendanceListOfStudent.value is empty")
                for(queryDocument in queryDocumentSnapshots) {
                    Log.d(
                        TAG,
                        "AttendanceListOfStudent.queryDocument.value  ${queryDocument.toString()}"
                    )
                    val mAttendenceOnThisList = queryDocument.toObject(Attendence::class.java)
                    queryDocument.reference.parent.parent?.addSnapshotListener { docList, e ->
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e)
                            return@addSnapshotListener
                        }
                        if (docList != null) {
                            var myAttendence = docList.toObject(AttendenceList::class.java)!!
                            myAttendence.attendence = ArrayList()
                            myAttendence.attendence.add(mAttendenceOnThisList)
                            myAttendanceList.add(myAttendence)
                            myLDAttendenceList.value = myAttendanceList

                            Log.d(TAG, "AttendanceListOfStudent.value  ${myLDAttendenceList.value}")
                        }

                    }
                }


            }
        }

        return myLDAttendenceList
    }

    fun getAllAttendanceListOfTeacherLD(teacherID: String, myLDAttendenceList: MutableLiveData<ArrayList<AttendenceList>> = MutableLiveData()): MutableLiveData<ArrayList<AttendenceList>> {

        val db = FirebaseFirestore.getInstance()

        Log.d(TAG, "getAllAttendanceListOfTeacher()")
        db.collection("list").whereEqualTo("teacherID",teacherID).addSnapshotListener{ documents, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if(documents!=null){
                Log.d("Data change", " db.collection(\"list\").whereEqualTo(\"teacherID\",teacherID")
                var myAttendanceList = ArrayList<AttendenceList>()
                for (doc in documents) {
                    var newAttendenceList = doc.toObject(AttendenceList::class.java)
                    doc.reference.collection("attendence").addSnapshotListener{attendenceList, e ->
                        Log.d("Data change", " doc.reference.collection(\"attendence\").")
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e)
                            return@addSnapshotListener
                        }
                        newAttendenceList.attendence=ArrayList()
                        if (attendenceList != null) {
                            for(doc2 in attendenceList){

                                newAttendenceList.attendence.add(doc2.toObject(Attendence::class.java))
                                Log.d("ChangeFS",newAttendenceList.attendence.toString()+" "+doc2)
                            }
                        }
                        myAttendanceList.add(newAttendenceList)
                        myLDAttendenceList.value=myAttendanceList


                    }
                    myLDAttendenceList.value=myAttendanceList
                }

                Log.d(TAG, "myLDAttendenceList.value: $myAttendanceList")
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
                Log.d("Data chnge", "${myLiveDataSubjectList.value}")

            }
        return myLiveDataSubjectList
    }

    fun getUserDataLD(userUID: String?): MutableLiveData<User> {
        var userDataLD: MutableLiveData<User> = MutableLiveData()
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(userUID.toString()).addSnapshotListener{
                document, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (document != null) {
                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                userDataLD.value=(document.toObject(User::class.java))
            } else {
                Log.d(TAG, "No such document")
            }
        }
        return userDataLD
    }

    fun getSelectedAttendanceList(code: String, attendenceListLD:MutableLiveData<AttendenceList> = MutableLiveData()): MutableLiveData<AttendenceList> {
        val db = FirebaseFirestore.getInstance()

        Log.d(TAG, "getSelectedAttendenceList()")
        db.collection("list").document(code).get().addOnSuccessListener { document ->
            Log.d(TAG, "${document.id} => ${document.data}")
            var attendanceList = document.toObject(AttendenceList::class.java)
            document.reference.collection("attendence").get().addOnSuccessListener { studentsList ->
                Log.d(TAG, "selectedAttendanceList data:"+studentsList.isEmpty)
                attendanceList?.attendence = ArrayList<Attendence>()
                for (studentDoc in studentsList) {
                    Log.d(TAG, "${studentDoc.id} => ${studentDoc.data}")
                    attendanceList?.attendence?.add(studentDoc.toObject(Attendence::class.java))
                    Log.d(TAG, "selectedAttendance "+attendanceList?.attendence.toString())
                    attendenceListLD.value=attendanceList
                }
            }
//
            attendenceListLD.value=attendanceList
            Log.d(TAG, "selectedAttendance LD ${attendenceListLD.value}")
        }
        return attendenceListLD
    }

    fun getAllStudentsLD(allStudentsLD: MutableLiveData<ArrayList<User>> = MutableLiveData()): MutableLiveData<ArrayList<User>> {
        val db = FirebaseFirestore.getInstance()

        Log.d(TAG, "getAllStudents()")
        db.collection("users").whereEqualTo("isTeacher",false).addSnapshotListener{
            documents, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if(documents!=null){
                var studentsList = ArrayList<User>()
                for (document in documents) {
                    Log.d(TAG, "getAllStudents ${document.id} => ${document.data}")
                    studentsList.add(document.toObject(User::class.java))

                }
                allStudentsLD.value=studentsList
                Log.d(TAG, "allStudentsLD.value"+allStudentsLD.value)

            }
        }
        return allStudentsLD
    }


}