package de.sdomma.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private var myGame = Game()
    private var inputFields: MutableList<ImageView>? = null

    private lateinit var currentPlayerTV: TextView
    private lateinit var winningMessageTV: TextView
    private lateinit var btnReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentPlayerTV = findViewById(R.id.current_player_tv)
        winningMessageTV = findViewById(R.id.winning_message_tv)
        btnReset = findViewById(R.id.btn_reset)

        val input0: ImageView = findViewById(R.id.input_0)
        val input1: ImageView = findViewById(R.id.input_1)
        val input2: ImageView = findViewById(R.id.input_2)
        val input3: ImageView = findViewById(R.id.input_3)
        val input4: ImageView = findViewById(R.id.input_4)
        val input5: ImageView = findViewById(R.id.input_5)
        val input6: ImageView = findViewById(R.id.input_6)
        val input7: ImageView = findViewById(R.id.input_7)
        val input8: ImageView = findViewById(R.id.input_8)

        inputFields = mutableListOf(
            input0, input1, input2, input3, input4, input5, input6, input7, input8
        ).apply {
            this.forEachIndexed { index, iv ->
                iv.setOnClickListener {
                    //  (2 / 3 = 0) (2 % 3 = 2)
                    dothething(iv, index / 3, index % 3)
                }
            }
        }

        currentPlayerTV.text = myGame.currentPlayer.toString()

        btnReset.setOnClickListener {
            resetGame()
        }

        /*input0.setOnClickListener {
            dothething(winningMessageTV, currentPlayerTV, input0, 0, 0)
        }

        input1.setOnClickListener {
            dothething(winningMessageTV, currentPlayerTV, input1, 0, 1)
        }

        input2.setOnClickListener {
            dothething(winningMessageTV, currentPlayerTV, input2, 0, 2)
        }

        input3.setOnClickListener {
            dothething(winningMessageTV, currentPlayerTV, input3, 1, 0)
        }

        input4.setOnClickListener {
            dothething(winningMessageTV, currentPlayerTV, input4, 1, 1)
        }

        input5.setOnClickListener {
            dothething(winningMessageTV, currentPlayerTV, input5, 1, 2)
        }

        input6.setOnClickListener {
            dothething(winningMessageTV, currentPlayerTV, input6, 2, 0)
        }

        input7.setOnClickListener {
            dothething(winningMessageTV, currentPlayerTV, input7, 2, 1)
        }

        input8.setOnClickListener {
            dothething(winningMessageTV, currentPlayerTV, input8, 2, 2)
        }*/
    }

    private fun dothething(
        inputField: ImageView,
        x: Int,
        y: Int
    ) {
        // Game is running AND valid input (Field is not already taken)
//            if (myGame.gameStatus != GameStatus.RUNNING || !myGame.isValidInput(0, 0)) return@setOnClickListener
        if (myGame.gameStatus == GameStatus.RUNNING && myGame.isValidInput(x, y)) {

            // Make move on the gameboard
            myGame.makeMove(x, y)

            // Show move on the UI
            inputField.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    if (myGame.currentPlayer == Player.ONE) {
                        // Set icon for player one
                        R.drawable.ic_player_one
                    } else {
                        // Set icon for player two
                        R.drawable.ic_player_two
                    }, null
                )
            )

            // Checkboard for win or Draw
            myGame.checkBoard(myGame.board)

            if (myGame.gameStatus == GameStatus.WON) {
                winningMessageTV.apply {
                    text = "Won"
                    visibility = View.VISIBLE
                }
                // Solution 1
//                btnReset.visibility = View.VISIBLE
            }

            if (myGame.gameStatus == GameStatus.DRAW) {
                winningMessageTV.apply {
                    text = "Draw"
                    visibility = View.VISIBLE
                }
//                btnReset.visibility = View.VISIBLE
            }

            // Solution 2
//            if (myGame.gameStatus == GameStatus.WON || myGame.gameStatus == GameStatus.DRAW) {
//                btnReset.visibility = View.VISIBLE
//            }

            // Solution 3
//            if (myGame.gameStatus != GameStatus.RUNNING) {
//                btnReset.visibility = View.VISIBLE
//            }

            // Solution 4 - 5
            btnReset.visibility = if (myGame.gameStatus != GameStatus.RUNNING) View.VISIBLE else View.INVISIBLE

            // Solution 5 - 3
//            btnReset.isVisible = myGame.gameStatus != GameStatus.RUNNING

            // Switch Player
            myGame.switchPlayer()
            currentPlayerTV.text = myGame.currentPlayer.toString()
        }
    }

    private fun resetGame() {
        inputFields?.forEach {
            it.setImageDrawable(null)
        }

        myGame = Game()

        btnReset.visibility = View.INVISIBLE
        winningMessageTV.visibility = View.INVISIBLE

        currentPlayerTV.text = myGame.currentPlayer.toString()
    }
}