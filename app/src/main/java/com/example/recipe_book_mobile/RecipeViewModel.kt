package com.example.recipe_book_mobile

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray

class RecipeViewModel(application: Application): AndroidViewModel(application) {
    val recipes: MutableLiveData<ArrayList<Recipe>> by lazy {
        MutableLiveData<ArrayList<Recipe>>().also {
            loadRecipes()
        }
    }

    fun loadRecipes() {
        val url = "https://d764047784cd.ngrok.io/recipes/" // THIS Changes every time you start ngrok
        val data: ArrayList<Recipe> = ArrayList()

        val recipesRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONArray> { response ->
                for (index in 0 until response.length()) {
                    data.add(Recipe.fromJson(response.getJSONObject(index)))
                }

                recipes.value = data
            },
            Response.ErrorListener { error ->
                // TODO handle Error
                println(error.toString())
            }
        )
        HttpRequestClient.getInstance(getApplication()).addToRequestQueue(recipesRequest)
    }

    fun getRecipes(): LiveData<ArrayList<Recipe>> {
        return recipes
    }
}