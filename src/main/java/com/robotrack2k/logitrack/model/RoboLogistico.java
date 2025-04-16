package com.robotrack2k.logitrack.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class RoboLogistico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String status;
    private Double nivelEnergia;
    private String modelo;

    @OneToMany(mappedBy = "robo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntregaSimulada> entregas;

    @OneToMany(mappedBy = "robo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventoSensorial> eventos;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getNivelEnergia() {
        return nivelEnergia;
    }

    public void setNivelEnergia(Double nivelEnergia) {
        this.nivelEnergia = nivelEnergia;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public List<EntregaSimulada> getEntregas() {
        return entregas;
    }

    public void setEntregas(List<EntregaSimulada> entregas) {
        this.entregas = entregas;
    }

    public List<EventoSensorial> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventoSensorial> eventos) {
        this.eventos = eventos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoboLogistico)) return false;
        RoboLogistico that = (RoboLogistico) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
