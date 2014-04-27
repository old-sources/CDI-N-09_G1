package org.imie.service;

import java.util.List;

import javax.ejb.Remote;

import model.Projet;
import model.Travaille;

@Remote
public interface ServiceGestionProjetJPARemote {
	//Signatures liées à la gestion des projets
	public List<Projet> rechercherProjet(Projet prj);
    public List<Travaille> rechercherTravaille(Travaille trv);
    public Projet insertProjet(Projet projet);
    public void deleteProjet(Projet projet);
    public Projet updateProjet(Projet projetToUpdate);
    public Travaille insertTravaille(Travaille travaille);
    public Travaille updateTravaille(Travaille travailleToUpdate);
    public void deleteTravaille(Travaille travaille);
   
}
