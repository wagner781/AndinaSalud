package pe.edu.upeu.andinasalud.domain.model

data class Cita(
    val id: String,
    val especialidadId: String,
    val medicoId: String,
    val sedeId: String,
    /** Fecha ISO-8601 local: yyyy-MM-dd. */
    val fecha: String,
    /** Hora local en formato HH:mm. */
    val hora: String,
    val motivo: String,
    val estado: EstadoCita,
)
