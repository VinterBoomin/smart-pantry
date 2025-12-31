package com.smartpantry.smart_pantry_web;

import com.smartpantry.smart_pantry_web.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PantryController {

    @Autowired
    private IngredientRepository ingredientRepo;
    
    private List<Recipe> cookbook = new ArrayList<>();

    public PantryController() {
        setupCookbook();
    }
    
    private void setupCookbook() {
        Recipe omelette = new Recipe("Omelette", "Fry eggs");
        omelette.addIngredient(new Ingredient("Eggs", 2, Unit.PIECES));
        omelette.addIngredient(new Ingredient("Salt", 1, Unit.TSP)); 
        cookbook.add(omelette);
        
        Recipe pancakes = new Recipe("Pancakes", "Mix everything");
        pancakes.addIngredient(new Ingredient("Flour", 1, Unit.CUP)); 
        pancakes.addIngredient(new Ingredient("Milk", 0.5, Unit.LITERS));
        cookbook.add(pancakes);
    }

    @GetMapping("/pantry")
    public List<Ingredient> getAllIngredients() {
        return ingredientRepo.findAll();
    }

    @PostMapping("/add")
    public String addIngredient(@RequestBody Ingredient newIngredient) {
        ingredientRepo.save(newIngredient);
        return "Added " + newIngredient.getName();
    }
    
    @GetMapping("/recommend")
    public List<Recipe> getRecommendations() {
        List<Ingredient> currentStock = ingredientRepo.findAll();
        
        Pantry tempPantry = new Pantry();
        for (Ingredient dbIng : currentStock) {
            tempPantry.addIngredientToPantry(dbIng);
        }
        
        return tempPantry.getAvailableRecipes(cookbook);
    }

    @PostMapping("/cook")
    public String cookRecipe(@RequestParam String recipeName) {
        Recipe foundRecipe = cookbook.stream()
                .filter(r -> r.getName().equalsIgnoreCase(recipeName))
                .findFirst()
                .orElse(null);

        if (foundRecipe == null) return "Recipe not found!";

        List<Ingredient> stock = ingredientRepo.findAll();
        
        Pantry tempPantry = new Pantry();
        for (Ingredient i : stock) tempPantry.addIngredientToPantry(i);

        if (!tempPantry.canMakeRecipe(foundRecipe)) return "Not enough ingredients!";

        for (Ingredient recipeIng : foundRecipe.getIngredients()) {
            for (Ingredient dbIng : stock) {
                if (dbIng.getName().equalsIgnoreCase(recipeIng.getName())) {
                    double newQuantity = dbIng.getQuantity() - recipeIng.getQuantity();
                    dbIng.setQuantity(newQuantity);
                    
                    ingredientRepo.save(dbIng);
                }
            }
        }
        
        return "Bon Appétit! Cooked " + foundRecipe.getName();
    }
}