package com.littleboysoftware.exercisetracker.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.littleboysoftware.exercisetracker.R
import kotlinx.android.synthetic.main.fragment_placeholder.*

class FeedFragment : Fragment() {

    /* On create view is used for inflating. Do not try to access view elements in this method as
    * they are not guaranteed to be setup yet. From the docs: "It is recommended to only inflate
    * the layout in this method and move logic that operates on the returned View to
    * onViewCreated(View, Bundle)." */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_placeholder, container, false)
    }

    /* onViewCreated is used for initialising values now that the view has been inflated. */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.placeholder_text.text = javaClass.simpleName
    }
}