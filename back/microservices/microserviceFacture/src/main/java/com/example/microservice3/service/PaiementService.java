package com.example.microservice3.service;

import com.example.microservice3.entity.Echeance;
import com.example.microservice3.entity.ModePaiement;
import com.example.microservice3.entity.Paiement;
import com.example.microservice3.entity.StatutEcheance;
import com.example.microservice3.repository.EcheanceRepository;
import com.example.microservice3.repository.PaiementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class PaiementService implements IPaimentService {

    private final PaiementRepository paiementRepository;
    private final EcheanceRepository echeanceRepository;

    @Override
    public List<Paiement> getAllModesPaiement() {
        return paiementRepository.findAll();
    }

    @Override
    public Paiement getModePaiement(Integer id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ModePaiement not found with id: " + id));
    }


    @Override
    public Paiement addModePaiement(Paiement paiement, int nbre) {
        // Handle Full Payment
        if (paiement.getModePaiement() == ModePaiement.Full) {
            return paiementRepository.save(paiement);
        }

        // Handle Echeance Payment
        if (paiement.getModePaiement() == ModePaiement.Echeance) {
            // Assuming we have a number of installments to be calculated based on some criteria
            int numberOfInstallments = nbre;

            // Create installments in Echeance table
            BigDecimal amountPerInstallment = paiement.getMontantPaye()
                    .divide(BigDecimal.valueOf(numberOfInstallments), 2, BigDecimal.ROUND_HALF_UP);

            for (int i = 0; i < numberOfInstallments; i++) {
                Echeance echeance = new Echeance();
                echeance.setDateLimite(calculateInstallmentDate(i)); // You can calculate installment date based on your logic
                echeance.setMontantDu(amountPerInstallment);
                echeance.setStatut(StatutEcheance.EN_ATTENTE);  // Assuming Echeance is not paid initially
                echeance.setFacture(paiement.getFacture());  // Associate with facture

                // Save the Echeance
                echeanceRepository.save(echeance);

                // Associate the payment with the first installment
                if (i == 0) {
                    paiement.setEcheances(List.of(echeance)); // Only link to the first installment
                }
            }

            return paiementRepository.save(paiement);  // Save the payment after handling installments
        }

        return paiementRepository.save(paiement);
    }


    @Override
    public void deleteModePaiement(Integer id) {
        if (paiementRepository.existsById(id)) {
            paiementRepository.deleteById(id);
        } else {
            throw new RuntimeException("ModePaiement not found");
        }
    }

    @Override
    public Paiement updatePaiement(Integer id, Paiement paiement) {
        if (!paiementRepository.existsById(id)) {
            throw new RuntimeException("Paiement not found with id: " + id);
        }

        Paiement existingPaiement = paiementRepository.findById(id).orElseThrow();

        existingPaiement.setMontantPaye(paiement.getMontantPaye());
        existingPaiement.setDatePaiement(paiement.getDatePaiement());
        existingPaiement.setModePaiement(paiement.getModePaiement());
        existingPaiement.setFacture(paiement.getFacture());

        return paiementRepository.save(existingPaiement);
    }

    // Method to calculate the number of installments based on business logic
    private int calculateNumberOfInstallments(BigDecimal montantPaye) {
        // Example logic: If the payment amount is more than 1000, break into 3 installments
        if (montantPaye.compareTo(BigDecimal.valueOf(1000)) > 0) {
            return 3;
        }
        return 1;  // Otherwise, just one installment
    }

    // Method to calculate the due date for each installment
    private Date calculateInstallmentDate(int installmentIndex) {
        // Example: Calculate the date based on the index, adding 30 days for each installment
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, installmentIndex * 1);  // Adding 1 month per installment
        return cal.getTime();
    }
}
