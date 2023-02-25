package com.example.rickandmortypagingmvvm.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmortypagingmvvm.R
import com.example.rickandmortypagingmvvm.databinding.ActivityMainBinding
import com.example.rickandmortypagingmvvm.ui.viewmodel.RickAndMortyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: RickAndMortyViewModel by viewModels()
    private lateinit var adapterCharacter: CharacterAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRv()
        setUpViewModel()
        loadSearch()
        refreshswipe()

    }

    private fun loadSearch() {

        lifecycleScope.launch {
            viewModel.searchCharacter.collect {
                adapterCharacter.submitData(it)

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search_character, menu)
        val item = menu.findItem(R.id.searchCharacterMenu)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchCharacterMorty(query)
                    loadSearch()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    viewModel.searchCharacterMorty(newText)
                }
                return true
            }

        })
        return true
    }

    private fun refreshswipe() {
        binding.swiperick.setOnRefreshListener {

            setUpRv()
            setUpViewModel()
            binding.swiperick.isRefreshing = false
        }
    }

    private fun setUpRv() {

        adapterCharacter = CharacterAdapter()

        binding.recyclerRickMorty.apply {
            adapter = adapterCharacter
            /*  layoutManager = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)*/
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            setHasFixedSize(true)
        }
    }

    private fun setUpViewModel() {

        lifecycleScope.launch {
            viewModel.characterRickMorty.collect {

                Log.d("aaa", "load: $it")
                adapterCharacter.submitData(it)
            }
        }
    }
}