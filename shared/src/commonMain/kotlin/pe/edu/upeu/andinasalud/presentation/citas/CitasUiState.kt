package pe.edu.upeu.andinasalud.presentation.citas

import pe.edu.upeu.andinasalud.domain.model.Cita

sealed interface CitasUiState {
    data object Loading : CitasUiState
    data class Content(val citas: List<Cita>) : CitasUiState
    data object Empty : CitasUiState
    data class Error(val message: String) : CitasUiState
}
