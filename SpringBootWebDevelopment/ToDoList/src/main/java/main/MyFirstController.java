package main;

import main.model.Dog;
import main.model.DogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MyFirstController {

    private DogRepository dogRepository;

    public MyFirstController(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    @Value("${someParameter.value}")
    private Integer someParameter;

    @RequestMapping("/")
    public String index(Model model){
        Iterable<Dog> dogIterable = dogRepository.findAll();
        List<Dog> dogs = new ArrayList<>();
        for (Dog dog : dogIterable) {
            dogs.add(dog);
        }
        model.addAttribute("dogs", dogs)
                .addAttribute("dogsCount", dogs.size())
                .addAttribute("someParameter", someParameter);
        return "index";
    }
}
