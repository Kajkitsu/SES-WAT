package pl.edu.wat.seswat.ui.presentList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import pl.edu.wat.seswat.R

class PresentListFragment : Fragment() {

    private lateinit var presentListViewModel: PresentListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presentListViewModel =
            ViewModelProviders.of(this).get(PresentListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_present_list, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        presentListViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}