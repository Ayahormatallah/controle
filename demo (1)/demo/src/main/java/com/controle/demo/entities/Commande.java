package com.controle.demo.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private double montantTotal;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "commande_produit",
            joinColumns = @JoinColumn(name = "commande_id"),
            inverseJoinColumns = @JoinColumn(name = "produit_id"))
    private Set<ProduitAlimentaire> produitsAssocies = new HashSet<>();


      public Commande() {

    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public Set<ProduitAlimentaire> getProduitsAssocies() {
        return produitsAssocies;
    }

    public void setProduitsAssocies(Set<ProduitAlimentaire> produitsAssocies) {
        this.produitsAssocies = produitsAssocies;
    }


}
