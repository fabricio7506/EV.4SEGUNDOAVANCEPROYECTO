package com.example.pmyat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class GameSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_selection)

        // Referencia de los botones
        val btn4x4 = findViewById<Button>(R.id.btn4x4)
        val btn3x3 = findViewById<Button>(R.id.btn3x3)

        // Establecer las acciones de los botones
        btn4x4.setOnClickListener { startGame(4) }
        btn3x3.setOnClickListener { startGame(3) }
    }

    // Método para iniciar el juego con el tamaño de tablero seleccionado
    private fun startGame(size: Int) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("GRID_SIZE", size) // Pasar el tamaño del tablero a GameActivity
        startActivity(intent)
    }
}
