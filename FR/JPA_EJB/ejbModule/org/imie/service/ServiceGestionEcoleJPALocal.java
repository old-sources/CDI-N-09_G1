package org.imie.service;

import java.util.List;

import javax.ejb.Local;
import javax.xml.rpc.ServiceException;

import model.Competence;
import model.Personne;
import model.Possede;
import model.Promotion;
import model.PropositionComp;
import model.Role;

// Commentaire JM
// besoin d'annotations documentations et commentaires explicatifs

@Local
public interface ServiceGestionEcoleJPALocal {
	public List<Personne> rechercherPersonne(Personne personne);

    public Personne insertPersonne(Personne personne);
    
    public void deletePersonne(Personne personne);
    
    public Personne updatePersonne(Personne personneToUpdate);
    
    public Promotion insertPromotion(Promotion promotion);
    
    public void deletePromotion(Promotion promotion);
    
    public List<Promotion> rechercherPromotion(Promotion promotion);
   
    public Promotion updatePromotion(Promotion promotionToUpdate);
    
    public void duplicatePersonne(Personne personneToDuplicate, Integer nbFois);
    
    public Personne verifierAuthPersonne(Personne personne) throws ServiceException;
    
    public List<Possede> rechercherPossede(Possede possede);
    public List<Role> rechercherRole(Role role);

    // ajout methodes Competence JM
	public List<Competence> rechercherCompetence(Competence searchCompetences);
	public void deleteCompetence(Competence updatedCompetence);
	//public void createCompetence(Competence modelCompetence); = insert
	public void insertCompetence(Competence updatedCompetence);
	public void updateCompetence(Competence updatedCompetence);
	public void setChildCompetence(List<Competence> foundCompetences);

	public List<PropositionComp> rechercherPropComp(PropositionComp prop);

	//public void movedCompetence(Competence movedComp);

	void movedCompetence(Competence movedComp, Competence father);
	void movedCompetence(Competence movedComp);
	
	public void updatePropComp(PropositionComp propitToUpdate);
	public void updatePossede(Possede relToUpdate);


}
