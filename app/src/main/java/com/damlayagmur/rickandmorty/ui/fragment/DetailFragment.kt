package com.damlayagmur.rickandmorty.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.damlayagmur.rickandmorty.R
import com.damlayagmur.rickandmorty.R.drawable.favorite_black
import com.damlayagmur.rickandmorty.R.drawable.favorite_red
import com.damlayagmur.rickandmorty.database.FavoriteDB
import com.damlayagmur.rickandmorty.databinding.FragmentDetailBinding
import com.damlayagmur.rickandmorty.util.showToast
import com.damlayagmur.rickandmorty.util.viewBinding
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var favButton: Button
    private lateinit var db: FavoriteDB

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FavoriteDB(requireContext())

        val name = args.name
        val status = args.status
        val image = args.imaege
        val episode = args.episode
        val species = args.species
        val gender = args.gender
        val origin = args.origin
        val location = args.location
        var id = args.id

        binding.detailName.text = name
        binding.detailStatus.text = status
        binding.detailSpecies.text = species
        binding.detailEpisode.text = episode.toString()
        binding.detailGender.text = gender
        binding.detailOrigin.text = origin
        binding.detailLocation.text = location
        Picasso.get().load(image).transform(CropCircleTransformation()).into(binding.imageView)
        favButton = binding.favButton
        updateFavButton(id)
        favButton.setOnClickListener {
            if (isFav(id)) {
                db.deleteFav(id)
                favButton.setBackgroundResource(favorite_black)
            } else {
                db.addFav(id)
                favButton.setBackgroundResource(favorite_red)
            }
        }
    }

    private fun updateFavButton(id: Int) {
        if (isFav(id)) {
            favButton.setBackgroundResource(favorite_red)
        } else {
            favButton.setBackgroundResource(favorite_black)
        }
    }


    private fun isFav(id: Int): Boolean {
        val cursor = db.checkDB(id)
        val isFav = cursor!!.count != 0
        cursor.close()
        return isFav
    }
}