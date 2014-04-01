package org.imie.service;

import java.util.List;

import javax.ejb.Local;
import javax.xml.rpc.ServiceException;

import model.Personne;
import model.Promotion;

// Commentaire JM

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
}
