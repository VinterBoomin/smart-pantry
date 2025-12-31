package com.smartpantry.smart_pantry_web.model;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String name;
    private List<Ingredient> ingredients;
    private String instructions;

    public Recipe(String name, String instructions) {
        this.name = name;
        this.instructions = instructions;
        this.ingredients = new ArrayList<>();
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public String getName() { return name; }
    public List<Ingredient> getIngredients() { return ingredients; }
    public String getInstructions() { return instructions; }
    
    @Override
    public String toString() {
    	String output = "Recipe: " + name + "\n";
        output += "Instructions: " + instructions + "\n";
        output += "Ingredients List:\n";
        
       
        for (Ingredient ing : ingredients) {
            output += " - " + ing.toString() + "\n";
        }
        return output;
    }
}

