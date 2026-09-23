package pe.edu.upeu.andinasalud.domain.usecase

import pe.edu.upeu.andinasalud.domain.model.Cita
import pe.edu.upeu.andinasalud.domain.repository.CitaRepository

class ObtenerCitasUseCase(private val repository: CitaRepository) {
    suspend operator fun invoke(): List<Cita> = repository.obtenerCitas()
        .sortedWith(compareBy<Cita> { it.fecha }.thenBy { it.hora })
}
