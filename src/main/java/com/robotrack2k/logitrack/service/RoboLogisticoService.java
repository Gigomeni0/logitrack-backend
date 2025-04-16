package com.robotrack2k.logitrack.service;

import com.robotrack2k.logitrack.model.RoboLogistico;
import com.robotrack2k.logitrack.repository.RoboLogisticoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoboLogisticoService {

    @Autowired
    private RoboLogisticoRepository repository;

    public List<RoboLogistico> listarRobos() {
        return repository.findAll();
    }

    public Optional<RoboLogistico> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public RoboLogistico salvar(RoboLogistico robo) {
        return repository.save(robo);
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    public boolean existe(Long id) {
        return repository.existsById(id);
    }
}
