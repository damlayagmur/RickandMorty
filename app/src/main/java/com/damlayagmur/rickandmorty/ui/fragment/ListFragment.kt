package com.damlayagmur.rickandmorty.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.GridLayout
import android.widget.GridView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.damlayagmur.rickandmorty.R
import com.damlayagmur.rickandmorty.adapter.CharacterAdapter
import com.damlayagmur.rickandmorty.databinding.FragmentListBinding
import com.damlayagmur.rickandmorty.model.Character
import com.damlayagmur.rickandmorty.model.Info
import com.damlayagmur.rickandmorty.model.Result
import com.damlayagmur.rickandmorty.model.State
import com.damlayagmur.rickandmorty.util.showLoading
import com.damlayagmur.rickandmorty.util.showToast
import com.damlayagmur.rickandmorty.util.viewBinding
import com.damlayagmur.rickandmorty.viewmodel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var recyclerView: RecyclerView
    private var characterList: List<Result?>? = null
    private var infoList: List<Info?>? = null
    private lateinit var tempArrayList: ArrayList<Result?>
    private lateinit var characterAdapter: CharacterAdapter
    private var gridLayoutManager: GridLayoutManager = GridLayoutManager(context, 1)
    var page = 1
    //var baseContext: Context? = null

    private val binding by viewBinding(FragmentListBinding::bind)

    private val characterViewModel: CharacterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)


        /*recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    nextPage()
                }
            }
        })*/

        tempArrayList = arrayListOf<Result?>()

        observeLiveData()


        binding.gridView.setOnClickListener {
            //recyclerView.layoutManager.toString()
            //if(recyclerView.layoutManager == LinearLayoutManager(requireContext())){
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
            //}else{
            //recyclerView.layoutManager = LinearLayoutManager(requireContext())
            // }
        }
    }

    /*private fun nextPage(): Int {
        if (characterList != null) {
            var lastCharacterResp = characterList!!.last()
            if (lastCharacterResp != null) {
                if (lastCharacterResp.info.next != null) {
                    page += 1
                }
            }
        }
        return page
    }*/

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
        /*characterViewModel.getInfo()
        characterViewModel.infoListLiveData.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Loading -> showLoading(binding.progressBar, true)
                is State.Success -> {
                    infoList = state.data
                    showToast(requireContext(),"aaaa")
                }
                is State.Error -> {
                    showToast(requireContext(), state.message)
                }
            }
        })*/
    }

    private fun setCharacterRecyclerView(characterList1: List<Result?>?) {
        if (characterList?.isNotEmpty() == true) {
            characterAdapter = CharacterAdapter(characterList)
            recyclerView.adapter = characterAdapter

            characterAdapter.setOnItemClickListener(object : CharacterAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    val action = characterList!![position]?.let {
                        //val characterId = it.getCharacterId()

                        ListFragmentDirections.actionListFragmentToDetailFragment(
                            it.name,
                            it.status,
                            it.image,
                            it.episode.size,
                            it.species,
                            it.gender,
                            it.origin.name,
                            it.location.name
                        )
                    }
                    findNavController().navigate(action!!)

                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_item, menu)
        val item = menu.findItem(R.id.item_search)
        val searchView = item?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tempArrayList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    characterList!!.forEach {
                        if (it!!.name.toLowerCase(Locale.getDefault()).contains(searchText)) {
                            tempArrayList.add(it)
                        }
                    }
                    recyclerView.adapter!!.notifyDataSetChanged()
                    setCharacterRecyclerView(tempArrayList)
                } else {
                    tempArrayList.clear()
                    tempArrayList.addAll(characterList!!)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_grid) {
            switchLayout()
            switchIcon(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun switchLayout() {

        if (gridLayoutManager.spanCount == 1) {
            gridLayoutManager.spanCount = 2
            recyclerView.layoutManager = gridLayoutManager
        } else {
            gridLayoutManager.spanCount = 1
            recyclerView.layoutManager = gridLayoutManager
        }
        characterAdapter.notifyItemRangeChanged(0, characterAdapter.itemCount)
    }

    private fun switchIcon(item: MenuItem) {
        if (gridLayoutManager.spanCount == 1) {
            item.setIcon(R.drawable.ic_baseline_grid_on_24)
        } else {
            item.setIcon(R.drawable.ic_baseline_list_24)
        }
    }

/*override fun onAttach(context: Context) {
    super.onAttach(context)
    baseContext = context
}*/
}


