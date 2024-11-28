package com.example.pmyat

import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private lateinit var scoreTextView: TextView

    private var gridSize: Int = 4 // Tamaño del tablero predeterminado
    private var firstCardIndex: Int? = null
    private var matchedPairs = 0
    private var attempts = 0
    private var score = 0

    private lateinit var cards: MutableList<Card>
    private val images = listOf(
        R.drawable.animal1, R.drawable.animal2, R.drawable.fruit1, R.drawable.fruit2,
        R.drawable.sport1, R.drawable.sport2, R.drawable.bebida1, R.drawable.bebida2,
        R.drawable.dragonz1, R.drawable.dragonz2, R.drawable.pais1, R.drawable.pais2,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Inicializar vistas
        gridLayout = findViewById(R.id.gridLayout)
        scoreTextView = findViewById(R.id.scoreTextView)

        // Obtener el tamaño del tablero desde el Intent
        gridSize = intent.getIntExtra("GRID_SIZE", 4)
        Log.d("GameActivity", "Tamaño del tablero recibido: $gridSize")

        setupGame() // Configurar el tablero
    }

    private fun setupGame() {
        val numCards = gridSize * gridSize

        // Validar que haya suficientes imágenes disponibles
        if (images.size * 2 < numCards) {
            throw IllegalArgumentException("No hay suficientes imágenes para un tablero de $gridSize x $gridSize")
        }

        // Seleccionar imágenes y duplicarlas para formar pares
        val selectedImages = (images.shuffled().take(numCards / 2) + images.shuffled().take(numCards / 2)).shuffled()

        // Crear lista de tarjetas
        cards = selectedImages.map { Card(it) }.toMutableList()

        // Configurar el GridLayout dinámicamente
        gridLayout.rowCount = gridSize
        gridLayout.columnCount = gridSize
        gridLayout.removeAllViews()

        // Agregar tarjetas al GridLayout
        cards.forEachIndexed { index, card ->
            val cardView = layoutInflater.inflate(R.layout.card_item, gridLayout, false) as ImageView
            cardView.setImageResource(R.drawable.card_back)
            cardView.setOnClickListener {
                onCardClicked(index, cardView)
            }
            gridLayout.addView(cardView)
        }

        // Reiniciar estadísticas
        matchedPairs = 0
        attempts = 0
        score = 0
        updateScore()
    }

    private fun onCardClicked(index: Int, cardView: ImageView) {
        val card = cards[index]

        // Ignorar clics en tarjetas que ya están volteadas o emparejadas
        if (card.isMatched || card.isFaceUp) return

        // Voltear la tarjeta
        card.isFaceUp = true
        cardView.setImageResource(card.imageRes)
        Log.d("GameActivity", "Carta volteada en la posición $index, imagen: ${card.imageRes}")

        if (firstCardIndex == null) {
            // Primera tarjeta seleccionada
            firstCardIndex = index
        } else {
            // Segunda tarjeta seleccionada
            val firstCard = cards[firstCardIndex!!]

            // Comparar las tarjetas
            gridLayout.postDelayed({
                if (firstCard.imageRes == card.imageRes) {
                    // Las tarjetas coinciden
                    firstCard.isMatched = true
                    card.isMatched = true
                    matchedPairs++
                    score += 10
                    Log.d("GameActivity", "¡PAREJA encontrada!")

                    // Verificar si el juego ha terminado
                    if (matchedPairs == cards.size / 2) {
                        showGameOver()
                    }
                } else {
                    // Las tarjetas no coinciden, voltearlas de nuevo después de un retraso
                    firstCard.isFaceUp = false
                    card.isFaceUp = false
                    (gridLayout.getChildAt(firstCardIndex!!) as ImageView).setImageResource(R.drawable.card_back)
                    cardView.setImageResource(R.drawable.card_back)
                    Log.d("GameActivity", "No coinciden, volviendo a voltear.")
                }

                // Reiniciar el índice para el próximo intento
                firstCardIndex = null
                attempts++
                updateScore()
            }, 1000)  // Retraso para dar tiempo a ver las cartas

        }
    }

    private fun updateScore() {
        scoreTextView.text = "Puntaje: $score | Intentos: $attempts"
    }

    private fun showGameOver() {
        AlertDialog.Builder(this)
            .setTitle("¡Juego Terminado!")
            .setMessage("Puntaje final: $score\nIntentos fallidos: $attempts")
            .setPositiveButton("Reiniciar") { _, _ -> setupGame() }
            .setNegativeButton("Salir") { _, _ -> finish() }
            .show()
    }

    data class Card(val imageRes: Int, var isFaceUp: Boolean = false, var isMatched: Boolean = false)
}
