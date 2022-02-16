package com.example.recipe_book_mobile

import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class Recipe constructor(
    val id: String,
    val name: String,
    val ownerId: String,
    val ownerName: String,
    val description: String,
    val ingredients: ArrayList<String>,
    val directions: ArrayList<String>,
    val createdAt: String,
    val image: String?
){

    companion object {
        /*
            @ brief: Returns a new Recipe Generated from the JSON passed in
            @throws: logs and then throws any exceptions from the Json parser, if not caught, will crash the app
         */
        fun fromJson(json: JSONObject): Recipe {
            val tempIngredients: ArrayList<String>
            val tempDirections: ArrayList<String>
            try {
                val jsonIngredients: JSONArray = json.getJSONArray("ingredients")
                tempIngredients = arrayListOf()
                for (index in 0 until jsonIngredients.length()) {
                    tempIngredients.add(jsonIngredients.getString(index))
                }
                val jsonDirections: JSONArray = json.getJSONArray("directions")
                tempDirections= arrayListOf()
                for (index in 0 until jsonDirections.length()) {
                    tempDirections.add(jsonDirections.getString(index))
                }
                return Recipe(
                    json.getString("recipeId"),
                    json.getString("name"),
                    json.getString("ownerId"),
                    json.getString("ownerName"),
                    json.getString("description"),
                    tempIngredients,
                    tempDirections,
                    json.getString("createdAt"),
                    json.getString("image")
                )
            } catch (e: Exception ) {
                println(e)
                throw e
            }
        }
    }
}
