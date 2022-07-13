package com.estudo.rickandmorty

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.estudo.rickandmorty.databinding.FragmentEpisodeListBinding

class EpisodeListFragment: Fragment(R.layout.fragment_episode_list) {

    private var _binding : FragmentEpisodeListBinding? = null
    private val binding : FragmentEpisodeListBinding by lazy {
        _binding!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEpisodeListBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}