package pe.edu.upeu.andinasalud.domain.repository

import pe.edu.upeu.andinasalud.domain.model.Cita
import pe.edu.upeu.andinasalud.domain.model.Especialidad
import pe.edu.upeu.andinasalud.domain.model.Medico
import pe.edu.upeu.andinasalud.domain.model.NuevaCita
import pe.edu.upeu.andinasalud.domain.model.Paciente
import pe.edu.upeu.andinasalud.domain.model.Sede

/** Contrato del dominio; la UI no conoce el origen de los datos. */
interface CitaRepository {
    suspend fun obtenerPaciente(): Paciente
    suspend fun obtenerCitas(): List<Cita>
    suspend fun obtenerSedes(): List<Sede>
    suspend fun obtenerEspecialidades(): List<Especialidad>
    suspend fun obtenerMedicos(): List<Medico>
    suspend fun guardarCita(nuevaCita: NuevaCita): Cita
}
