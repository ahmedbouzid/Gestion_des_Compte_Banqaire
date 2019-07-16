package org.banque;

import java.util.Date;

import org.banque.dao.ClientRepository;
import org.banque.dao.CompteRepository;
import org.banque.dao.OperationRepository;
import org.banque.entities.Client;
import org.banque.entities.Compte;
import org.banque.entities.CompteCourant;
import org.banque.entities.CompteEpagne;
import org.banque.entities.Operation;
import org.banque.entities.Retrait;
import org.banque.entities.Verssement;
import org.banque.metier.BanqueMetierImpl;
import org.banque.metier.IbanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class Tp3Application  implements CommandLineRunner{
 @Autowired
 private OperationRepository operationRepository;
 @Autowired
 private CompteRepository compteRepository ;
 @Autowired
 private ClientRepository clientRepository ;
 @Autowired
 private IbanqueMetier ibanqueMetier ;
 
	public static void main(String[] args)  {
		SpringApplication.run(Tp3Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Client c1 = clientRepository.save(new Client( "Mohamed","med@gmail.com"));
		Client c2 = clientRepository.save(new  Client("Ahmed" ,"Ahmed@gmail.com"));
		Compte cp1=compteRepository.save(new CompteCourant("cp1",new Date(),800000,c1,6000));
		Compte cp2=compteRepository.save(new CompteEpagne("cp2",new Date(),50000,c2,6.6));
		operationRepository.save(new Verssement(new Date(),9000,cp1));
		operationRepository.save(new Verssement(new Date(),6000,cp1));
		operationRepository.save(new Retrait(new Date(),9000,cp1));
		
		operationRepository.save(new Verssement(new Date(),2300,cp2));
		operationRepository.save(new Verssement(new Date(),3000,cp2));
		operationRepository.save(new Retrait(new Date(),4000,cp2));
		
ibanqueMetier.verser("cp1", 111111111);
	}

}
