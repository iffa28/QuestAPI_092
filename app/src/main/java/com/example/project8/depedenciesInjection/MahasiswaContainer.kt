package com.example.project8.depedenciesInjection

import com.example.project8.repository.MahasiswaRepository
import com.example.project8.repository.NetworkMahasiswaRepository
import com.example.project8.service_api.MahasiswaService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val mahasiswaRepository: MahasiswaRepository
}

class MahasiswaContainer : AppContainer{

    // URL dasar untuk mengakses API
    private val baseUrl = "http://localhost:8000/umyTI/"

    // Mengatur JSON parser agar bisa mengabaikan data yang tidak dikenal di response
    private val json = Json { ignoreUnknownKeys = true }

    // Membuat objek Retrofit untuk menghubungkan aplikasi dengan API
    private val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(baseUrl)   // Mengatur alamat dasar API
    .build()  // Membangun objek Retrofit

    // Objek ini digunakan untuk mengakses layanan API mahasiswa
    private val mahasiswaService: MahasiswaService by lazy {
        retrofit.create(MahasiswaService::class.java)
    }

    // Repository untuk mengelola data mahasiswa dari API
    override val mahasiswaRepository: MahasiswaRepository by lazy {
        NetworkMahasiswaRepository(mahasiswaService)
    }
}
