package com.example.rickandmortypagingmvvm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmortypagingmvvm.network.RickAndMortyApi
import com.example.rickandmortypagingmvvm.data.CharacterMorty
import com.example.rickandmortypagingmvvm.data.paging.MoviePagingSourceRickAndMorty
import com.example.rickandmortypagingmvvm.data.paging.MoviePagingSourceSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject


@HiltViewModel
class RickAndMortyViewModel @Inject constructor( private var query: String, private val serviceRickMorty: RickAndMortyApi) : ViewModel() {


    val characterRickMorty = Pager(PagingConfig(pageSize = 20, prefetchDistance = 50)) {
        MoviePagingSourceRickAndMorty(serviceRickMorty)
    }.flow.cachedIn(viewModelScope)

    var searchCharacter: Flow<PagingData<CharacterMorty>> = emptyFlow()
    fun searchCharacterMorty(query: String) {
        this.query = query
        searchCharacter = Pager(PagingConfig(pageSize = 20)) {
            MoviePagingSourceSearch(serviceRickMorty, this.query)
        }.flow.cachedIn(viewModelScope)
    }
}