package com.smartpantry.smart_pantry_web.model;
import jakarta.persistence.*; 

@Entity
public class Ingredient {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)     private Long id;

    private String name;
    private double quantity;
    
    @Enumerated(EnumType.STRING)  
    private Unit unit;

   
    public Ingredient() {}

    public Ingredient(String name, double quantity, Unit unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

   
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
    
    public Unit getUnit() { return unit; }
    public void setUnit(Unit unit) { this.unit = unit; }
}
