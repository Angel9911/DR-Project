package interview_tasks_paysafe.object_oriented.softuni.java_oop.task3_encapsulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Task3 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        double getHeight = Double.parseDouble(reader.readLine());
        double getWeight = Double.parseDouble(reader.readLine());

        Animal animal = new Animal(23,25);

        Dog dog = new Dog();
        dog.setHeight(15);
        dog.setWeight(20);
        List<Animal> animalList = new ArrayList<>();
        animalList.add(animal);
        animalList.add(dog);
        Animal dog2 = new Dog();
        dog2.weight = 200;
        animalList.add(dog2);

        String nameShelter = reader.readLine();
        String locationShelter = reader.readLine();

        try {
            Shelter shelter = new Shelter(animalList, nameShelter, locationShelter);
            System.out.println(shelter);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        Dog dog3 = new Dog();
        dog3.setHeight(getHeight);
        dog3.setWeight(getWeight);
        animalList.add(dog3);
        animalList.sort((animal1, animal2) -> {
            int compare = Double.compare(animal1.getWeight(), animal2.getWeight());
            if(compare != 0){
                return compare;
            }else{
                return Double.compare(animal1.getHeight(), animal2.getHeight());
            }
        });

        //System.out.println(animalList);
        List<Double> getWeights = animalList.stream()
                .map(currentAnimal -> currentAnimal.getWeight())
                .collect(Collectors.toList());
        System.out.println(getWeights);
        System.out.println(dog.makeSound());

    }
}
