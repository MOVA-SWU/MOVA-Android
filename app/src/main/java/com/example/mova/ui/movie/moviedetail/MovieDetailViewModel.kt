package com.example.mova.ui.movie.moviedetail

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova.data.model.response.MissionDetailResponse
import com.example.mova.data.model.response.MovieDetailResponse
import com.example.mova.data.model.response.VerificationResponse
import com.example.mova.data.source.remote.repository.MovieDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieDetailRepository,
    @ApplicationContext private val context: Context
): ViewModel() {
    private val _movieDetailResponse = MutableStateFlow<Result<MovieDetailResponse>?>(null)
    val movieDetailResponse = _movieDetailResponse.asStateFlow()

    private val _missionDetailResponse = MutableStateFlow<Result<MissionDetailResponse>?>(null)
    val missionDetailResponse = _missionDetailResponse.asStateFlow()

    private val _missionCompleteResponse = MutableStateFlow<Result<VerificationResponse>?>(null)
    val missionCompleteResponse = _missionCompleteResponse.asStateFlow()

    fun loadMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _movieDetailResponse.value = repository.getMovieDetail(movieId)
        }
    }

    fun loadMissionDetail(movieId: Int) {
        viewModelScope.launch {
            _missionDetailResponse.value = repository.getMissionDetail(movieId)
        }
    }

    fun missionComplete(movieId: Int, imageUri: Uri) {
        viewModelScope.launch {
            try {
                val imagePart = uriToMultipart(imageUri)
                val result = repository.patchMissionComplete(
                    movieRecordId = movieId,
                    imagePart = imagePart
                )
                _missionCompleteResponse.value = result
            } catch (e: Exception) {
                _missionCompleteResponse.value = Result.failure(e)
            }
        }
    }

    private suspend fun uriToMultipart(uri: Uri): MultipartBody.Part = withContext(Dispatchers.IO) {
        val file = File(context.cacheDir, "temp_image.jpg")

        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        } ?: throw IllegalArgumentException("유효하지 않은 이미지 URI입니다")

        val mediaType = "image/jpeg".toMediaTypeOrNull()
        val requestBody = file.asRequestBody(mediaType)

        return@withContext MultipartBody.Part.createFormData("image", file.name, requestBody)
    }
}