package dk.nodes.template.presentation.ui.main

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import com.squareup.picasso.Picasso
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movieinfodiaglogview.*
import net.hockeyapp.android.UpdateManager
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.ui.savedmovies.ShowSavedMovieActivity
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle



class MainActivity : BaseActivity(), SearchView.OnQueryTextListener, BottomNavigationView.OnNavigationItemSelectedListener {


    private val viewModel by viewModel<MainActivityViewModel>()
    private val adapter = MoviesAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.isDeviceOnlineCheck(this)

        //see_saved_movie_btn.setOnClickListener(this)
        updateRecyclerview()
        input_search.setOnQueryTextListener(this)
        bottomNavigation_View.setOnNavigationItemSelectedListener(this)

        viewModel.viewState.observeNonNull(this) { state ->
            handleMovies(state)
            handleErrors(state)
        }
    }

    private fun handleMovies(viewState: MainActivityViewState) {
        viewState.movies?.let { movieList ->
            error_view.visibility = View.INVISIBLE
            adapter.addMovies(movieList)
            adapter.notifyDataSetChanged()
        }
    }

    private fun handleErrors(viewState: MainActivityViewState) {
        viewState?.viewError?.let {
            if (it.consumed) return@let
            Timber.e("no internet")
            Glide.with(this).asGif().load(R.drawable.nointernetconnection).into(error_view)
            error_view.visibility = View.VISIBLE
            Snackbar.make(rv_moviesList, "No Internet OR No Result", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun updateRecyclerview() {
        // Creates a vertical Layout Manager
        rv_moviesList.layoutManager = GridLayoutManager(this, 3)
        // Access the RecyclerView Adapter and load the data into it
        rv_moviesList.adapter = adapter
        rv_moviesList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        showDialog()

    }


    override fun onDestroy() {
        super.onDestroy()
        // If we checked for hockey updates, unregisterak
        UpdateManager.unregister()
    }


    private fun showDialog() {

        adapter.onItemClickedListener = { movie ->

            val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.movieinfodiaglogview)

            dialog.moviename_txt.setText(movie.name)
            Picasso.get().load("https://image.tmdb.org/t/p/w185/" + movie.poster_path).error(R.drawable.images).fit().into(dialog.movie_images)
            dialog.language_txt.setText(movie.original_language)

            if (dialog.release_txt.text == "") {
                dialog.release_txt.setText("Unknown")

            } else {

                val localdate = LocalDate.of(movie.releaseDate!!.substring(0, 4).toInt(), movie.releaseDate!!.substring(5, 7).toInt(), movie.releaseDate!!.substring(8, 10).toInt()).format((DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)))
                dialog.release_txt.setText(localdate.toString())
            }

            dialog.vote_average_txt.setText(movie.vote_average + "/10")
            dialog.overview_txt.setText(movie.overview)
            dialog.popularity.setText(movie.popularity)
            val savemovieswitch = dialog.save_movie_switch
            val backbtn = dialog.findViewById(R.id.back_btn) as Button

            dialog.show()

            savemovieswitch.setOnClickListener {
              if(!savemovieswitch.isChecked){
                  dialog.setCancelable(true)

              }else{
                  dialog.setCancelable(false)
              }
                showMessage(savemovieswitch)
            }
            backbtn.setOnClickListener {
                saveObject(savemovieswitch, movie)
                dialog.dismiss()
            }

        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

            if(item.itemId == R.id.navigation_savedphoto){


                 startActivity(Intent(this, ShowSavedMovieActivity::class.java))
             }

    return true
    }


    private fun saveObject(savemovieswitch: Switch, movie: Movie) {
        var saveMoviesArrayList = ArrayList<Movie>()

        if (savemovieswitch.isChecked) {
            saveMoviesArrayList.add(movie)
            Log.d("movierepo", " --> Mainactivity saving" + saveMoviesArrayList.toString())
            viewModel.saveMovie(movie)

        }
    }

    fun showMessage(savemovieswitch: Switch) {
        if (savemovieswitch.isChecked) {
            Toast.makeText(applicationContext, "movie is saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "movie is unsaved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.moviesfun(newText.toString())
        updateRecyclerview()

    return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

}















