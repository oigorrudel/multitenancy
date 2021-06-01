package br.xksoberbado.app.controller;

import br.xksoberbado.app.model.Person;
import br.xksoberbado.app.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("people")
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Person person) {
        return ResponseEntity.ok(repository.save(person));
    }

}
