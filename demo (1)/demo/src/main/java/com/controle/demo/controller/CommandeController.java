package com.controle.demo.controller;

import com.controle.demo.entities.Commande;
import com.controle.demo.entities.ProduitAlimentaire;
import com.controle.demo.repository.CommandeRepository;
import com.controle.demo.repository.ProduitAlimentaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Controller
public class CommandeController {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ProduitAlimentaireRepository produitAlimentaireRepository;

    @GetMapping("/details_commande")
    public String afficherDetailsCommandes(Model model) {
        List<Commande> commandes = commandeRepository.findAll();
        model.addAttribute("commandes", commandes);
        return "detail_commande";
    }

    @GetMapping("/ajouterCommande")
    public String afficherFormulaireCommande(Model model) {
        Commande commande = new Commande();
        commande.setProduitsAssocies(new HashSet<>());
        model.addAttribute("commande", commande);
        model.addAttribute("produits", produitAlimentaireRepository.findAll());
        return "ajouter_commande";
    }

    @PostMapping("/ajouterCommande")
    public String ajouterCommande(@RequestParam("date") LocalDate date,
                                  @RequestParam List<Long> produitsAssocies,
                                  @RequestParam Map<Long, Integer> quantites) {
        double montantTotal = 0.0;
        Commande commande = new Commande();
        commande.setDate(date);
        commande.setProduitsAssocies(new HashSet<>());
        for (Long produitId : produitsAssocies) {
            ProduitAlimentaire produit = produitAlimentaireRepository.findById(produitId).orElse(null);
            if (produit != null) {
                int quantite = quantites.getOrDefault(produitId, 1);
                montantTotal += produit.getPrix() * quantite;
                if (produit.getStock() >= quantite) {
                    produit.setStock(produit.getStock() - quantite);
                    commande.getProduitsAssocies().add(produit);
                } else {
                 System.out.println("le stock est vide/ Rupture de stock ");
                }
            }
        }

        commande.setMontantTotal(montantTotal);
        commandeRepository.save(commande);

        return "redirect:/details_commande";
    }


}
