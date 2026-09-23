package pe.edu.upeu.andinasalud.domain.model

data class Medico(
    val id: String,
    val nombre: String,
    val especialidadIds: Set<String>,
    val sedeIds: Set<String>,
)
