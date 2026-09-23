package pe.edu.upeu.andinasalud.domain.model

data class NuevaCita(
    val especialidadId: String,
    val medicoId: String,
    val sedeId: String,
    val fecha: String,
    val hora: String,
    val motivo: String,
)
