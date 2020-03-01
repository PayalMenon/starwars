package com.example.starwars.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.starwars.R
import com.example.starwars.utils.EventObserver
import com.example.starwars.viewmodel.SearchViewModel
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.search_fragment.*
import javax.inject.Inject

class SearchFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        super.onCreateView(inflater, container, savedInstanceState)
        searchViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(SearchViewModel::class.java)
        this.setHasOptionsMenu(true)
        setListener()
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val searchMenuItem = menu.findItem(R.id.action_search)
        val searchView : SearchView = searchMenuItem.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchViewModel.onSearchSelected(query)
                }
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
    }

    private fun setListener() {
        searchViewModel.showLoading.observe(this, EventObserver {
            this.loading.visibility = View.VISIBLE
            this.loading_text.visibility = View.VISIBLE
        })

        searchViewModel.hideLoading.observe(this, EventObserver {
            this.loading.visibility = View.GONE
            this.loading_text.visibility = View.GONE
        })

        searchViewModel.showText.observe(this, EventObserver { textId ->
            this.info_text.text = resources.getString(textId)
            this.info_text.visibility = View.VISIBLE
        })

        searchViewModel.hideText.observe(this, EventObserver{
            this.info_text.visibility = View.GONE
        })
    }

}