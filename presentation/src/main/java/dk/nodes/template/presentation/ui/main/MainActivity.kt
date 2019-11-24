package dk.nodes.template.presentation.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.ui.base.BaseActivity



class MainActivity : BaseActivity() {


    private var shownMenu: Int = 0
    lateinit var discoverMoviesFragment: Fragment
    lateinit var showSavedMovieFragment: Fragment
    lateinit var searchMoviesFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }


}



















