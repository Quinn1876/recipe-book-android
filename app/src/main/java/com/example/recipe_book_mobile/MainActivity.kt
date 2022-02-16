package com.example.recipe_book_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager

    var recipes = MutableLiveData<ArrayList<Recipe>>()

    private fun launchRecipeDetailsActivity(id: String, view: View ) {
        val intent: Intent = Intent(view.context, RecipeDetailsActivity::class.java)
        val state: Bundle = Bundle().apply {
            putString("RECIPE_ID", id)
        }
        intent.putExtras(state)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById<RecyclerView>(R.id.rv_recipe_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val recipeModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)

        recipeModel.getRecipes().observe(this, Observer<ArrayList<Recipe>>{ recipeList ->
            recipes.value = recipeList
            recyclerView.adapter = RecipeListAdapter(this, recipes) { id: String, view: View -> launchRecipeDetailsActivity(id, view)}
        })

        val button: Button = findViewById<Button>(R.id.button_load_recipes)
        button.setOnClickListener{view: View -> recipeModel.loadRecipes()}

    }
}

