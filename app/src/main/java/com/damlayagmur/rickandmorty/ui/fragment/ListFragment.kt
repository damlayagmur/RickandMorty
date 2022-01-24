package com.damlayagmur.rickandmorty.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.damlayagmur.rickandmorty.R
import com.damlayagmur.rickandmorty.adapter.CharacterAdapter
import com.damlayagmur.rickandmorty.databinding.FragmentListBinding
import com.damlayagmur.rickandmorty.model.Result
import com.damlayagmur.rickandmorty.util.showToast
import com.damlayagmur.rickandmorty.util.viewBinding
import androidx.lifecycle.Observer
import com.damlayagmur.rickandmorty.model.Character
import com.damlayagmur.rickandmorty.model.State
import com.damlayagmur.rickandmorty.repository.CharacterRepository
import com.damlayagmur.rickandmorty.util.showLoading
import com.damlayagmur.rickandmorty.viewmodel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var recyclerView: RecyclerView
    private var characterList: List<Result?>? = null
    private lateinit var tempArrayList: ArrayList<Result>
    private lateinit var characterAdapter: CharacterAdapter
    lateinit var imageId: Array<String>
    lateinit var name: Array<String>
    lateinit var status: Array<String>
    lateinit var species: Array<String>
    var index=0
    var baseContext: Context? = null

    private val binding by viewBinding(FragmentListBinding::bind)

    private val characterViewModel: CharacterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        characterList = arrayListOf<Result>()
        tempArrayList = arrayListOf<Result>()
        //getCharacterData()
        observeLiveData()
        //characterViewModel.getCharacter()
        //getAPiData()

    }

    private fun observeLiveData() {
        characterViewModel.getCharacter()
        characterViewModel.characterListLiveData.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Loading -> showLoading(binding.progressBar, true)
                is State.Success -> {
                    showLoading(binding.progressBar, false)
                    characterList = state.data
                    setCharacterRecyclerView(characterList)
                }
                is State.Error -> {
                    showToast(requireContext(), state.message)
                    showLoading(binding.progressBar, false)
                }
            }
        })
    }

    private fun setCharacterRecyclerView(characterList1: List<Result?>?){
        if(characterList?.isNotEmpty() == true){
            characterAdapter = CharacterAdapter(characterList)
            recyclerView.adapter = characterAdapter

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_item, menu)
        val item = menu.findItem(R.id.item_search)
        val searchView = item?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                tempArrayList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    while(index<characterList!!.size){

                    }
                    /*characterList.forEach {
                        if (it?.name.toLowerCase(Locale.getDefault()).contains(searchText)) {

                            tempArrayList.add(it?)
                        }
                    }*/
                    recyclerView.adapter!!.notifyDataSetChanged()
                } else {
                    tempArrayList.clear()
                    //tempArrayList.addAll(characterList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    /*private fun getCharacterData() {
        for (i in imageId.indices) {
            val news = Result(imageId[i], name[i], status[i], species[i])
            characterList.add(news)
        }
        recyclerView.adapter = CharacterAdapter(tempArrayList)

        tempArrayList.addAll(characterList)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseContext = context
    }*/
}