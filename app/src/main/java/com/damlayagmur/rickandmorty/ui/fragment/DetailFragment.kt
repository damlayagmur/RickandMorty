package com.damlayagmur.rickandmorty.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.damlayagmur.rickandmorty.R
import com.damlayagmur.rickandmorty.R.drawable.favorite_red
import com.damlayagmur.rickandmorty.database.FavoriteDB
import com.damlayagmur.rickandmorty.databinding.FragmentDetailBinding
import com.damlayagmur.rickandmorty.util.showToast
import com.damlayagmur.rickandmorty.util.viewBinding
import com.squareup.picasso.Picasso
import android.widget.ImageView
import jp.wasabeef.picasso.transformations.CropCircleTransformation


class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var favButton: Button

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = FavoriteDB(requireContext())
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


        favButton.setOnClickListener {
            var cursor2 = db.getName()
            if (cursor2!!.count == null) {
                db.addFav(id)
                showToast(requireContext(), "şimdi eklendi$id")
                favButton.setBackgroundResource(favorite_red)
            } else {
                while (cursor2!!.moveToNext()) {
                    var aa =
                        cursor2!!.getString(cursor2.getColumnIndex(FavoriteDB.CHARACTER_ID_COL))
                    if (aa.toString() == id.toString()) {
                        db.addFav(id)
                        showToast(requireContext(), "added $id")
                        favButton.setBackgroundResource(favorite_red)
                    } else {
                        favButton.setBackgroundResource(R.drawable.favorite_black)
                    }
                }
            }

            /*for(i in 1..17){
                db.checkDB(i)
            }*/

            cursor2.close()
        }
    }
}