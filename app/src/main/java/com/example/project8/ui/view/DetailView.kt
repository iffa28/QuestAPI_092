package com.example.project8.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project8.model.Mahasiswa
import com.example.project8.ui.customWidget.CustomTopAppBar
import com.example.project8.ui.navigation.DestinasiNavigasi
import com.example.project8.ui.viewModel.DetailMhsUiState
import com.example.project8.ui.viewModel.DetailViewModel
import com.example.project8.ui.viewModel.PenyediaViewModel

object DestinasiDetail : DestinasiNavigasi {
    override val route = "Detail_mhs"
    override val titleRes = "Detail Mahasiswa"
    const val NIM = "nim"
    val routeWithArgs = "$route/{$NIM}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMhsScreen(
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    detailViewModel.getMhsbyId()
                }
            )
        },
    )
    { innerPadding ->
        DetailStatus(
            mhsUiState = detailViewModel.detailMhsUiState,
            retryAction = {detailViewModel.getMhsbyId() },
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize(),
            onEditClick = onEditClick,
            onDeleteClick = {
                detailViewModel.deleteMhs(it.nim)
            }

        )
    }
}

@Composable
fun DetailStatus(
    mhsUiState: DetailMhsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit = {},
    onDeleteClick: (Mahasiswa) -> Unit = {}
){
    when(mhsUiState){
        is DetailMhsUiState.Success -> {
            DetailMhsLayout(
                mahasiswa = mhsUiState.mahasiswa,
                modifier = modifier,
                onEditClick = {onEditClick(it)},
                onDeleteClick = { onDeleteClick(it) }
            )
        }
        is DetailMhsUiState.Loading -> OnLoading(modifier = modifier)
        is DetailMhsUiState.Error -> OnError(retryAction, modifier = modifier)
    }
}

@Composable
fun DetailMhsLayout(
    mahasiswa: Mahasiswa,
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit = {},
    onDeleteClick: (Mahasiswa) -> Unit = {}
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    Column(modifier = Modifier.padding(top = 20.dp)){
        ItemDetailMhs(
            mahasiswa = mahasiswa,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Button(
            onClick = {
                onEditClick(mahasiswa.nim)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ){
            Text(text = "Edit")
        }
        Button(
            onClick = {
                deleteConfirmationRequired = true
                onDeleteClick(mahasiswa) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ){
            Text(text = "Hapus")
        }

        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDeleteClick(mahasiswa)
                },
                onDeleteCancel = { deleteConfirmationRequired = false},
                modifier = Modifier.padding(8.dp)
            )
        }
    }

}

@Composable
fun ItemDetailMhs(
    modifier: Modifier = Modifier,
    mahasiswa: Mahasiswa
)
{
    Column(modifier = Modifier.background(color = Color.LightGray)
    ) {
        ComponentDetailMhs(judul = "Nama Mahasiswa", isinya = mahasiswa.nama)
        Spacer(modifier = Modifier.padding(4.dp))

        ComponentDetailMhs(judul = "NIM", isinya = mahasiswa.nim)
        Spacer(modifier = Modifier.padding(4.dp))

        ComponentDetailMhs(judul = "Jenis Kelamin", isinya = mahasiswa.jenisKelamin)
        Spacer(modifier = Modifier.padding(4.dp))

        ComponentDetailMhs(judul = "Alamat", isinya = mahasiswa.alamat)
        Spacer(modifier = Modifier.padding(4.dp))

        ComponentDetailMhs(judul = "Kelas", isinya = mahasiswa.kelas)
        Spacer(modifier = Modifier.padding(4.dp))

        ComponentDetailMhs(judul = "Angkatan", isinya = mahasiswa.angkatan)
        Spacer(modifier = Modifier.padding(4.dp))
    }
}

@Composable
fun ComponentDetailMhs(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(start = 20.dp, 10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            color = Color(0XFF3E5879),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            color = Color(0XFF213555),
            fontWeight = FontWeight.Bold,
        )

    }

}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do Nothing */  },
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}