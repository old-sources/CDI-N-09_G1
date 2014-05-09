package org.imie.service;

import java.util.List;

import model.Competence;

public interface ServiceGestionCompJPALocal {

    // ajout methodes Competence JM
	public List<Competence> rechercherCompetence(Competence searchCompetences);
	public void deleteCompetence(Competence updatedCompetence);
	public void insertCompetence(Competence updatedCompetence);
	public void updateCompetence(Competence updatedCompetence);
	public void setChildCompetence(List<Competence> foundCompetences);

}
