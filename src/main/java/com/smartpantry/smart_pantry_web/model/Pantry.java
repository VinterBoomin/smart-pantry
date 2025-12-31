package com.smartpantry.smart_pantry_web.model;

import java.util.ArrayList;
import java.util.List;

public class Pantry {
    
    private List<Ingredient> availableIngredients;

    public Pantry() {
        this.availableIngredients = new ArrayList<>();
    }

    public void addIngredientToPantry(Ingredient newIngredient) {
        this.availableIngredients.add(newIngredient);
        System.out.println("Added to pantry: " + newIngredient.getName());
    }
    
    public List<Ingredient> getIngredients() {
        return availableIngredients;
    }

    public void showStock() {
        System.out.println("\n=== Current Pantry Stock ===");
        if (availableIngredients.isEmpty()) {
            System.out.println("The pantry is empty!");
        } else {
            for (Ingredient ing : availableIngredients) {
                System.out.println(ing.toString());
            }
        }
    }
    
    public boolean canMakeRecipe(Recipe recipe) {
        System.out.println("Checking if we can make: " + recipe.getName() + "...");
        
        for (Ingredient recipeIng : recipe.getIngredients()) {
            boolean found = false;
            
            for (Ingredient pantryIng : availableIngredients) {
            	if (pantryIng.getName().equals(recipeIng.getName())) {
                    found = true;
                    
                    double pantryVal = normalizeAmount(pantryIng.getQuantity(), pantryIng.getUnit());
                    double recipeVal = normalizeAmount(recipeIng.getQuantity(), recipeIng.getUnit());
                    
                    if (pantryVal < recipeVal) {
                        System.out.println(" -> Missing amount for " + recipeIng.getName());
                        return false;
                    }
                    
                    break; 
                }
            }
            
            if (!found) {
                System.out.println(" -> Missing entirely: " + recipeIng.getName());
                return false;
            }
        }
        
        System.out.println(" -> Yes! You have everything needed.");
        return true;
    }
    
    public void cookRecipe(Recipe recipe) {
        if (!canMakeRecipe(recipe)) {
            System.out.println("ERROR: Cannot cook " + recipe.getName() + ". Missing ingredients!");
            return;
        }

        System.out.println("\nCooking " + recipe.getName() + "... Yummy!");

        for (Ingredient recipeIng : recipe.getIngredients()) {
            for (Ingredient pantryIng : availableIngredients) {
                if (pantryIng.getName().equals(recipeIng.getName())) {
                    
                    double newAmount = pantryIng.getQuantity() - recipeIng.getQuantity();
                    
                    pantryIng.setQuantity(newAmount);
                    
                    System.out.println("Updated " + pantryIng.getName() + ": " + newAmount + " " + pantryIng.getUnit() + " left.");
                }
            }
        }
        System.out.println("Bon Appétit!\n");
    }
    
    public void suggestRecipes(List<Recipe> cookbook) {
        System.out.println("\n*** Chef's Recommendations ***");
        System.out.println("Based on your current stock, you can make:");
        
        boolean foundAny = false;
        
        for (Recipe recipe : cookbook) {
            if (canMakeRecipe(recipe)) {
                System.out.println(" ✅ " + recipe.getName());
                foundAny = true;
            }
        }
        
        if (!foundAny) {
            System.out.println(" ❌ Nothing! Go to the supermarket.");
        }
        System.out.println("******************************\n");
    }

    private double normalizeAmount(double amount, Unit unit) {
        switch (unit) {
            case KILOGRAMS: return amount * 1000;
            case GRAMS:     return amount;
            case LITERS:    return amount * 1000;
            case MILLILITERS: return amount;
            case TSP:       return amount * 5;
            case TBS:       return amount * 15;
            case CUP:       return amount * 240;
            case PIECES:    return amount;
            default:        return amount;
        }
    }
    
    public void printShoppingList(Recipe recipe) {
        System.out.println("\n📝 Shopping List for: " + recipe.getName());
        boolean missingAny = false;

        for (Ingredient recipeIng : recipe.getIngredients()) {
            boolean foundInPantry = false;

            for (Ingredient pantryIng : availableIngredients) {
                if (pantryIng.getName().equals(recipeIng.getName())) {
                    foundInPantry = true;
                    
                    double pantryVal = normalizeAmount(pantryIng.getQuantity(), pantryIng.getUnit());
                    double recipeVal = normalizeAmount(recipeIng.getQuantity(), recipeIng.getUnit());

                    if (pantryVal < recipeVal) {
                        missingAny = true;
                        System.out.println(" - " + recipeIng.getName() + ": You have " + 
                                           pantryIng.getQuantity() + " " + pantryIng.getUnit() + 
                                           ", but need " + recipeIng.getQuantity() + " " + recipeIng.getUnit());
                    }
                    break;
                }
            }

            if (!foundInPantry) {
                missingAny = true;
                System.out.println(" - Buy: " + recipeIng.getQuantity() + " " + recipeIng.getUnit() + " " + recipeIng.getName());
            }
        }

        if (!missingAny) {
            System.out.println("Nothing to buy! You are ready to cook. 👨‍🍳");
        }
        System.out.println("-----------------------------------");
    }
    
    public List<Recipe> getAvailableRecipes(List<Recipe> cookbook) {
        List<Recipe> available = new ArrayList<>();
        
        for (Recipe r : cookbook) {
            if (canMakeRecipe(r)) {
                available.add(r);
            }
        }
        return available;
    }
}