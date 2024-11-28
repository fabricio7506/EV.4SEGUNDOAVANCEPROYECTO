// Presentando el avance de la aplicacion para un juego de memoria llamado PÁMYAT
// Mi equipo y yo integrados por:
// - Fabricio Carrazco Arana
// - Arnold Saya Ramos
// - Luigui Huanca Capira
// Estos archivos crean una pantalla principal en la aplicación donde
// el usuario puede navegar hacia el juego o ver opciones básicas de
// puntuación y ajustes. Nuestra estructura asegura que cada actividad tenga
// el diseño visual y comportamiento esperado para una experiencia de
// usuario amena y consistente.

package com.example.pmyat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPlay = findViewById<Button>(R.id.btnPlay)
        val btnScore = findViewById<Button>(R.id.btnScore)


        // Navegación a GameSelectionActivity al hacer clic en "Jugar"
        btnPlay.setOnClickListener {
            val intent = Intent(this, GameSelectionActivity::class.java)
            startActivity(intent)
        }

        // Navegación a una actividad de Puntuación (puedes crear una ScoreActivity)
        btnScore.setOnClickListener {
            // Cambiar Toast por navegación cuando implementes la actividad de puntuación
            Toast.makeText(this, "Botón Puntuación presionado", Toast.LENGTH_SHORT).show()
        }

    }
}
