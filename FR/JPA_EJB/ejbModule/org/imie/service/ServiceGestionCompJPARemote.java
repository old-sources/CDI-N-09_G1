package org.imie.service;

//import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
//import javax.ejb.Stateless;
//import javax.ejb.TransactionAttribute;
//import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//import javax.xml.rpc.ServiceException;

import model.Competence;
import model.Possede;
//import model.Personne;
//import model.Possede;
//import model.Possede;
import model.PropositionComp;


/**
 * Session Bean implementation class ServiceGestionEcoleJPA
 */
// @Stateless(mappedName = "ServiceGestionComp")
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceGestionCompJPARemote implements	ServiceGestionCompJPALocal {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Default constructor.
	 */
	public ServiceGestionCompJPARemote() {
	}

	@Override
	public List<Competence> rechercherCompetence(Competence searchCompetences) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCompetence(Competence updatedCompetence) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertCompetence(Competence updatedCompetence) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCompetence(Competence updatedCompetence) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setChildCompetence(List<Competence> foundCompetences) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PropositionComp> rechercherPropComp(PropositionComp prop) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public void movedCompetence(Competence movedComp, Competence father) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public void movedCompetence(Competence movedComp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Possede> rechercherPossede(Possede possede) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public void updatePropComp(PropositionComp propitToUpdate) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void updatePossede(Possede relToUpdate) {
//		// TODO Auto-generated method stub
//		
//	}

}
