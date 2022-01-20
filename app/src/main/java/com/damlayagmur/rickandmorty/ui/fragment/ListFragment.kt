package com.damlayagmur.rickandmorty.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.damlayagmur.rickandmorty.R
import com.damlayagmur.rickandmorty.adapter.CharacterAdapter
import com.damlayagmur.rickandmorty.databinding.FragmentListBinding
import com.damlayagmur.rickandmorty.model.Result
import com.damlayagmur.rickandmorty.util.viewBinding

class ListFragment: Fragment(R.layout.fragment_list) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var characterList: ArrayList<Result>
    lateinit var imageId: Array<String>
    lateinit var name: Array<String>
    lateinit var status: Array<String>
    lateinit var species: Array<String>

    private val binding by viewBinding(FragmentListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageId = arrayOf(
            "R.drawable.ic_launcher_background",
            "R.drawable.ic_baseline_access_alarms_24"
        )

        name = arrayOf(
            "birinci",
            "ikinci"
        )
        status = arrayOf(
            "birinci1",
            "ikinci1"
        )
        species = arrayOf(
            "birinci2",
            "ikinci2"
        )

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        characterList = arrayListOf<Result>()
        getCharacterData()

    }

    private fun getCharacterData() {
        for(i in imageId.indices){
            val news = Result(imageId[i],name[i],status[i], species[i])
            characterList.add(news)
        }
        recyclerView.adapter = CharacterAdapter(characterList)
    }
}