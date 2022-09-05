package com.bignerdranch.android.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bignerdranch.android.composition.R

import com.bignerdranch.android.composition.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {
    ///осуществлене привязки через binding
    //проблема в том что иштвштп можно взывать из любого метода жизненного цикла фрагмента, что не хорошо, именно для этого все танцы с бубном(нуллабельные перменныеперменные)
    private var _binding: FragmentWelcomeBinding? = null

    //в нормальных методах следует использовать эту переменную
    private val binding: FragmentWelcomeBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding==null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //получение экзмепляра привязки
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonUnderstand.setOnClickListener{
            launchChooseLevelFragment()
        }
    }

    /**
     * переход к фрагменту выбора уровня
     */
    private fun launchChooseLevelFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, ChooseLevelFragment.newInstance())
            .addToBackStack(ChooseLevelFragment.NAME)
            .commit()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
