package com.example.rickandmortypagingmvvm

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortypagingmvvm.Constants.MOVIES_STARTING_PAGE_INDEX
import com.example.rickandmortypagingmvvm.data.CharacterMorty
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject



class MoviePagingSourceSearch @Inject constructor(
    private val moviservice: RickAndMortyApi,
    private val query: String
) : PagingSource<Int, CharacterMorty>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterMorty>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterMorty> {

        return try {
            val currentPageList = params.key ?: MOVIES_STARTING_PAGE_INDEX
            val response = moviservice.getSearchCharacters(
                currentPageList,
                characterName = query
            )
            val responseList = mutableListOf<CharacterMorty>()

            if (response.isSuccessful) {
                val data = response.body()?.results ?: emptyList()
                responseList.addAll(data)
            }

            LoadResult.Page(
                data = responseList,
                prevKey = if (currentPageList == MOVIES_STARTING_PAGE_INDEX) null else currentPageList - 1,
                nextKey = currentPageList.plus(1)
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}