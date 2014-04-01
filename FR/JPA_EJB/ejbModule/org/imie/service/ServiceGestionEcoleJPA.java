package org.imie.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.xml.rpc.ServiceException;

import model.Personne;
import model.Promotion;

/**
 * Session Bean implementation class ServiceGestionEcoleJPA
 */
@Stateless(mappedName = "ServiceGestionEcole")
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceGestionEcoleJPA implements ServiceGestionEcoleJPARemote, ServiceGestionEcoleJPALocal {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	
    /**
     * Default constructor. 
     */
    public ServiceGestionEcoleJPA() {
        // TODO Auto-generated constructor stub
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Personne>rechercherPersonne(Personne personne){
    	CriteriaBuilder qb=entityManager.getCriteriaBuilder();
    	
    	CriteriaQuery<Personne> query = qb.createQuery(Personne.class);
    	Root<Personne> personneRoot = query.from(Personne.class);
    	
    	List<Predicate> criteria = new ArrayList<Predicate>();
    	if (personne.getNom() != null){
    		criteria.add(qb.like(personneRoot.<String>get("nom"), "*"+personne.getNom() + "*"));
    	}
    	if (personne.getPrenom() != null){
    		criteria.add(qb.like(personneRoot.<String>get("prenom"), "*"+personne.getPrenom()+"*"));
    	}
    	if (personne.getId() != null){
    		criteria.add(qb.equal(personneRoot.get("id"), personne.getId()));
    	}
    	query.where(criteria.toArray(new Predicate[] {}));
    	
    	List<Personne> result = entityManager.createQuery(query).getResultList();
    	
		return result;
		
//		remplace :
//			String query = "select id,nom,prenom,datenaiss,passw,promotionid from personne where nom like ?";
//		statement = connection.prepareStatement(query);
//		// affectation des valeurs de paramètres
//		statement.setString(1, "%".concat(nomInput.concat("%")));
//		// exécution de la requête
//		resultSet = statement.executeQuery();
    	
    }
    
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Personne insertPersonne(Personne personne){
    	Personne personne2 = new Personne();
    	personne2.setNom(personne.getNom());
    	personne2.setPrenom(personne.getPrenom());
    	personne2.setDateNaiss(personne.getDateNaiss());
    	personne2.setPassw(personne.getPassw());
    	personne2.setPromotion(personne.getPromotion());
    	System.out.println("est passé par le insert "+personne.getNom()+" "+personne.getPrenom());
	    entityManager.persist(personne2);
		return personne2;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deletePersonne(Personne personne){
    	personne = entityManager.find(Personne.class, personne.getId());
    	entityManager.remove(personne);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Personne updatePersonne(Personne personneToUpdate){
    	return entityManager.merge(personneToUpdate);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Promotion insertPromotion(Promotion promotion){
    	entityManager.persist(promotion);
    	return promotion;
		
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deletePromotion(Promotion promotion){
    	entityManager.remove(promotion);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Promotion> rechercherPromotion(Promotion promotion){
    	CriteriaBuilder qb=entityManager.getCriteriaBuilder();
    	
    	CriteriaQuery<Promotion> query = qb.createQuery(Promotion.class);
    	Root<Promotion> promotionRoot = query.from(Promotion.class);
    	
    	List<Predicate> criteria = new ArrayList<Predicate>();
    	if (promotion.getId() != null){
    		criteria.add(qb.equal(promotionRoot.get("id"), promotion.getId()));
    	}
    	if (promotion.getLibelle() != null){
    		criteria.add(qb.like(promotionRoot.<String>get("libelle"), "*"+promotion.getLibelle()+"*"));
    	}
    	
    	query.where(criteria.toArray(new Predicate[] {}));
    	List<Promotion> result = entityManager.createQuery(query).getResultList();
		return result;
    }
   
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Promotion updatePromotion(Promotion promotionToUpdate){
    	return entityManager.merge(promotionToUpdate);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void duplicatePersonne(Personne personneToDuplicate, Integer nbFois){
    	for (Integer i = 0; i < nbFois; i++) {
    		this.insertPersonne(personneToDuplicate);
    	}
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Personne verifierAuthPersonne(Personne personne) throws ServiceException {
		Personne retour = null;
		if ((personne.getNom() == null || personne.getNom().isEmpty())
				|| (personne.getPassw() == null || personne.getPassw()
						.isEmpty())) {
			throw new ServiceException(
					"la personne à authentifier doit renseigner son nom et son passw");

		}

		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
    	
    	CriteriaQuery<Personne> query =qb.createQuery(Personne.class);
    	Root<Personne> personneRoot = query.from(Personne.class);

    	//creation liste de criteres
    	List<Predicate> criteria = new ArrayList<Predicate>();
   		criteria.add(qb.equal(personneRoot.<String>get("nom"), personne.getNom()));
    	criteria.add(qb.equal(personneRoot.<String>get("passw"), personne.getPassw()));
    	query.where(criteria.toArray(new Predicate[]{}));

    	//Verification que la personne existe bien dans la base
    	List<Personne> resultList = entityManager.createQuery(query).getResultList();
    	if(resultList.size() == 1){
    		retour = resultList.get(0);
    	}
    	else{
			throw new ServiceException(
					"Utilisateur inconnu ou erreur de mot de passe");
    	}
    	
    	return retour;
	}

    
    
}

//
//
//alex
//@Override
//public Personne insertPersonne(Personne personne) throws ServiceException {
//	Personne retour;
//	try {
//		retour = personneDAO.insertPersonne(personne);
//		retour = achievePersonne(retour);
//	} catch (PersistanceException e) {
//		throw new ServiceException(e);
//	}
//	return retour;
//}
//
//nicolas
//@Override
//public void deletePersonne(Personne personne) throws ServiceException {
//	try {
//		personneDAO.deletePersonne(personne);
//	} catch (PersistanceException e) {
//		throw new ServiceException(e);
//	}
//
//}
//
//@Override
//public List<Personne> rechercherPersonne(Personne personne)
//		throws ServiceException {
//	List<Personne> retour = null;
//	try {
//		// personneDAO.beginTransaction(this);
//		ApplicationException exception = null;
//		try {
//
//			ApplicationException exception2 = null;
//			retour = personneDAO.rechercherPersonne(personne);
//			// promotionDAO.beginTransaction();
//			try {
//				for (Personne personneFinded : retour) {
//					personneFinded = achievePersonne(personneFinded);
//				}
//			} catch (PersistanceException e1) {
//				exception2 = e1;
//			}
//			// promotionDAO.endTransaction(exception2);
//		} catch (PersistanceException e) {
//			exception = e;
//		}
//		// personneDAO.endTransaction(exception);
//
//	} catch (PersistanceException e) {
//		throw new ServiceException(e);
//	}
//	return retour;
//}

//
//ricardo
//@Override
//public Personne updatePersonne(Personne personneToUpdate)
//		throws ServiceException {
//	Personne retour;
//	try {
//		retour = personneDAO.updatePersonne(personneToUpdate);
//		retour = achievePersonne(retour);
//	} catch (PersistanceException e) {
//		throw new ServiceException(e);
//	}
//	return retour;
//}

//jean-marc
//@Override
//public Promotion insertPromotion(Promotion promotion)
//		throws ServiceException {
//	Promotion retour;
//	try {
//		retour = promotionDAO.insertPromotion(promotion);
//	} catch (PersistanceException e) {
//		throw new ServiceException(e);
//	}
//	return retour;
//}
//
//@Override
//public void deletePromotion(Promotion promotion) throws ServiceException {
//	try {
//
//		personneDAO.beginTransaction(this);
//
//		ApplicationException exception = null;
//		try {
//			Personne searchPersonne = new Personne();
//			searchPersonne.setPromotion(promotion);
//			List<Personne> personnes = personneDAO
//					.rechercherPersonne(searchPersonne);
//			for (Personne personne : personnes) {
//				personneDAO.updatePersonne(personne);
//			}
//		} catch (PersistanceException e) {
//			exception = e;
//		}
//		personneDAO.endTransaction(exception);
//
//		promotionDAO.beginTransaction(this);
//		try {
//			promotionDAO.deletePromotion(promotion);
//		} catch (PersistanceException e) {
//			exception = e;
//		}
//		promotionDAO.endTransaction(exception);
//
//	} catch (PersistanceException e) {
//		throw new ServiceException(e);
//	}
//
//}
//
//@Override
//public List<Promotion> rechercherPromotion(Promotion promotion)
//		throws ServiceException {
//	List<Promotion> retour;
//	try {
//		retour = promotionDAO.rechercherPromotion(promotion);
//	} catch (PersistanceException e) {
//		throw new ServiceException(e);
//	}
//	return retour;
//}
//
//@Override
//public Promotion updatePromotion(Promotion promotionToUpdate)
//		throws ServiceException {
//	Promotion retour;
//	try {
//		retour = promotionDAO.updatePromotion(promotionToUpdate);
//	} catch (PersistanceException e) {
//		throw new ServiceException(e);
//	}
//	return retour;
//}
//
//@Override
//public void duplicatePersonne(Personne personneToDuplicate, Integer nbFois)
//		throws ServiceException {
//	for (Integer i = 0; i < nbFois; i++) {
//		this.insertPersonne(personneToDuplicate);
//	}
//
//}
//
//
//private Personne achievePersonne(Personne personne)
//		throws PersistanceException {
//
//	if (personne.getPromotion() != null) {
//		List<Promotion> promotions = promotionDAO
//				.rechercherPromotion(personne.getPromotion());
//		if (promotions.size() > 0) {
//			personne.setPromotion(promotions.get(0));
//		}
//	}
//
//	return personne;
//}
//
//@Override
//public Personne verifierAuthPersonne(Personne personne)
//		throws ServiceException {
//	Personne retour;
//	if ((personne.getNom() == null || personne.getNom().isEmpty())
//			|| (personne.getPassw() == null || personne.getPassw()
//					.isEmpty())) {
//		throw new ServiceException(
//				"la personne à authentifier doit renseigner son nom et son passw");
//
//	}
//	try {
//
//		retour = personneDAO.verifierAuthPersonne(personne);
//		if (retour!=null){
//			retour = achievePersonne(retour);
//		}
//	} catch (PersistanceException e) {
//		throw new ServiceException(e);
//	}
//	return retour;
//}
//}

