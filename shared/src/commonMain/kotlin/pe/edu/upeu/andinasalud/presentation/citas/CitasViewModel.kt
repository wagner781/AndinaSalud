package pe.edu.upeu.andinasalud.presentation.citas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pe.edu.upeu.andinasalud.domain.usecase.ObtenerCitasUseCase

class CitasViewModel(private val obtenerCitas: ObtenerCitasUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<CitasUiState>(CitasUiState.Loading)
    val uiState: StateFlow<CitasUiState> = _uiState.asStateFlow()

    init {
        cargar()
    }

    fun cargar() {
        viewModelScope.launch {
            _uiState.value = CitasUiState.Loading
            try {
                val citas = obtenerCitas()
                _uiState.value = if (citas.isEmpty()) CitasUiState.Empty else CitasUiState.Content(citas)
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Throwable) {
                _uiState.value = CitasUiState.Error("No se pudieron cargar las citas. Intenta nuevamente.")
            }
        }
    }
}
