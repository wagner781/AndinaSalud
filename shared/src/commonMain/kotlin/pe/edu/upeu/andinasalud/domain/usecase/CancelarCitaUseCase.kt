package pe.edu.upeu.andinasalud.domain.usecase

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import pe.edu.upeu.andinasalud.domain.model.Cita
import pe.edu.upeu.andinasalud.domain.model.EstadoCita
import pe.edu.upeu.andinasalud.domain.repository.CitaRepository
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.Instant

sealed interface ResultadoCancelacionCita {
    data class Cancelada(val cita: Cita) : ResultadoCancelacionCita
    data object NoEncontrada : ResultadoCancelacionCita
    data object NoProgramada : ResultadoCancelacionCita
    data object FueraDelPlazo : ResultadoCancelacionCita
}

/** RN-03: solo permite cancelar una cita programada con más de 24 horas de anticipación. */
class CancelarCitaUseCase(
    private val repository: CitaRepository,
    private val obtenerAhora: () -> Instant = { Clock.System.now() },
) {
    suspend operator fun invoke(id: String, motivo: String = "Cancelada por el paciente"): ResultadoCancelacionCita {
        val cita = repository.obtenerCitas().firstOrNull { it.id == id }
            ?: return ResultadoCancelacionCita.NoEncontrada
        if (cita.estado !is EstadoCita.Programada) return ResultadoCancelacionCita.NoProgramada

        val zona = TimeZone.currentSystemDefault()
        val fechaHora = LocalDateTime(LocalDate.parse(cita.fecha), LocalTime.parse(cita.hora))
        val instanteCita = fechaHora.toInstant(zona)
        if (instanteCita - obtenerAhora() <= 24.hours) return ResultadoCancelacionCita.FueraDelPlazo

        val actualizada = repository.cancelarCita(id, motivo)
            ?: return ResultadoCancelacionCita.NoProgramada
        return ResultadoCancelacionCita.Cancelada(actualizada)
    }
}
