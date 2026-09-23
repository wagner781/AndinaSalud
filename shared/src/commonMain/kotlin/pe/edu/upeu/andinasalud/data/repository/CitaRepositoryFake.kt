package pe.edu.upeu.andinasalud.data.repository

import kotlinx.coroutines.delay
import pe.edu.upeu.andinasalud.data.local.CitasSimuladas
import pe.edu.upeu.andinasalud.domain.model.Cita
import pe.edu.upeu.andinasalud.domain.model.EstadoCita
import pe.edu.upeu.andinasalud.domain.model.NuevaCita
import pe.edu.upeu.andinasalud.domain.repository.CitaRepository

/** Implementación temporal en memoria, sustituible por una implementación remota. */
class CitaRepositoryFake : CitaRepository {
    private val citas = CitasSimuladas.citas.toMutableList()

    override suspend fun obtenerPaciente() = CitasSimuladas.paciente

    override suspend fun obtenerCitas(): List<Cita> {
        delay(800)
        return citas.toList()
    }

    override suspend fun obtenerSedes() = CitasSimuladas.sedes

    override suspend fun obtenerEspecialidades() = CitasSimuladas.especialidades

    override suspend fun obtenerMedicos() = CitasSimuladas.medicos

    override suspend fun guardarCita(nuevaCita: NuevaCita): Cita {
        val cita = Cita(
            id = "C-${(citas.size + 1).toString().padStart(3, '0')}",
            especialidadId = nuevaCita.especialidadId,
            medicoId = nuevaCita.medicoId,
            sedeId = nuevaCita.sedeId,
            fecha = nuevaCita.fecha,
            hora = nuevaCita.hora,
            motivo = nuevaCita.motivo,
            estado = EstadoCita.Programada(recordatorioActivo = true),
        )
        citas += cita
        return cita
    }
}
