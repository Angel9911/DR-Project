package interview_tasks_paysafe.object_oriented.softuni.java_oop.task3_encapsulation;

import java.util.List;
import java.util.stream.Collectors;

public class Shelter {
    private List<Animal> animalList;
    private String nameShelter;
    private String locationShelter;

    public Shelter(List<Animal> animalList, String nameShelter, String locationShelter) {
        this.animalList = animalList;
        this.nameShelter(nameShelter);
        this.locationShelter(locationShelter);
    }

    private void locationShelter(String locationShelter) {
        this.checkLength(locationShelter, "LocationError");
        this.locationShelter = locationShelter;
    }

    private void nameShelter(String nameShelter) {
        this.checkLength(nameShelter, "NameShelterError");
        this.nameShelter = nameShelter;
    }

    private void checkLength(String nameField, String messageError){
        if(nameField.length() <= 3 || nameField.length() >=5){
            throw new IllegalArgumentException(messageError+"invalid size for input field");
        }
    }
    public String getAnimalWeightList(){
        return this.animalList.stream()
                .map(animal -> String.format("%.2f,",animal.weight))// if the animal would be in the same package where the Shelter is located, we can access directly protected weight
                .collect(Collectors.joining("|"));
    }

    @Override
    public String toString() {
        return String.format("Name shelter is %s and location is %s", this.nameShelter, this.locationShelter);
    }
}
