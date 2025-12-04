package com.example.mova.ui.movie.moviewrite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova.data.model.response.MovieInfo
import com.example.mova.data.source.remote.repository.MovieSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
sealed interface MovieSearchState {
    data object Initial : MovieSearchState
    data object Loading : MovieSearchState
    data class Success(val movieList: List<MovieInfo>) : MovieSearchState
    data object EmptyResult : MovieSearchState
    data class Error(val message: String) : MovieSearchState
}
@HiltViewModel
class MovieSearchViewModel @Inject constructor(private val repository : MovieSearchRepository) : ViewModel() {

    private val _state = MutableStateFlow<MovieSearchState>(MovieSearchState.Initial)
    val state = _state.asStateFlow()

    fun searchMovies(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) return@launch

            _state.emit(MovieSearchState.Loading)

            try {
                val result = repository.searchMovies(query)

                if (result.isNotEmpty()) {
                    _state.emit(MovieSearchState.Success(result))
                } else {
                    _state.emit(MovieSearchState.EmptyResult)
                }
            } catch (e: Exception) {
                _state.emit(MovieSearchState.Error("검색 중 오류가 발생했습니다."))
            }
        }
    }

    fun resetState() {
        viewModelScope.launch {
            _state.emit(MovieSearchState.Initial)
        }
    }
}