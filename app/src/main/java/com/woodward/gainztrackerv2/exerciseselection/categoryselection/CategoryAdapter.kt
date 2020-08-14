package com.woodward.gainztrackerv2.exerciseselection.categoryselection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woodward.gainztrackerv2.database.entity.Category
import com.woodward.gainztrackerv2.databinding.ListItemCategoryDetailsBinding

class CategoryAdapter(val clickListener: CategoryAdapterListener) :
    ListAdapter<Category, CategoryAdapter.ViewHolder>(CategoryDiffUtil()) {
    /**
     * Called when the recyclerview needs a new view holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.viewHolderInflation(parent)
    }

    /**
     * Displays data at certain position for the recyclerview UI -> should update contents of widget
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    fun getCategoryPosition(position: Int) : Category {
        return getItem(position)
    }

    class ViewHolder private constructor(val binding: ListItemCategoryDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //Explicit for the code editing for the bind-able objects of the view
        fun bind(
            item: Category,
            clickListener: CategoryAdapterListener
        ) {
            binding.category = item
            binding.clickListener = clickListener
            binding.executePendingBindings() //Optimization technique -> tells data binding to immediately commit all prior data bindings
        }

        //Inflation of the view
        companion object {
            fun viewHolderInflation(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCategoryDetailsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class CategoryAdapterListener(val clickListener: (catID : Int) -> Unit) {
    fun onClick(cat: Category) = clickListener(cat.categoryID)
}

class CategoryDiffUtil : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(
        oldItem: Category,
        newItem: Category
    ): Boolean {
        return oldItem.categoryID == newItem.categoryID
    }

    override fun areContentsTheSame(
        oldItem: Category,
        newItem: Category
    ): Boolean {
        return oldItem == newItem
    }

}