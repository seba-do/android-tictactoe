package de.sdomma.tictactoe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import de.sdomma.tictactoe.databinding.ActivityFragmentsBinding
import de.sdomma.tictactoe.fragments.GameFragment
import de.sdomma.tictactoe.fragments.TitleFragment

class FragmentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityFragmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_holder)
        if (currentFragment is TitleFragment) {
            currentFragment.onButtonClick = {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_holder, GameFragment())
                    .commit()

//                supportFragmentManager.commit {
//                    replace<GameFragment>(R.id.fragment_holder)
//                }
            }
        }
    }
}