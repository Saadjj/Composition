package com.bignerdranch.android.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.composition.R
import com.bignerdranch.android.composition.databinding.FragmentGameBinding
import com.bignerdranch.android.composition.domain.entity.GameResult
import com.bignerdranch.android.composition.domain.entity.Level


class GameFragment : Fragment() {
    //вызов аргументов из класса GameFragment
    // используя JetPackNavigation, та же как и при
    // использовании by lazy, она не будет
    // проинициализирована  до обращения к ней
    private val args by navArgs<GameFragmentArgs>()

    private val viewModelFactory by lazy{
        GameViewModelFactory(args.level, requireActivity().application )
    }

    //обратить внимание, ленивая инициализация
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[GameViewModel::class.java]
    }

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null ")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //для того чтобы можно было вовремя реагировать на изменения в
        // объектах лайв дате и отписываться от них когда нужно
        // установить в переменную байндинг вью модель и лайв цикл
        binding.viewModel=viewModel
        binding.lifecycleOwner=viewLifecycleOwner
        observeViewModel()
        setClickListenersToOptions()

    }

    private fun setClickListenersToOptions() {
        for (tvOption in tvOptions) {
            tvOption.setOnClickListener {
                viewModel.chooseAnswer(tvOption.text.toString().toInt())
            }
        }
    }

    //подписываемся на объекты лайв дате
    private fun observeViewModel() {

        viewModel.gameResult.observe(viewLifecycleOwner){
            launchGameFinishedFragment(it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null    }




    /**
     * переход к фрагменту конца игры
     */
    private fun launchGameFinishedFragment(gameResult: GameResult) {
       // переход между фрагментами осуществляется с помощью jetpackNavigation, аргументы так же передаются через класс mainNavigationFragment
       findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult))
    }


}
