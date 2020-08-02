package edu.example.broders.englishwords.quizzlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.example.broders.englishwords.R
import edu.example.broders.englishwords.TextItemViewHolder
import edu.example.broders.englishwords.database.Quizz

class QuizzAdapter : RecyclerView.Adapter<TextItemViewHolder>(){
    var data = listOf<Quizz>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.quizzName.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.text_item_view, parent, false) as TextView
        return TextItemViewHolder(view)
    }


}