package com.bignerdranch.android.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.composition.R
import com.bignerdranch.android.composition.databinding.FragmentGameFinishedBinding


class GameFinishedFragment : Fragment() {

    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding: FragmentGameFinishedBinding? = null
    //в нормальных методах следует использовать эту переменную
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding==null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        bindViews()

    }

    private fun setupClickListeners() {
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }

    /**
     * привязка вьюшек
     */
    private fun bindViews() {
        //установка gameResult в binding возможна благодаря тому что я
        // вручную через data запихнул ее в xml file
        binding.gameResult=args.gameResult

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * переход к началу игры
     */
    private fun retryGame() {
       findNavController().popBackStack()
    }


}

