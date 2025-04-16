package com.robotrack2k.logitrack.controller;

import com.robotrack2k.logitrack.model.RoboLogistico;
import com.robotrack2k.logitrack.repository.RoboLogisticoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/robos")
public class RoboLogisticoController {

    @Autowired
    private RoboLogisticoRepository repository;

    @GetMapping
    public List<RoboLogistico> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<RoboLogistico> buscarPorId(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping
    public RoboLogistico criar(@RequestBody RoboLogistico robo) {
        return repository.save(robo);
    }

    @PutMapping("/{id}")
    public RoboLogistico atualizar(@PathVariable Long id, @RequestBody RoboLogistico novoRobo) {
        return repository.findById(id).map(robo -> {
            robo.setNome(novoRobo.getNome());
            robo.setStatus(novoRobo.getStatus());
            robo.setNivelEnergia(novoRobo.getNivelEnergia());
            robo.setModelo(novoRobo.getModelo());
            return repository.save(robo);
        }).orElseGet(() -> {
            novoRobo.setId(id);
            return repository.save(novoRobo);
        });
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
