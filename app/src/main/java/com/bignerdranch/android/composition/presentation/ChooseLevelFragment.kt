package com.bignerdranch.android.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bignerdranch.android.composition.R
import com.bignerdranch.android.composition.databinding.FragmentChooseLevelBinding
import com.bignerdranch.android.composition.databinding.FragmentWelcomeBinding
import com.bignerdranch.android.composition.domain.entity.Level


class ChooseLevelFragment : Fragment() {
    private lateinit var lvl:Level
    ///осуществлене привязки через binding
    //проблема в том что иштвштп можно взывать из любого метода жизненного цикла фрагмента, что не хорошо, именно для этого все танцы с бубном(нуллабельные перменныеперменные)
    private var _binding: FragmentChooseLevelBinding? = null

    //в нормальных методах следует использовать эту переменную
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding==null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //установка слушателя кликов
        binding.buttonLevelTest.setOnClickListener{
            launchGameFragment(Level.TEST)
        }
        binding.buttonLevelEasy.setOnClickListener{
            launchGameFragment(Level.EASY)
        }
        binding.buttonLevelNormal.setOnClickListener{
            launchGameFragment(Level.NORMAL)
        }
        binding.buttonLevelHard.setOnClickListener{
            launchGameFragment(Level.HARD)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    /**
     * переход к фрагменту игры
     */
    private fun launchGameFragment(level: Level){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container,GameFragment.newInstance(level))
                //указываем имя фрагмента чтобы потом можно было его удалить из стека
            .addToBackStack(GameFragment.NAME)
            .commit()
    }
    companion object{

        const val NAME="ChooseLevelFragment"

        fun newInstance():ChooseLevelFragment{
            return ChooseLevelFragment()
        }
    }

}
