package com.yes.playlistfeature.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.yes.playlistfeature.R
import com.yes.playlistfeature.databinding.FragmentPlaylistBinding
import com.yes.playlistfeature.di.components.DaggerPlayListComponent

import com.yes.playlistfeature.di.module.PlayListModule

import javax.inject.Inject


class PlaylistFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistBinding
  /*  @Inject
     lateinit var trackDialog: DialogFragment*/

    override fun onAttach(context: Context) {
        super.onAttach(context)

      /*  DaggerPlayListComponent.builder()
            .build()
            .inject(this)*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding=FragmentPlaylistBinding.inflate(inflater)
        binding.btnAdd.setOnClickListener{
           // trackDialog.show(childFragmentManager,null)
        }
        return binding.root
    }



}