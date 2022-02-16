package com.example.recipe_book_mobile

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader

import kotlinx.android.synthetic.main.activity_recipe_details.*
import kotlinx.android.synthetic.main.listview_recipe_row.view.*

class RecipeDetailsActivity : AppCompatActivity() {

    private var recipeId: String? = null

    var recipe = MutableLiveData<Recipe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)
        setSupportActionBar(toolbar)

        recipeId = intent.getStringExtra("RECIPE_ID") ?: "0"

        val recipeModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)

        val recipeImage = findViewById<ImageView>(R.id.imageview_recipe_details_preview)
        val recipeTitle = findViewById<TextView>(R.id.recipe_details_header)
        val recipeDescription = findViewById<TextView>(R.id.text_view_recipe_details_description)

        recipeModel.getRecipes().observe(this, Observer<ArrayList<Recipe>>{ recipeList ->
            recipe.value = recipeList.find { recipe -> recipe.id == recipeId }
            recipeTitle.text = recipe.value?.name ?: "Loading"
            recipeDescription.text = recipe.value?.description ?: "Loading"

            val fragManager = supportFragmentManager
            var fragTransaction = fragManager.beginTransaction()
            fragTransaction.add(R.id.layout_recipe_details, DropListFragment.newInstance("Ingredients", recipe.value?.ingredients ?: arrayListOf()))
            fragTransaction.commit()
            fragTransaction = fragManager.beginTransaction()
            fragTransaction.add(R.id.layout_recipe_details, DropListFragment.newInstance("Directions", recipe.value?.directions?: arrayListOf(), true))
            fragTransaction.commit()

            if ( recipe.value!!.image is String ) {
                HttpRequestClient
                    .getInstance(this).imageLoader
                    .get(
                        recipe.value!!.image,
                        ImageLoader.getImageListener(
                            recipeImage,
                            R.drawable.food_placeholder, // Loading Image
                            R.drawable.food_placeholder // Error Image
                        )
                    )
            }
        })

    }

}
