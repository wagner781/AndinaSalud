package pe.edu.upeu.andinasalud.data.local

import pe.edu.upeu.andinasalud.domain.model.Cita
import pe.edu.upeu.andinasalud.domain.model.Especialidad
import pe.edu.upeu.andinasalud.domain.model.EstadoCita
import pe.edu.upeu.andinasalud.domain.model.Medico
import pe.edu.upeu.andinasalud.domain.model.Paciente
import pe.edu.upeu.andinasalud.domain.model.Sede

/** Catálogo local de demostración. No realiza llamadas de red ni usa persistencia. */
object CitasSimuladas {
    val paciente = Paciente(
        id = "P-0417",
        nombre = "Lucía Quispe Mamani",
        documento = "70154823",
        correo = "lucia.quispe@correo.pe",
        telefono = "987654321",
    )

    val sedes = listOf(
        Sede("nana", "Ñaña"),
        Sede("chosica", "Chosica"),
        Sede("chaclacayo", "Chaclacayo"),
        Sede("santa-anita", "Santa Anita"),
    )

    val especialidades = listOf(
        Especialidad("medicina-general", "Medicina General"),
        Especialidad("odontologia", "Odontología"),
        Especialidad("pediatria", "Pediatría"),
        Especialidad("nutricion", "Nutrición"),
        Especialidad("psicologia", "Psicología"),
    )

    val medicos = listOf(
        Medico("med-01", "Dr. Iván Rojas", setOf("medicina-general"), setOf("nana", "chosica")),
        Medico("med-02", "Dra. Elena Paredes", setOf("medicina-general"), setOf("chaclacayo", "santa-anita")),
        Medico("med-03", "Dra. Rosa Flores", setOf("odontologia"), setOf("chosica", "santa-anita")),
        Medico("med-04", "Dr. Marco Salas", setOf("odontologia"), setOf("nana", "chaclacayo")),
        Medico("med-05", "Dra. Carla Núñez", setOf("pediatria"), setOf("chaclacayo", "nana")),
        Medico("med-06", "Dr. José Vega", setOf("pediatria"), setOf("chosica", "santa-anita")),
        Medico("med-07", "Lic. Ana Bermúdez", setOf("nutricion"), setOf("santa-anita", "chosica")),
        Medico("med-08", "Lic. Pablo León", setOf("nutricion"), setOf("nana", "chaclacayo")),
        Medico("med-09", "Ps. Luis Tapia", setOf("psicologia"), setOf("nana", "chosica")),
        Medico("med-10", "Ps. Mariela Soto", setOf("psicologia"), setOf("chaclacayo", "santa-anita")),
    )

    // Fechas programadas posteriores al 23/09/2026 (fecha de preparación del proyecto).
    val citas = listOf(
        Cita("C-001", "medicina-general", "med-01", "nana", "2026-10-05", "09:00", "Control general", EstadoCita.Programada(true)),
        Cita("C-002", "odontologia", "med-03", "chosica", "2026-10-12", "16:30", "Evaluación dental", EstadoCita.Programada(false)),
        Cita("C-003", "nutricion", "med-07", "santa-anita", "2026-10-20", "11:15", "Orientación nutricional", EstadoCita.Programada(true)),
        Cita("C-004", "pediatria", "med-05", "chaclacayo", "2026-08-30", "08:45", "Control pediátrico", EstadoCita.Atendida("Control en tres meses")),
        Cita("C-005", "psicologia", "med-09", "nana", "2026-09-02", "15:00", "Consulta de seguimiento", EstadoCita.Atendida("Continuar sesiones quincenales")),
        Cita("C-006", "medicina-general", "med-02", "chosica", "2026-09-05", "10:30", "Consulta general", EstadoCita.Cancelada("Viaje del paciente", true)),
    )
}
