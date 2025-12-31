# Smart Pantry Management System

**Created by [Itay Vinter](https://www.linkedin.com/in/itay-vinter/)**
> A full-stack inventory management application that helps users minimize food waste by tracking ingredients and suggesting recipes based on available stock.
<img width="1802" height="860" alt="dashboard" src="https://github.com/user-attachments/assets/ba9c6e47-92fa-425a-809b-6d6a2f98ab3d" />

## Overview

The **Smart Pantry** is a web application designed to solve the common problem of "What can I cook with what I have?". 
It allows users to manage their kitchen inventory in real-time and uses a smart algorithm to match available ingredients with recipes. The system handles unit conversions and updates the inventory automatically after cooking.

## Key Features

* **Inventory Management:** Add, view, and track ingredients with various units (kg, liters, cups, etc).
* **Smart Recipe Engine:** Analyzes current stock to suggest possible recipes.
* **Unit Normalization:** Advanced logic to compare different units (e.g., Liters vs. Cups).
* **"Cook & Eat" Logic:** Automatically deducts used ingredients from the inventory when a recipe is cooked.
* **Data Persistence:** Uses H2 Database to save inventory state between sessions.
* **Responsive UI:** Built with Bootstrap 5 for a clean experience on mobile and desktop.

## Tech Stack

* **Backend:** Java 17, Spring Boot 3.4
* **Database:** H2 Database (In-Memory/File-based), Spring Data JPA (Hibernate)
* **Frontend:** HTML5, JavaScript (ES6), Bootstrap 5
* **API:** RESTful Architecture
* **Build Tool:** Maven

## How to Run

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/ItayV/smart-pantry.git](https://github.com/ItayV/smart-pantry.git)
    ```
2.  **Open in IDE:** Import the project as a Maven Project into Eclipse or IntelliJ IDEA.
3.  **Run:** Locate `SmartPantryWebApplication.java` and run it as a Java Application.
4.  **Access:** Open your browser and navigate to:
    `http://localhost:8080`

## Code Highlight

The system features a custom logic to handle unit conversions dynamically:

```java
// Example of the normalization logic used in the project
private double normalizeAmount(double amount, Unit unit) {
    switch (unit) {
        case KILOGRAMS: return amount * 1000; // Convert to grams
        case LITERS:    return amount * 1000; // Convert to ml
        case CUP:       return amount * 240;  // Standard cup size
        // ... handles all unit types
        default:        return amount;
    }
}
```
