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

import model.Competence;
import model.Personne;
import model.Possede;
import model.Projet;
import model.Promotion;
import model.Role;
import model.Travaille;

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
    	personne2.setEmail(personne.getEmail());
    	personne2.setInfos(personne.getInfos());
    	personne2.setDisponibilite(personne.getDisponibilite());
    	personne2.setRole(personne.getRole());
    	personne2.setIdentConnexion(personne.getIdentConnexion());
    	
    	
    	System.out.println("est passé par le insert "+personne.getNom()+" "+personne.getPrenom());
    	System.out.println("est passé par le insert lemail est "+personne.getEmail());
    	
	    entityManager.persist(personne2);
		return personne2;
    }
     
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deletePersonne(Personne personne){
    	personne = entityManager.find(Personne.class, personne.getId());

    	// on eleve la dependance FK possede de la personne 
    	while (personne.getPossedes().size()>0){
    		int numElt = personne.getPossedes().size() - 1;
    		Possede poss = personne.getPossedes().get(numElt);
    		personne.getPossedes().remove(numElt);
    		//poss.getCompetence().getPossedes().remove(poss.getCompetence().getPossedes().size() - 1);
    		entityManager.remove(poss);
    	}
    	

//    	
//    	// on enleve la dependande FK projet de la personne
//    	while (personne.getProjets1().size()>0){
//    		int numElt = personne.getProjets1().size() - 1;
//    		Projet prj = personne.getProjets1().get(numElt);
//    		personne.getProjets1().remove(numElt);
//    		entityManager.remove(prj);
//    	}
//    	
//    	while (personne.getProjets2().size()>0){
//    		int numElt = personne.getProjets2().size() - 1;
//    		Projet prj = personne.getProjets2().get(numElt);
//    		personne.getProjets2().remove(numElt);
//    		entityManager.remove(prj);
//    	}
    	
    	
    	
//    	//on enleve la dependance travaille de la personne
//    	while (personne.get.size()>0){
//    		int numElt = personne.getInvitationProjets().size() - 1;
//    		InvitationProjet invprj = personne.getInvitationProjets().get(numElt);
//    		personne.getProjets2().remove(numElt);
//    		entityManager.remove(invprj);
//    	}
//    	
//    	//on enleve la dependance envoyer de la personne
//    	
    	
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
    	System.out.println("rentré dans promotion 1");
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
    	System.out.println("rentré dans promotion 2");
    	List<Promotion> result = entityManager.createQuery(query).getResultList();
    	System.out.println("rentré dans promotion 3");
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
//	NK	if ((personne.getNom() == null || personne.getNom().isEmpty())
		if ((personne.getIdentConnexion() == null || personne.getIdentConnexion().isEmpty())
				|| (personne.getPassw() == null || personne.getPassw()
						.isEmpty())) {
			throw new ServiceException(
					"la personne à authentifier doit renseigner son login et son passw");

		}

		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
    	
    	CriteriaQuery<Personne> query =qb.createQuery(Personne.class);
    	Root<Personne> personneRoot = query.from(Personne.class);

    	//creation liste de criteres
    	List<Predicate> criteria = new ArrayList<Predicate>();
//   NK		criteria.add(qb.equal(personneRoot.<String>get("nom"), personne.getNom()));
   		criteria.add(qb.equal(personneRoot.<String>get("identConnexion"), personne.getIdentConnexion()));
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

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Possede> rechercherPossede(Possede possede){
    	CriteriaBuilder qb=entityManager.getCriteriaBuilder();
    	
    	CriteriaQuery<Possede> query = qb.createQuery(Possede.class);
    	Root<Possede> possedeRoot = query.from(Possede.class);
    	
    	List<Predicate> criteria = new ArrayList<Predicate>();
    	if (possede.getPossId() != null){
    		criteria.add(qb.equal(possedeRoot.get("possId"), possede.getPossId()));
    	}
    	if (possede.getCompNiveau() != null){
    		criteria.add(qb.equal(possedeRoot.get("compNiveau"), possede.getCompNiveau()));
    	}
    	if (possede.getCompetence() != null){
    		criteria.add(qb.equal(possedeRoot.get("competence"), possede.getCompetence()));
    	}
    	if (possede.getPersonne() != null){
    		criteria.add(qb.equal(possedeRoot.get("personne"), possede.getPersonne()));
    	}
    	
    	query.where(criteria.toArray(new Predicate[] {}));
    	List<Possede> result = entityManager.createQuery(query).getResultList();
		return result;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Projet> rechercherProjet(Projet prj){
    	CriteriaBuilder qb=entityManager.getCriteriaBuilder();
    	
    	CriteriaQuery<Projet> query = qb.createQuery(Projet.class);
		Root<Projet> prjRoot = query.from(Projet.class);
    	
    	List<Predicate> criteria = new ArrayList<Predicate>();
    	if (prj.getPersonne() != null){
    		criteria.add(qb.equal(prjRoot.get("personne"), prj.getPersonne()));
    	}
    	if (prj.getProjId() != null){
    		criteria.add(qb.equal(prjRoot.get("projId"), prj.getProjId()));
    	}
    	if (prj.getProjNom() != null){
    		criteria.add(qb.equal(prjRoot.<String>get("ProjNom"), prj.getProjNom()));
    	}
    	
    	query.where(criteria.toArray(new Predicate[] {}));
    	List<Projet> result = entityManager.createQuery(query).getResultList();
		return result;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Travaille> rechercherTravaille(Travaille trv){
    	CriteriaBuilder qb=entityManager.getCriteriaBuilder();
    	
    	CriteriaQuery<Travaille> query = qb.createQuery(Travaille.class);
		Root<Travaille> trvRoot = query.from(Travaille.class);
    	
    	List<Predicate> criteria = new ArrayList<Predicate>();
    	if (trv.getPersonne() != null){
    		criteria.add(qb.equal(trvRoot.get("personne"), trv.getPersonne()));
    	}
    	if (trv.getProjet() != null){
    		criteria.add(qb.equal(trvRoot.get("projId"), trv.getProjet()));
    	}
    	if (trv.getTrvId() != null){
    		criteria.add(qb.equal(trvRoot.<String>get("trvId"), trv.getTrvId()));
    	}
    	
    	query.where(criteria.toArray(new Predicate[] {}));
    	List<Travaille> result = entityManager.createQuery(query).getResultList();
		return result;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Role> rechercherRole(Role role){
    	CriteriaBuilder qb=entityManager.getCriteriaBuilder();
    	
    	CriteriaQuery<Role> query = qb.createQuery(Role.class);
		Root<Role> roleRoot = query.from(Role.class);
    	
    	List<Predicate> criteria = new ArrayList<Predicate>();
    	if (role.getRoleId() != null){
    		criteria.add(qb.equal(roleRoot.get("roleId"), role.getRoleId()));
    	}
    	if (role.getRoleIntitule() != null){
    		criteria.add(qb.equal(roleRoot.get("intitule"), role.getRoleIntitule()));
    	}
    	    	
    	query.where(criteria.toArray(new Predicate[] {}));
    	List<Role> result = entityManager.createQuery(query).getResultList();
		return result;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Projet insertProjet(Projet projet){
    	Projet projet2 = new Projet();
    	projet2.setProjNom(projet.getProjNom());
    	projet2.setProjDatedebut(projet.getProjDatedebut());
    	projet2.setProjDatedefin(projet.getProjDatedefin());
    	projet2.setProjDescription(projet.getProjDescription());
    	projet2.setProjWikiCdp(projet.getProjWikiCdp());
    	projet2.setProjWikiMembre(projet.getProjWikiMembre());
    	projet2.setProjAvancement(projet.getProjAvancement());
    	projet2.setPersonne(projet.getPersonne());
	    entityManager.persist(projet2);
		return projet2;
    }
     
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteProjet(Projet projet){
    	projet = entityManager.find(Projet.class, projet.getProjId());
    	entityManager.remove(projet);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Projet updateProjet(Projet projetToUpdate){
    	return entityManager.merge(projetToUpdate);
    }

	@Override
	public List<Competence> rechercherCompetence(Competence searchCompetences) {
		// TODO Auto-generated method stub
		return null;
	}

	//----------------------------------------------------------
	// JM méthodes compétences à implémenter
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
    
    
}
