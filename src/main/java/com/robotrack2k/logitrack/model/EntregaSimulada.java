package com.robotrack2k.logitrack.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class EntregaSimulada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origem;
    private String destino;
    private String prioridade;
    private String status;

    @ManyToOne
    @JoinColumn(name = "robo_responsavel_id")
    private RoboLogistico robo;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RoboLogistico getRobo() {
        return robo;
    }

    public void setRobo(RoboLogistico robo) {
        this.robo = robo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntregaSimulada)) return false;
        EntregaSimulada that = (EntregaSimulada) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
