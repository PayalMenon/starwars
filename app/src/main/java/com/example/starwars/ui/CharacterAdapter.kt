package com.example.starwars.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwars.R
import com.example.starwars.models.Character
import kotlinx.android.synthetic.main.character_list_item.view.*

class CharacterAdapter(val clickListener : (Character) -> Unit ) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    var characterList : List<Character> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.character_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.setCharacterData(characterList[position], clickListener)
    }

    fun setCharactersList(characters : List<Character>) {
        characterList = characters
    }

    class CharacterViewHolder(val view : View) : RecyclerView.ViewHolder(view) {

        fun setCharacterData(character: Character, clickListener: (Character) -> Unit){
            view.name.text = character.name
            view.gender.text = character.gender
            view.character_parent.setOnClickListener { clickListener(character) }
        }
    }
}