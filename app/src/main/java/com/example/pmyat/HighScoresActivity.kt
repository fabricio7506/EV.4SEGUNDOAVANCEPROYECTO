package com.example.pmyat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HighScoresActivity : AppCompatActivity() {

    private lateinit var highScoresTextView: TextView
    private lateinit var scoresListTextView: TextView // Para mostrar la lista de puntuaciones
    private lateinit var btnBack: Button  // El botón de volver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_scores)

        // Referencias de vistas
        highScoresTextView = findViewById(R.id.highScoresTextView)
        scoresListTextView = findViewById(R.id.scoresListTextView)
        btnBack = findViewById(R.id.btnBack)

        // Aquí podrías cargar puntuaciones desde una base de datos o preferencia compartida.
        val highScores = listOf(120, 85, 50)  // Ejemplo de puntuaciones
        val scoresString = highScores.joinToString("\n") { "Puntaje: $it" }

        // Establecer el texto de puntuaciones
        scoresListTextView.text = scoresString

        // Configurar el listener para el botón "Volver"
        btnBack.setOnClickListener {
            // Regresar a GameSelectionActivity
            val intent = Intent(this, GameSelectionActivity::class.java)
            startActivity(intent)
            finish()  // Cerrar la actividad actual para que no quede en la pila de actividades
        }
    }
}
