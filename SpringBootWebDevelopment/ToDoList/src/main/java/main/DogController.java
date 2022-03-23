package main;

import main.model.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.Dog;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class DogController {

    @Autowired
    private DogRepository dogRepository;

    @GetMapping("/dogs/")
    public List<Dog> list() {
        Iterable<Dog> dogIterable = dogRepository.findAll();
        List<Dog> dogs = new ArrayList<>();
        dogIterable.forEach(dog -> dogs.add(dog));
        return dogs;
    }

    @PostMapping("/dogs/")
    public int add(Dog dog) {
        Dog newDog = dogRepository.save(dog);
        return newDog.getId();
    }

    @PutMapping("/dogs/{id}")
    public ResponseEntity update(@PathVariable int id, Dog dog) {
        Optional<Dog> dogOptional = dogRepository.findById(id);
        if (!dogOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Dog newDog = dogOptional.get();
        newDog.setBreed(dog.getBreed());
        newDog.setNickname(dog.getNickname());
        dogRepository.save(newDog);
        return new ResponseEntity(add(newDog), HttpStatus.OK);
    }

    @GetMapping("/dogs/{id}")
    public ResponseEntity get(@PathVariable int id) {
        Optional<Dog> dogOptional = dogRepository.findById(id);
        if (!dogOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(dogOptional.get(), HttpStatus.OK);
    }

    @DeleteMapping("/dogs/{id}")
    public ResponseEntity remove(@PathVariable int id) {
        Optional<Dog> dogOptional = dogRepository.findById(id);
        if (!dogOptional.isPresent()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        dogRepository.deleteById(id);
        return new ResponseEntity(list(), HttpStatus.OK);
    }

    @DeleteMapping("/dogs/")
    public List<Dog> clear() {
        dogRepository.deleteAll();
        return list();
    }
}
