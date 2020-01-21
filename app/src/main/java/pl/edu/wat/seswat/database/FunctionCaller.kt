package pl.edu.wat.seswat.database

import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.HttpsCallableResult

class FunctionCaller(
    var mFunctions: FirebaseFunctions
){


    fun confirmStudent(code:String, userID: String): Task<HttpsCallableResult> {
        Log.d("DUPA","DUPA"+code+userID)
        return mFunctions.getHttpsCallable("confirmStudent").call(
            hashMapOf(
                "code" to code,
                "studentID" to userID
            )
        )
    }

    fun cancelConfirmationStudent(code:String, userID: String): Task<HttpsCallableResult> {
        Log.d("DUPA","DUPA"+code+userID)
        return mFunctions.getHttpsCallable("cancelConfirmationStudent").call(
            hashMapOf(
                "code" to code,
                "studentID" to userID
            )
        )
    }




}