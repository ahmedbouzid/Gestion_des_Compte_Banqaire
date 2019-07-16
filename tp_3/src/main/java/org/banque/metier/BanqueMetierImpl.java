package org.banque.metier;

import java.util.Date;

import org.banque.dao.CompteRepository;
import org.banque.dao.OperationRepository;
import org.banque.entities.Compte;
import org.banque.entities.CompteCourant;
import org.banque.entities.Operation;
import org.banque.entities.Retrait;
import org.banque.entities.Verssement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BanqueMetierImpl implements IbanqueMetier {
	@Autowired
 private CompteRepository compteRepository ;
	@Autowired
private OperationRepository operationRepository;	
	@Override
	public Compte consulterCompter(String codeCpte) {
		Compte cp=compteRepository.getOne(codeCpte);
		if (cp==null) throw new RuntimeException("compte introuvable");
		return cp ;
		
	}

	@Override
	public void verser(String codeCpte, double montant) {
		Compte cp = consulterCompter(codeCpte);
		Verssement v = new Verssement(new Date(), montant, cp);
		operationRepository.save(v);
		cp.setSolde(cp.getSolde()+montant);
		compteRepository.save(cp);
		
	}

	@Override
	public void retirer(String codeCpte, double montant) {
		Compte cp = consulterCompter(codeCpte);
		double faciliterCaisse = 0 ;
		if (cp instanceof CompteCourant )
			faciliterCaisse=((CompteCourant) cp).getDecouvert();
		if (cp.getSolde()+faciliterCaisse<montant)
			throw new RuntimeException("solde insuffisant");
		Retrait r =new Retrait(new Date(), montant, cp);
		operationRepository.save(r);
		cp.setSolde(cp.getSolde()-montant);
		compteRepository.save(cp);
	}

	@Override
	public void virrement(String codeCpte1, String codeCpte2, double montant) {
		if (codeCpte1.equals(codeCpte2)) 
			throw new RuntimeException("Impossible d'éffectuer la Virement sur la meme Compte ");
		
retirer(codeCpte1, montant);	
verser(codeCpte2, montant);
	}

	@Override
	public Page<Operation> listOperation(String codeCpte, int page, int size) {
		// TODO Auto-generated method stub
		return operationRepository.listOperation(codeCpte, new PageRequest(page, size));
	}

}
