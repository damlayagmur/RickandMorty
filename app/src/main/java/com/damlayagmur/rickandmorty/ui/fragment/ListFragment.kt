package com.damlayagmur.rickandmorty.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.damlayagmur.rickandmorty.R
import com.damlayagmur.rickandmorty.adapter.CharacterAdapter
import com.damlayagmur.rickandmorty.databinding.FragmentListBinding
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
    private lateinit var tempArrayList: ArrayList<Result?>
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private var spanCount = 1
    //var baseContext: Context? = null

    private val binding by viewBinding(FragmentListBinding::bind)

    private val characterViewModel: CharacterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        gridLayoutManager = GridLayoutManager(context, spanCount)
        recyclerView.layoutManager = gridLayoutManager
        tempArrayList = arrayListOf<Result?>()
        observeLiveData()
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
    }

    private fun setCharacterRecyclerView(characterList1: List<Result?>?) {
        if (characterList1?.isNotEmpty() == true) {
            characterAdapter = CharacterAdapter(characterList1, gridLayoutManager)
            recyclerView.adapter = characterAdapter

            characterAdapter.setOnItemClickListener(object :
                CharacterAdapter.onAdapterItemClickListener {
                override fun onItemClick(position: Int) {
                    val action = characterList1!![position]?.let {
                        ListFragmentDirections.actionListFragmentToDetailFragment(
                            it.name,
                            it.status,
                            it.image,
                            it.episode.size,
                            it.species,
                            it.gender,
                            it.origin.name,
                            it.location.name,
                            it.id
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
                        if (it!!.name.toLowerCase(Locale.getDefault())
                                .contains(searchText) || it!!.status.toLowerCase(Locale.getDefault())
                                .contains(searchText)
                        ) {
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
            //switchIcon(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun switchLayout() {
        val layoutManager = (recyclerView.layoutManager as GridLayoutManager)
        spanCount = if (spanCount == 2) 1 else 2
        layoutManager.spanCount = spanCount
        characterAdapter.notifyItemRangeChanged(0, characterAdapter.itemCount)
    }

    private fun switchIcon(item: MenuItem) {
        if (spanCount == 1) {
            item.setIcon(R.drawable.grid_mode)
        } else {
            item.setIcon(R.drawable.list_mode)
        }
    }

/*override fun onAttach(context: Context) {
    super.onAttach(context)
    baseContext = context
}*/
}


