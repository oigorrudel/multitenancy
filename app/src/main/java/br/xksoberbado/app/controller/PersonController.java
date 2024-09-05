package br.xksoberbado.app.controller;

import br.xksoberbado.app.model.Person;
import br.xksoberbado.app.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonRepository repository;

    @GetMapping
    public List<Person> get() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person post(@RequestBody final Person person) {
        return repository.save(person);
    }
}
