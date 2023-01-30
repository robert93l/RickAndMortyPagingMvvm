package com.example.rickandmortypagingmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.rickandmortypagingmvvm.databinding.ActivityMainBinding
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