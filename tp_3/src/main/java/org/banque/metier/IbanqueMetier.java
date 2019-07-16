package org.banque.metier;

import java.util.List;

import org.banque.entities.Compte;
import org.banque.entities.Operation;
import org.springframework.data.domain.Page;

public interface IbanqueMetier {
	public Compte consulterCompter(String codeCpte);
	public void verser (String codeCpte ,  double montant  );
	public void retirer (String codeCpte , double montant);
	public  void virrement (String codeCpte1 , String codeCpte2 ,double montant);
	public Page<Operation> listOperation (String codeCpte , int page , int size);

}
