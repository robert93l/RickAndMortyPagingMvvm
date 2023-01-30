package com.example.rickandmortypagingmvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RickAndMortyViewModel @Inject constructor(private val serviceRickMorty: RickAndMortyApi) : ViewModel() {


    val characterRickMorty = Pager(PagingConfig(pageSize = 20, prefetchDistance = 50)) {
        MoviePagingSourceRickAndMorty(serviceRickMorty)
    }.flow.cachedIn(viewModelScope)
}