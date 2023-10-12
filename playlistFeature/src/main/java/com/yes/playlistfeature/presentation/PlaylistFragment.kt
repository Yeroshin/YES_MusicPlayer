package com.yes.playlistfeature.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.yes.playlistfeature.databinding.FragmentPlaylistBinding


class PlaylistFragment : Fragment() {
    interface MediaChooserManager {
        fun showMediaDialog()
    }

    interface PlaylistManager {
        fun showPlaylistDialog()
    }

    private lateinit var binding: FragmentPlaylistBinding

    private val mediaChooserManager: MediaChooserManager by lazy {
        activity as MediaChooserManager
    }
    private val playlistManager: PlaylistManager by lazy {
        activity as PlaylistManager
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentPlaylistBinding.inflate(inflater)
        binding.btnMedia.setOnClickListener {
            mediaChooserManager.showMediaDialog()
        }
        binding.btnPlaylist.setOnClickListener {
            playlistManager.showPlaylistDialog()
        }
        return binding.root
    }


    class Dependency(

    )
}