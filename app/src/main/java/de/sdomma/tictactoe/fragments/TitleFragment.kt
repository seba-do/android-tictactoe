package de.sdomma.tictactoe.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import de.sdomma.tictactoe.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    var onButtonClick: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTitleBinding.inflate(inflater)

        binding.btnStartGame.setOnClickListener {
            onButtonClick()

            Toast.makeText(activity, "", Toast.LENGTH_LONG).show()
        }

        return binding.root
    }
}