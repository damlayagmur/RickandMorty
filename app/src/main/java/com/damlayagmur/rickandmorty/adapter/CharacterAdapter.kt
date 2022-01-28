package com.damlayagmur.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.damlayagmur.rickandmorty.R
import com.damlayagmur.rickandmorty.model.Result
import com.squareup.picasso.Picasso

class CharacterAdapter(
    private var characterList: List<Result?>?,
    private val mLayout: GridLayoutManager
) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private val LIST_ITEM = 0
    private val SPAN_COUNT_ONE: Int = 1
    private val SPAN_COUNT_TWO: Int = 2
    private lateinit var mListener: OnAdapterItemClickListener


    interface OnAdapterItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnAdapterItemClickListener) {

        mListener = listener
    }

    //private lateinit var binding: ListCharacterBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        var view: View
        if (viewType == LIST_ITEM) {
            //binding = ListCharacterBinding.inflate(parent.layoutInflaterFactory(), parent, false)
            view =
                LayoutInflater.from(parent.context).inflate(R.layout.grid_character, parent, false)
        } else {
            view =
                LayoutInflater.from(parent.context).inflate(R.layout.list_character, parent, false)
        }
        return CharacterViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val currentCharacter = characterList!![position]
        Picasso.get().load(currentCharacter?.image).into(holder.characterImage)
        holder.characterName.text = currentCharacter?.name
        holder.characterStatus.text = currentCharacter?.status
        holder.characterSpecies.text = currentCharacter?.species
    }

    override fun getItemCount(): Int {
        return characterList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        val spanCount = mLayout.spanCount
        if (spanCount == 1) {
            return SPAN_COUNT_TWO
        } else {
            SPAN_COUNT_ONE
        }
        return super.getItemViewType(position)
    }

    inner class CharacterViewHolder(
        view: View,
        listener: OnAdapterItemClickListener
    ) :
        RecyclerView.ViewHolder(view) {
        val characterImage: ImageView = view.findViewById(R.id.imageView_photo)
        val characterName: TextView = view.findViewById(R.id.textView_name)
        val characterStatus: TextView = view.findViewById(R.id.textView_status)
        val characterSpecies: TextView = view.findViewById(R.id.textView_species)

        init {
            view.setOnClickListener {
                listener.onItemClick(adapterPosition)

            }
        }
    }
}