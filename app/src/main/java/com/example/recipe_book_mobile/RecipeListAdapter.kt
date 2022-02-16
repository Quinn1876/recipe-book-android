package com.example.recipe_book_mobile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import kotlinx.android.synthetic.main.listview_recipe_row.view.*

/*
    @file: RecipeListAdapter.kt

    @brief: Provides the Logical mapping for a list of recipes to a recipe_list ui element
 */

class RecipeListAdapter(
    private val context: Context,
    private val recipes: LiveData<ArrayList<Recipe>>,
            private val onClickHandler: (id: String, v: View) -> Unit
): RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>() {
    class RecipeListViewHolder(val recipeCard: LinearLayout): RecyclerView.ViewHolder(recipeCard)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        val recipeView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.listview_recipe_row, parent, false) as LinearLayout
        // Set the size of the view's size, margnis, padding, and layout paramaters here
        return RecipeListViewHolder(recipeView)
    }

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        holder.recipeCard.textview_recipe_title.text = recipes.value!![position].name
        holder.recipeCard.textview_recipe_description.text = recipes.value!![position].description
        holder.recipeCard.hidden_recipeId.text = recipes.value!![position].id
        holder.recipeCard.setOnClickListener { view: View -> onClickHandler(recipes.value!![position].id, view) }
        if ( recipes.value!![position].image is String ) {
            HttpRequestClient
                .getInstance(context).imageLoader
                .get(
                    recipes.value!![position].image,
                    ImageLoader.getImageListener(
                        holder.recipeCard.imageview_recipe_preview,
                        R.drawable.food_placeholder, // Loading Image
                        R.drawable.food_placeholder // Error Image
                    )
                )
        }
    }

    override fun getItemCount(): Int = recipes.value!!.size
}