package pe.edu.upeu.andinasalud

import androidx.compose.ui.window.ComposeUIViewController
import pe.edu.upeu.andinasalud.di.initKoin

fun MainViewController() = run {
    initKoin()
    ComposeUIViewController { App() }
}
