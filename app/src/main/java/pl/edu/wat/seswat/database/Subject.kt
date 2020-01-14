package pl.edu.wat.seswat.database

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Subject(
    var name: String = "brak",
    var shortName: String = "brak",
    var type: ArrayList<String> = ArrayList()
)

