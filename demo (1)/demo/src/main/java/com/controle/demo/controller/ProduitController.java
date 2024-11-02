package com.controle.demo.controller;
import com.controle.demo.entities.ProduitAlimentaire;
import com.controle.demo.repository.ProduitAlimentaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
public class ProduitController {

    @Autowired
    private ProduitAlimentaireRepository produitAlimentaireRepository;

    @GetMapping("/catalogue")
    public String afficherCatalogue(Model model) {
        model.addAttribute("produits", produitAlimentaireRepository.findAll());
        return "catalogue";
    }


    @GetMapping("/ajouterProduit")
    public String afficherFormulaireProduit(Model model) {
        model.addAttribute("produitAlimentaire", new ProduitAlimentaire());
        return "ajouter_produit";
    }
    @PostMapping("/supprimerProduit")
    public String supprimerProduit(@RequestParam Long produitId) {
        produitAlimentaireRepository.deleteById(produitId);
        return "redirect:/catalogue";
    }

    @PostMapping("/ajouterProduit")
    public String ajouterProduit(@Valid @ModelAttribute("produitAlimentaire") ProduitAlimentaire produit,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ajouter_produit";
        }
        produitAlimentaireRepository.save(produit);
        return "redirect:/catalogue";
    }
}
