package com.damlayagmur.rickandmorty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.damlayagmur.rickandmorty.R
import com.damlayagmur.rickandmorty.databinding.GridCharacterBinding
import com.damlayagmur.rickandmorty.databinding.ListCharacterBinding
import com.damlayagmur.rickandmorty.model.Result
import com.damlayagmur.rickandmorty.util.layoutInflaterFactory
import com.squareup.picasso.Picasso

class CharacterAdapter(private val characterList: List<Result?>?) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private val LIST_ITEM = 0
    private val GRID_ITEM = 1

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener
    }

    //private lateinit var binding: ListCharacterBinding
    //private lateinit var binding2: GridCharacterBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view: View
        if (viewType == GRID_ITEM) {
            //binding = ListCharacterBinding.inflate(parent.layoutInflaterFactory(), parent, false)
            view = LayoutInflater.from(parent.context).inflate(R.layout.list_character, parent, false);
        } else {
            //binding = ListCharacterBinding.inflate(parent.layoutInflaterFactory(), parent, false)
            view = LayoutInflater.from(parent.context).inflate(R.layout.grid_character, parent, false);

        }
        return CharacterViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val currentCharacter = characterList!![position]
        //Picasso.get().load(currentCharacter?.image).into(holder.characterImage)
        holder.characterName.text = currentCharacter?.name
        //holder.characterStatus.text = currentCharacter?.status
        //holder.characterSpecies.text = currentCharacter?.species
    }

    override fun getItemCount(): Int {
        return characterList!!.size
    }

    inner class CharacterViewHolder(view: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(view) {
        //val characterImage: ImageView = view.findViewById(R.id.imageView_photo)
        val characterName: TextView = view.findViewById(R.id.textView_name)
        //val characterStatus: TextView = binding.textViewStatus
        //val characterSpecies: TextView = binding.textViewSpecies

        init {
            view.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}