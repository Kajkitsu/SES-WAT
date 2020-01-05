package pl.edu.wat.seswat.database

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Subject(
    var name: String? = null,
    var shortName: String? = null,
    var type: ArrayList<String>? = null
)

