package com.bignerdranch.android.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bignerdranch.android.composition.R
import com.bignerdranch.android.composition.databinding.FragmentGameBinding
import com.bignerdranch.android.composition.domain.entity.GameResult
import com.bignerdranch.android.composition.domain.entity.GameSettings
import com.bignerdranch.android.composition.domain.entity.Level


class GameFragment : Fragment() {

    private lateinit var level:Level

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding==0 ")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //потом удалить
        binding.tvQuestion.setOnClickListener{
            launchGameFinishedFragment(
                GameResult(
                true,20,30,
                GameSettings(0,0,0,0)
            )
            )
        }
    }

    /**
     * переход к фрагменту конца игры
     */
    private fun launchGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * распаковка сериализуемого объекта
     */
    private fun parseArgs(){
        //явное приведение
        level=requireArguments().getSerializable(KEY_LEVEL) as Level
    }

    companion object{

        const val NAME="GameFragment"

        private const val KEY_LEVEL="level"

        /**
         * фабричный метод для создания объектов GameFragment
         */
        fun newInstance(level: Level):GameFragment{
            return GameFragment().apply {
                arguments=Bundle().apply{
                    putSerializable(KEY_LEVEL,level)
                }
            }
        }
    }
}
