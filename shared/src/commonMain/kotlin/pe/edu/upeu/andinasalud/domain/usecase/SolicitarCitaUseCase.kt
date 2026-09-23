package pe.edu.upeu.andinasalud.domain.usecase

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import pe.edu.upeu.andinasalud.domain.model.Cita
import pe.edu.upeu.andinasalud.domain.model.NuevaCita
import pe.edu.upeu.andinasalud.domain.repository.CitaRepository
import kotlin.time.Clock

sealed interface ResultadoSolicitudCita {
    data class Creada(val cita: Cita) : ResultadoSolicitudCita
    data class Invalida(val errores: Map<CampoSolicitud, String>) : ResultadoSolicitudCita
}

class SolicitarCitaUseCase(
    private val repository: CitaRepository,
    private val validar: ValidarNuevaCitaUseCase,
    private val obtenerAhora: () -> LocalDateTime = {
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    },
) {
    suspend operator fun invoke(nuevaCita: NuevaCita): ResultadoSolicitudCita {
        val citas = repository.obtenerCitas()
        val ahora = obtenerAhora()
        val resultado = validar(nuevaCita, citas, ahora.date.toString(), ahora.time.toString().take(5))
        if (!resultado.esValida) return ResultadoSolicitudCita.Invalida(resultado.errores)
        return ResultadoSolicitudCita.Creada(repository.guardarCita(nuevaCita))
    }
}
