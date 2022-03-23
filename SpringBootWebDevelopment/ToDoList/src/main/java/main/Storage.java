package main;

import main.model.Dog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Storage {
    private static Map<Integer, Dog> dogs = new ConcurrentHashMap<>();
    private static int currenId = 1;

    public static List<Dog> getAllDogs(){
        ArrayList<Dog> dogsList = new ArrayList<>();
        dogsList.addAll(dogs.values());
        return dogsList;
    }

    public static int addDog(Dog dog){
        int id = currenId++;
        dog.setId(id);
        dogs.put(id,dog);
        return id;
    }

    public static Dog getDog(int dogId){
        if(dogs.containsKey(dogId)){
            return dogs.get(dogId);
        }
        return null;
    }

    public static String removeDog(int dogId){
        if(dogs.containsKey(dogId)){
            String message = "Объект: "
                    + dogs.get(dogId).getNickname()
                    + " Успешно удален!";
            dogs.remove(dogId);
            return message;
        }
        return null;
    }

    public static String clearDogs(){
        dogs.clear();
        return "Коллекция успешно очищена!";
    }
}
