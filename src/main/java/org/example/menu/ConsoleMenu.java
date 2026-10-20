package org.example.menu;

import java.util.Scanner;

import org.example.model.AnimalId;
import org.example.model.Bird;
import org.example.model.Cat;
import org.example.model.Dog;
import org.example.model.Animal;
import org.example.shelter.Shelter;

public class ConsoleMenu {
    private final Shelter<Animal> shelter;
    private final Scanner scanner =  new Scanner(System.in);
    public ConsoleMenu(Shelter<Animal> shelter) {
        this.shelter = shelter;
    }

    public void start(){
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addAnimal();
                case "2" -> printAnimals(shelter.getAllAnimals());
                case "3" -> {
                    System.out.print("Species: ");
                    printAnimals(shelter.findBySpecies(scanner.nextLine().trim()));
                }
                case "4" -> printAnimals(shelter.findAvailableAnimals());
                case "5" -> {
                    System.out.print("Animal ID: ");
                    shelter.markAsAdopted(scanner.nextLine().trim());
                }
                case "0" -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void addAnimal() {
        System.out.print("Species (Dog, Cat, Bird): ");
        String species = scanner.nextLine().trim();
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Age: ");
        int age;
        try {
            age = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Age must be a number.");
            return;
        }

        Animal animal = switch (species.toLowerCase()) {
            case "dog" -> new Dog(new AnimalId(), name, age);
            case "cat" -> new Cat(new AnimalId(), name, age);
            case "bird" -> new Bird(new AnimalId(), name, age);
            default -> null;
        };
        if (animal == null) {
            System.out.println("Unknown species.");
        } else {
            shelter.addAnimal(animal);
            System.out.println("Animal added: " + animal);
        }
    }

    private void printAnimals(java.util.List<Animal> animals) {
        if (animals.isEmpty()) {
            System.out.println("No animals found.");
            return;
        }
        animals.forEach(System.out::println);
    }

    private void printMenu(){
        System.out.println("""
                1. Add animal
                2. List all animals
                3. Find animals by species
                4. List available animals
                5. Mark animal as adopted
                0. Exit
                """);
    }
}
