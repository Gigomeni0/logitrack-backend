package com.robotrack2k.logitrack.repository;

import com.robotrack2k.logitrack.model.RoboLogistico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoboLogisticoRepository extends JpaRepository<RoboLogistico, Long> {
}

