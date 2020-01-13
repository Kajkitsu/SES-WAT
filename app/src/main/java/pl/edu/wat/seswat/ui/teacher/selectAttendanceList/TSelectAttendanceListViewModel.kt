package pl.edu.wat.seswat.ui.teacher.selectAttendanceList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.edu.wat.seswat.database.FirestoreDataFunctions
import pl.edu.wat.seswat.database.Subject

class TSelectAttendanceListViewModel : ViewModel() {


    fun fetchSpinnerSubjectStringListItems(): MutableLiveData<ArrayList<Subject>> {
        return FirestoreDataFunctions().getAllSubjectList()
    }


    private val _text = MutableLiveData<String>().apply {
        value = "This is select list"
    }
    val text: LiveData<String> = _text


}