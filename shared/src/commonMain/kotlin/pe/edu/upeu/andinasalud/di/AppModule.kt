package pe.edu.upeu.andinasalud.di

import org.koin.core.context.startKoin
import org.koin.core.context.GlobalContext
import org.koin.dsl.module
import pe.edu.upeu.andinasalud.data.repository.CitaRepositoryFake
import pe.edu.upeu.andinasalud.domain.repository.CitaRepository
import pe.edu.upeu.andinasalud.domain.usecase.CancelarCitaUseCase
import pe.edu.upeu.andinasalud.domain.usecase.ObtenerCitasUseCase
import pe.edu.upeu.andinasalud.domain.usecase.SolicitarCitaUseCase
import pe.edu.upeu.andinasalud.domain.usecase.ValidarNuevaCitaUseCase
import pe.edu.upeu.andinasalud.presentation.citas.CitasViewModel

val appModule = module {
    single<CitaRepository> { CitaRepositoryFake() }
    factory { ValidarNuevaCitaUseCase() }
    factory { ObtenerCitasUseCase(get()) }
    factory { SolicitarCitaUseCase(get(), get()) }
    factory { CancelarCitaUseCase(get()) }
    factory { CitasViewModel(get()) }
}

fun initKoin() {
    if (GlobalContext.getOrNull() == null) {
        startKoin { modules(appModule) }
    }
}
