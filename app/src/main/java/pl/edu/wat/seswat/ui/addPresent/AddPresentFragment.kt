package pl.edu.wat.seswat.ui.addPresent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import pl.edu.wat.seswat.R

class AddPresentFragment : Fragment() {

    private lateinit var addPresentViewModel: AddPresentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addPresentViewModel =
            ViewModelProviders.of(this).get(AddPresentViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_present, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        addPresentViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        return root
    }
}