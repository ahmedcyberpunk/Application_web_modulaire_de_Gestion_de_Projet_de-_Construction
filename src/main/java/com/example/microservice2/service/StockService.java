package com.example.microservice2.service;

import com.example.microservice2.entity.Ressource;
import com.example.microservice2.entity.Stock;
import com.example.microservice2.repository.RessourceRepo;
import com.example.microservice2.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
@org.springframework.transaction.annotation.Transactional
public class StockService implements Stockinter{
    private final StockRepository stockRepository;
    private final RessourceRepo ressourceRepository;

    // 🟢 Mise à jour automatique des stocks après réception d'une commande
    @Transactional
    public void receptionnerCommande(Long ressourceId, int quantiteAjoutee) {
        // Recherche de la ressource dans la table Ressource
        Ressource ressource = ressourceRepository.findById(ressourceId)
                .orElseThrow(() -> new RuntimeException("Ressource non trouvée"));

        // Recherche de l'entité Stock associée à la ressource
        Stock stock = stockRepository.findByRessource(ressource)
                .orElse(new Stock(null, ressource, 0)); // Si pas de stock existant, créer un nouveau stock avec 0 quantité

        // Ajout de la quantité reçue au stock existant
        stock.setQuantiteDisponible(stock.getQuantiteDisponible() + quantiteAjoutee);

        // Sauvegarde du stock mis à jour
        stockRepository.save(stock);
    }


    // 🔴 Déduire la quantité après une commande
    @Transactional
    public void traiterCommande(Long ressourceId, int quantiteDemandee) {
        // Recherche de la ressource dans la table Ressource
        Ressource ressource = ressourceRepository.findById(ressourceId)
                .orElseThrow(() -> new RuntimeException("Ressource non trouvée"));

        // Vérification de la disponibilité de la ressource
        if (ressource.getQuantiteDisponible() < quantiteDemandee) {
            throw new RuntimeException("Stock insuffisant pour cette commande !");
        }

        // Mise à jour de la quantité dans la table Ressource
        int nouvelleQuantite = ressource.getQuantiteDisponible() - quantiteDemandee;
        ressource.setQuantiteDisponible(nouvelleQuantite);

        // Si la ressource est épuisée, on la marque comme inactive
        if (nouvelleQuantite == 0) {
            ressource.setActive(false); // Désactiver la ressource si elle est épuisée
            log.warn("🚨 Ressource {} est maintenant hors stock !", ressource.getNomProduit());
        }

        // Sauvegarder la ressource après modification
        ressourceRepository.save(ressource);
    }

    public Stock findByRessourceId(Long ressourceId) {
        return stockRepository.findByRessourceId(ressourceId);
    }
}
