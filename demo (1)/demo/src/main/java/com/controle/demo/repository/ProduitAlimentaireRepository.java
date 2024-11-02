package com.controle.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.controle.demo.entities.ProduitAlimentaire;

@Repository
public interface ProduitAlimentaireRepository extends JpaRepository<ProduitAlimentaire, Long> {}
