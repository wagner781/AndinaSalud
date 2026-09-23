package pe.edu.upeu.andinasalud.domain.usecase

import pe.edu.upeu.andinasalud.domain.model.Cita
import pe.edu.upeu.andinasalud.domain.model.EstadoCita
import pe.edu.upeu.andinasalud.domain.model.NuevaCita

/** Errores de negocio que la presentación puede asociar a sus campos. */
enum class CampoSolicitud { ESPECIALIDAD, SEDE, MEDICO, FECHA, HORA, MOTIVO }

data class ResultadoValidacionCita(val errores: Map<CampoSolicitud, String>) {
    val esValida: Boolean get() = errores.isEmpty()
}

/** RN-01, RN-02, RN-04 y RN-05. El reloj se inyecta como ISO local para hacerlo determinista. */
class ValidarNuevaCitaUseCase {
    operator fun invoke(
        nuevaCita: NuevaCita,
        citasExistentes: List<Cita>,
        ahoraFecha: String,
        ahoraHora: String,
    ): ResultadoValidacionCita {
        val errores = mutableMapOf<CampoSolicitud, String>()
        if (nuevaCita.especialidadId.isBlank()) errores[CampoSolicitud.ESPECIALIDAD] = "Selecciona una especialidad."
        if (nuevaCita.sedeId.isBlank()) errores[CampoSolicitud.SEDE] = "Selecciona una sede."
        if (nuevaCita.medicoId.isBlank()) errores[CampoSolicitud.MEDICO] = "Selecciona un médico."

        val fechaValida = esFechaIsoValida(nuevaCita.fecha)
        if (!fechaValida) {
            errores[CampoSolicitud.FECHA] = "Ingresa una fecha válida."
        } else if (esFechaIsoValida(ahoraFecha) && nuevaCita.fecha < ahoraFecha) {
            errores[CampoSolicitud.FECHA] = "La fecha debe ser hoy o posterior."
        }

        val horaValida = esHoraValida(nuevaCita.hora)
        if (!horaValida) {
            errores[CampoSolicitud.HORA] = "Ingresa una hora válida."
        } else if (fechaValida && nuevaCita.fecha == ahoraFecha && esHoraValida(ahoraHora) && nuevaCita.hora <= ahoraHora) {
            errores[CampoSolicitud.HORA] = "La hora debe ser posterior a la actual."
        }

        if (nuevaCita.motivo.length !in 10..200) {
            errores[CampoSolicitud.MOTIVO] = "El motivo debe tener entre 10 y 200 caracteres."
        }

        if (citasExistentes.count { it.estado is EstadoCita.Programada } >= MAX_CITAS_PROGRAMADAS) {
            errores[CampoSolicitud.FECHA] = "No puedes tener más de tres citas programadas."
        }
        if (citasExistentes.any {
                it.estado is EstadoCita.Programada && it.fecha == nuevaCita.fecha && it.hora == nuevaCita.hora
            }
        ) {
            errores[CampoSolicitud.HORA] = "Ya tienes una cita programada en ese horario."
        }
        return ResultadoValidacionCita(errores)
    }

    private fun esHoraValida(valor: String): Boolean {
        if (!valor.matches(Regex("\\d{2}:\\d{2}"))) return false
        val hora = valor.substring(0, 2).toInt()
        val minuto = valor.substring(3, 5).toInt()
        return hora in 0..23 && minuto in 0..59
    }

    private fun esFechaIsoValida(valor: String): Boolean {
        if (!valor.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) return false
        val anio = valor.substring(0, 4).toInt()
        val mes = valor.substring(5, 7).toInt()
        val dia = valor.substring(8, 10).toInt()
        if (mes !in 1..12) return false
        val bisiesto = anio % 4 == 0 && (anio % 100 != 0 || anio % 400 == 0)
        val diasPorMes = when (mes) {
            2 -> if (bisiesto) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }
        return dia in 1..diasPorMes
    }

    private companion object {
        const val MAX_CITAS_PROGRAMADAS = 3
    }
}
