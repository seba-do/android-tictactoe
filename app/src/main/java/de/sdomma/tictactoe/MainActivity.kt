package de.sdomma.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import de.sdomma.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val PLAYER_ONE_COUNTER_KEY = "playerOneCounterKey"
        const val PLAYER_TWO_COUNTER_KEY = "playerTwoCounterKey"
        const val DRAW_COUNTER_KEY = "drawCounterKey"
        const val GAME_BOARD_KEY = "gameBoardKey"
        const val GAME_STATUS_KEY = "gameStatusKey"
        const val CURRENT_PLAYER_KEY = "currentPlayer"
    }

    private lateinit var binding: ActivityMainBinding

    private var myGame = Game()
    private var inputFields: MutableList<ImageView>? = null

    private var counterOne: Int = 0
        set(value) {
            field = value
            binding.counterOne.text = value.toString()
        }

    private var counterTwo: Int = 0
        set(value) {
            field = value
            binding.counterTwo.text = value.toString()
        }

    private var counterDraw: Int = 0
        set(value) {
            field = value
            binding.counterDraw.text = value.toString()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputFields = mutableListOf(
            binding.input0,
            binding.input1,
            binding.input2,
            binding.input3,
            binding.input4,
            binding.input5,
            binding.input6,
            binding.input7,
            binding.input8
        ).apply {
            this.forEachIndexed { index, iv ->
                iv.setOnClickListener {
                    //  (2 / 3 = 0) (2 % 3 = 2)
                    onUserInput(iv, index / 3, index % 3)
                }
            }
        }

        binding.btnReset.setOnClickListener {
            resetGame()
        }

        setMessage(myGame.gameStatus)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(PLAYER_ONE_COUNTER_KEY, counterOne)
        outState.putInt(PLAYER_TWO_COUNTER_KEY, counterTwo)
        outState.putInt(DRAW_COUNTER_KEY, counterDraw)

        outState.putSerializable(GAME_BOARD_KEY, myGame.board)
        outState.putSerializable(CURRENT_PLAYER_KEY, myGame.currentPlayer)
        outState.putSerializable(GAME_STATUS_KEY, myGame.gameStatus)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        counterOne = savedInstanceState.getInt(PLAYER_ONE_COUNTER_KEY)
        counterTwo = savedInstanceState.getInt(PLAYER_TWO_COUNTER_KEY)
        counterDraw = savedInstanceState.getInt(DRAW_COUNTER_KEY)

        val board = savedInstanceState.getSerializable(GAME_BOARD_KEY) as Array<Array<Player?>>
        val currentPlayer = savedInstanceState.getSerializable(CURRENT_PLAYER_KEY) as Player
        val gameStatus = savedInstanceState.getSerializable(GAME_STATUS_KEY) as GameStatus
        myGame = Game(board, currentPlayer, gameStatus)

        resetAllPlayerIcons()
    }

    private fun onUserInput(inputField: ImageView, x: Int, y: Int) {
        if (myGame.gameStatus != GameStatus.RUNNING || !myGame.isValidInput(x, y)) return

        // Make move on the gameboard
        myGame.makeMove(x, y)

        // Show move on the UI
        setPlayerIcon(inputField, getIconForPlayer(myGame.currentPlayer))

        // Check board for win or Draw
        val gameStatus = myGame.checkBoard(myGame.board)

        updateCounters(gameStatus)
        setResetButtonVisibility(gameStatus)
        myGame.switchPlayer(gameStatus)

        // Show message according to the gameStatus
        setMessage(gameStatus)
    }

    private fun getIconForPlayer(player: Player): Int = when (player) {
        Player.ONE -> R.drawable.ic_player_one
        Player.TWO -> R.drawable.ic_player_two
    }

    private fun updateCounters(gameStatus: GameStatus) {
        when (gameStatus) {
            GameStatus.WON -> {
                if (myGame.currentPlayer == Player.ONE) {
                    counterOne++
                } else {
                    counterTwo++
                }
            }
            GameStatus.DRAW -> counterDraw++
            else -> Unit
        }
    }

    private fun setResetButtonVisibility(gameStatus: GameStatus) {
        binding.btnReset.visibility =
            if (gameStatus != GameStatus.RUNNING) View.VISIBLE else View.INVISIBLE
    }

    private fun setMessage(gameStatus: GameStatus) {
        when (gameStatus) {
            GameStatus.WON -> getString(R.string.msg_won_player, myGame.currentPlayer)
            GameStatus.DRAW -> getString(R.string.msg_draw)
            GameStatus.RUNNING -> getString(R.string.msg_turn_player, myGame.currentPlayer)
        }.let {
            binding.message.text = it
        }
    }

    private fun setPlayerIcon(inputField: ImageView, icon: Int) {
        inputField.setImageResource(icon)
    }

    private fun resetAllPlayerIcons() {
        myGame.board.flatten().forEachIndexed { index, player ->
            if (player != null) {
                inputFields?.getOrNull(index)?.let {
                    setPlayerIcon(it, getIconForPlayer(player))
                }
            }
        }
    }

    private fun resetGame() {
        myGame = Game()
        inputFields?.forEach { it.setImageDrawable(null) }

        setResetButtonVisibility(myGame.gameStatus)
        setMessage(myGame.gameStatus)
    }
}