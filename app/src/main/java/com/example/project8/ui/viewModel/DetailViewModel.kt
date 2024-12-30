package com.example.project8.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project8.model.Mahasiswa
import com.example.project8.repository.MahasiswaRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Kelas sealed untuk merepresentasikan berbagai status UI pada DetailMhs
sealed class DetailMhsUiState {
    data class Success(val mahasiswa: Mahasiswa) : DetailMhsUiState()
    object Error : DetailMhsUiState()
    object Loading : DetailMhsUiState()
}

// ViewModel untuk mengelola data dan status UI pada DetailMhs
class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val mahasiswaRepository: MahasiswaRepository
) : ViewModel() {
    private val nim: String = checkNotNull(savedStateHandle["nim"])
    var detailMhsUiState: DetailMhsUiState by mutableStateOf(DetailMhsUiState.Loading)
        private set

    init {
        getMhsbyId()
    }

    // Fungsi untuk mengambil data mahasiswa berdasarkan NIM
    fun getMhsbyId() {
        viewModelScope.launch {
            detailMhsUiState = DetailMhsUiState.Loading
            detailMhsUiState = try {
                DetailMhsUiState.Success(mahasiswaRepository.getMahasiswaById(nim))
            } catch (e: Exception) {
                DetailMhsUiState.Error
            }
        }

    }

    fun deleteMhs(nim: String){
        viewModelScope.launch {
            try {
                mahasiswaRepository.deleteMahasiswa(nim)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

}