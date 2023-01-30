package com.example.rickandmortypagingmvvm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortypagingmvvm.data.CharacterMorty


class CharacterAdapter() :
    PagingDataAdapter<CharacterMorty,CharacterAdapter.ViewHolder>(DiffUtilCallBack()) {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val nameTextView: TextView = view.findViewById(R.id.nameTextView)
            val imageView: ImageView = view.findViewById(R.id.imageView)

            fun bind(characterRick: CharacterMorty) {
                nameTextView.text = characterRick.name

                Glide.with(imageView.context)
                    .load(characterRick.image)
                    .into(imageView)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.character_item, parent, false)
            return ViewHolder(view)
        }



        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = getItem(position)
            if (item != null) {
                holder.bind(item)
            }

        }

    class DiffUtilCallBack: DiffUtil.ItemCallback<CharacterMorty>() {
        override fun areItemsTheSame(oldItem: CharacterMorty, newItem: CharacterMorty): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterMorty, newItem: CharacterMorty): Boolean {
            return  oldItem.id == newItem.id
        }
    }
    }
