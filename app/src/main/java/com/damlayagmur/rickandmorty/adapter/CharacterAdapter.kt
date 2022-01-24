package com.damlayagmur.rickandmorty.adapter

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.damlayagmur.rickandmorty.databinding.ListCharacterBinding
import com.damlayagmur.rickandmorty.model.Result
import com.damlayagmur.rickandmorty.util.layoutInflaterFactory
import com.squareup.picasso.Picasso

class CharacterAdapter(private val characterList: List<Result?>?) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private lateinit var binding: ListCharacterBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        binding = ListCharacterBinding.inflate(parent.layoutInflaterFactory(), parent, false)
        return CharacterViewHolder(binding)
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

    inner class CharacterViewHolder(binding: ListCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val characterImage: ImageView = binding.imageViewPhoto
        val characterName: TextView = binding.textViewName
        val characterStatus: TextView = binding.textViewStatus
        val characterSpecies: TextView = binding.textViewSpecies
    }
}