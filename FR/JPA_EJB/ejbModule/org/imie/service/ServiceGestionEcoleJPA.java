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
import model.Possede;
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
//    	Possede possede = new Possede(); 
//    	do{
//    		possede = entityManager.find(Possede.class, personne.getId());
//    		if (possede!=null){
//    			entityManager.remove(possede);
//    		}
//    	}while (possede!=null);
    	entityManager.remove(personne);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Personne updatePersonne(Personne personneToUpdate){
    	return entityManager.merge(personneToUpdate);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Promotion insertPromotion(Promotion promotion){
    	Promotion promotion2 = new Promotion();
    	promotion2.setLibelle(promotion.getLibelle());
    	promotion2.setLieu(promotion.getLieu());
    	promotion2.setDateDebut(promotion.getDateDebut());
    	promotion2.setDateFin(promotion.getDateFin());
    	System.out.println("est passé par le insert "+promotion.getLibelle()+" "+promotion.getLieu());
	   
    	entityManager.persist(promotion2);
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
