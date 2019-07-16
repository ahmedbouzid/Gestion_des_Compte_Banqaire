package org.banque.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.banque.entities.Compte;
import org.banque.entities.Operation;
import org.banque.metier.BanqueMetierImpl;
import org.banque.metier.IbanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller

public class BanqueController {
	@Autowired
	BanqueMetierImpl banqueMetierImpl ;
	
	@RequestMapping("/operations")
	public String index()
	{
		return "comptes";
		}
	@RequestMapping(value="/consulterCompte",method=RequestMethod.GET )
	public String consulterCompte(Model model ,String codeCompte ,
			@RequestParam(name="page",defaultValue="0") int page ,
			@RequestParam(name="size",defaultValue="5") int size) {
		model.addAttribute("codeCompte",codeCompte);
		
		try {
		Compte cp =banqueMetierImpl.consulterCompter(codeCompte);
	
		Page<Operation> pageOperation =banqueMetierImpl.
					listOperation(codeCompte, page,size);
		model.addAttribute("pageOperation",pageOperation.getContent());
		int Pages []=new int [pageOperation.getTotalPages()]	;
		model.addAttribute("pages",Pages);
		
		model.addAttribute("compte",cp);
		}
		catch (Exception e) {
			model.addAttribute("exception", e);
			
		}
		
		return "comptes";	
		}
@RequestMapping(value="/saveOperation" ,method=RequestMethod.POST)
public String save (Model model , String typeOperation , String codeCompte , String montant , String codeCompte2) {
	
		try {
			if (typeOperation.equals("VERS")) {
				banqueMetierImpl.verser(codeCompte, Double.parseDouble(montant));
			}
			else if (typeOperation.equals("RET")) {
				banqueMetierImpl.retirer(codeCompte, Double.parseDouble(montant));
				
			}
			if (typeOperation.equals("VIR")) {
				banqueMetierImpl.virrement(codeCompte, codeCompte2, Double.parseDouble(montant));
		} }
		catch (Exception e) {
			model.addAttribute("error",e);
			return "redirect:/consulterCompte?codeCompte="+codeCompte+"&error="+e.getMessage();
		}

		return "redirect:/consulterCompte?codeCompte="+codeCompte;
}
}
