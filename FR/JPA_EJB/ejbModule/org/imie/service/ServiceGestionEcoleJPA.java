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
import model.Projet;
import model.Promotion;
// import model.PropositionComp;
// import model.Competence;
import model.Role;


/**
 * Session Bean implementation class ServiceGestionEcoleJPA
 */
@Stateless(mappedName = "ServiceGestionEcole")
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceGestionEcoleJPA implements ServiceGestionEcoleJPARemote,
		ServiceGestionEcoleJPALocal {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Default constructor.
	 */
	public ServiceGestionEcoleJPA() {
		// TODO Auto-generated constructor stub
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Personne> rechercherPersonne(Personne personne) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Personne> query = qb.createQuery(Personne.class);
		Root<Personne> personneRoot = query.from(Personne.class);

		List<Predicate> criteria = new ArrayList<Predicate>();
		if (personne.getNom() != null) {
			criteria.add(qb.like(personneRoot.<String> get("nom"), "*"
					+ personne.getNom() + "*"));
		}
		if (personne.getPrenom() != null) {
			criteria.add(qb.like(personneRoot.<String> get("prenom"), "*"
					+ personne.getPrenom() + "*"));
		}
		if (personne.getId() != null) {
			criteria.add(qb.equal(personneRoot.get("id"), personne.getId()));
		}
		query.where(criteria.toArray(new Predicate[] {}));

		List<Personne> result = entityManager.createQuery(query)
				.getResultList();
		//actualisation des infos liées aux projets dont les personnes sont membres ou chefs de projet
		for(Personne personneEnCours : result){
			//projets en tant que membres
			List<Projet> projetsMembre = personneEnCours.getProjets();
			for(Projet projetMembre : projetsMembre){
				projetMembre.getMembres().size();
			}
			//projets en tant que Chef de projet
			List<Projet> projetsCDP = personneEnCours.getProjetsCDP();
			for(Projet projetCDP : projetsCDP){
				projetCDP.getMembres().size();
			}
		}

		return result;

		// remplace :
		// String query =
		// "select id,nom,prenom,datenaiss,passw,promotionid from personne where nom like ?";
		// statement = connection.prepareStatement(query);
		// // affectation des valeurs de paramètres
		// statement.setString(1, "%".concat(nomInput.concat("%")));
		// // exécution de la requête
		// resultSet = statement.executeQuery();

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Personne insertPersonne(Personne personne) {
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
		personne2.setCgu(personne.getCgu());

		System.out.println("service - personne - insert " + personne.getNom() + " "+ personne.getPrenom());

		entityManager.persist(personne2);
		return personne2;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deletePersonne(Personne personne) {
		personne = entityManager.find(Personne.class, personne.getId());

		// on eleve la dependance FK possede de la personne
		while (personne.getPossedes().size() > 0) {
			int numElt = personne.getPossedes().size() - 1;
			Possede poss = personne.getPossedes().get(numElt);
			personne.getPossedes().remove(numElt);
			// poss.getCompetence().getPossedes().remove(poss.getCompetence().getPossedes().size()
			// - 1);
			entityManager.remove(poss);
		}

		//
		// // on enleve la dependande FK projet de la personne
		// while (personne.getProjets1().size()>0){
		// int numElt = personne.getProjets1().size() - 1;
		// Projet prj = personne.getProjets1().get(numElt);
		// personne.getProjets1().remove(numElt);
		// entityManager.remove(prj);
		// }
		//
		// while (personne.getProjets2().size()>0){
		// int numElt = personne.getProjets2().size() - 1;
		// Projet prj = personne.getProjets2().get(numElt);
		// personne.getProjets2().remove(numElt);
		// entityManager.remove(prj);
		// }

		// //on enleve la dependance travaille de la personne
		// while (personne.get.size()>0){
		// int numElt = personne.getInvitationProjets().size() - 1;
		// InvitationProjet invprj =
		// personne.getInvitationProjets().get(numElt);
		// personne.getProjets2().remove(numElt);
		// entityManager.remove(invprj);
		// }
		//
		// //on enleve la dependance envoyer de la personne
		//

		entityManager.remove(personne);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Personne updatePersonne(Personne personneToUpdate) {
		System.out.println("service - personne - update " + personneToUpdate.getNom() + " "+ personneToUpdate.getPrenom() + " "+personneToUpdate.getIdentConnexion());
		return entityManager.merge(personneToUpdate);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Promotion insertPromotion(Promotion promotion) {
		Promotion promotion2 = new Promotion();
		promotion2.setLibelle(promotion.getLibelle());
		promotion2.setLieu(promotion.getLieu());
		promotion2.setDateDebut(promotion.getDateDebut());
		promotion2.setDateFin(promotion.getDateFin());
		System.out.println("est passé par le insert " + promotion.getLibelle()
				+ " " + promotion.getLieu());

		entityManager.persist(promotion2);
		return promotion;

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deletePromotion(Promotion promotion) {
		promotion = entityManager.find(Promotion.class, promotion.getId());
		entityManager.remove(promotion);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Promotion> rechercherPromotion(Promotion promotion) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Promotion> query = qb.createQuery(Promotion.class);
		Root<Promotion> promotionRoot = query.from(Promotion.class);

		List<Predicate> criteria = new ArrayList<Predicate>();
		if (promotion.getId() != null) {
			criteria.add(qb.equal(promotionRoot.get("id"), promotion.getId()));
		}
		if (promotion.getLibelle() != null) {
			criteria.add(qb.like(promotionRoot.<String> get("libelle"), "*"
					+ promotion.getLibelle() + "*"));
		}

		query.where(criteria.toArray(new Predicate[] {}));
		List<Promotion> result = entityManager.createQuery(query)
				.getResultList();

		return result;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Promotion updatePromotion(Promotion promotionToUpdate) {
		return entityManager.merge(promotionToUpdate);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void duplicatePersonne(Personne personneToDuplicate, Integer nbFois) {
		for (Integer i = 0; i < nbFois; i++) {
			this.insertPersonne(personneToDuplicate);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Personne verifierAuthPersonne(Personne personne)
			throws ServiceException {
		Personne retour = null;
		// NK if ((personne.getNom() == null || personne.getNom().isEmpty())
		if ((personne.getIdentConnexion() == null || personne
				.getIdentConnexion().isEmpty())
				|| (personne.getPassw() == null || personne.getPassw()
						.isEmpty())) {
			throw new ServiceException(
					"la personne à authentifier doit renseigner son login et son passw");

		}

		CriteriaBuilder qb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Personne> query = qb.createQuery(Personne.class);
		Root<Personne> personneRoot = query.from(Personne.class);

		// creation liste de criteres
		List<Predicate> criteria = new ArrayList<Predicate>();
		// NK criteria.add(qb.equal(personneRoot.<String>get("nom"),
		// personne.getNom()));
		criteria.add(qb.equal(personneRoot.<String> get("identConnexion"),
				personne.getIdentConnexion()));
		criteria.add(qb.equal(personneRoot.<String> get("passw"),
				personne.getPassw()));
		query.where(criteria.toArray(new Predicate[] {}));

		// Verification que la personne existe bien dans la base
		List<Personne> resultList = entityManager.createQuery(query)
				.getResultList();
		if (resultList.size() == 1) {
			retour = resultList.get(0);
		} else {
			throw new ServiceException(
					"Utilisateur inconnu ou erreur de mot de passe");
		}

		return retour;
	}

//	@Override
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public List<Possede> rechercherPossede(Possede possede) {
//		System.out.println("rechercherPossede"+possede.getCompetence().getCompIntitule());
//		
//		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
//		System.out.println("query");
//		CriteriaQuery<Possede> query = qb.createQuery(Possede.class);
//		System.out.println("root");
//		Root<Possede> possedeRoot = query.from(Possede.class);
//
//		List<Predicate> criteria = new ArrayList<Predicate>();
//		
//		System.out.println("avant la recherche");
//		if (possede.getPossId() != null) {
//			criteria.add(qb.equal(possedeRoot.get("possId"),
//					possede.getPossId()));
//		}
//		if (possede.getCompNiveau() != null) {
//			criteria.add(qb.equal(possedeRoot.get("compNiveau"),
//					possede.getCompNiveau()));
//		}
//		if (possede.getCompetence() != null) {
//			System.out.println("on devrait passer par la");
//			criteria.add(qb.equal(possedeRoot.get("competence"),
//					possede.getCompetence()));
//		}
//		if (possede.getPersonne() != null) {
//			criteria.add(qb.equal(possedeRoot.get("personne"),
//					possede.getPersonne()));
//		}
//		
//		System.out.println("Fin Test possede");
//		
//		query.where(criteria.toArray(new Predicate[] {}));
//
//		List<Possede> result = entityManager.createQuery(query).getResultList();
//				
//		return result;
//	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Role> rechercherRole(Role role) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Role> query = qb.createQuery(Role.class);
		Root<Role> roleRoot = query.from(Role.class);

		List<Predicate> criteria = new ArrayList<Predicate>();
		if (role.getRoleId() != null) {
			criteria.add(qb.equal(roleRoot.get("roleId"), role.getRoleId()));
		}
		if (role.getRoleIntitule() != null) {
			criteria.add(qb.equal(roleRoot.get("intitule"),
					role.getRoleIntitule()));
		}

		query.where(criteria.toArray(new Predicate[] {}));
		List<Role> result = entityManager.createQuery(query).getResultList();
		return result;
	}

//	@Override
//	public void updatePropComp(PropositionComp propitToUpdate) {
//		// TODO Auto-generated method stub
//		
//	}

//	// ----------------------------------------------------------
//	// JM méthodes compétences à implémenter
//	@Override
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public List<Competence> rechercherCompetence(Competence comp) {
//
//		System.out.println("entree recherche competence"+comp.getCompIntitule());
//		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
//		CriteriaQuery<Competence> query = qb.createQuery(Competence.class);
//		Root<Competence> compRoot = query.from(Competence.class);
//		List<Predicate> criteria = new ArrayList<Predicate>();
//
//		if (comp.getCompId() != null) {
//			criteria.add(qb.equal(compRoot.get("compId"), comp.getCompId()));
//		}
//		if (comp.getCompIntitule() != null) {
//			System.out.println("On devrait passeer par la"+comp.getCompIntitule());
//			criteria.add(qb.equal(compRoot.get("compIntitule"),
//					comp.getCompIntitule()));
//		}
//
//		if (comp.getCompetences() != null) {
//			// criteria.add(qb.equal(compRoot.<String>get("competences"),
//			// comp.getCompetences()));
//			criteria.add(qb.equal(compRoot.<String> get("competences"),
//					comp.getCompetences()));
//		}
//
//		if (comp.getCompetence() != null) {
//			criteria.add(qb.equal(compRoot.<String> get("competence"),
//					comp.getCompetence()));
//		}
//
//		query.where(criteria.toArray(new Predicate[] {}));
//		List<Competence> result = entityManager.createQuery(query)
//				.getResultList();
//		
//		return result;
//	}

	// JM méthodes compétences à implémenter
	// --------------------------------------------------------
	// Delete Competence
//	@Override
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public void deleteCompetence(Competence deletedCompetence) {
//		// TODO Auto-generated method stub
//		System.out.println("delete Competence");
//		if (deletedCompetence.getCompId() != null) {
//			System.out.println("delete Competence id non null");
//			// passage du monde objet au monde relationnel ?? ou juste
//			// completion de l'entité ?
//			deletedCompetence = entityManager.find(Competence.class,
//					deletedCompetence.getCompId());
//
//			// pere de la competence à modifier
//			Competence father = deletedCompetence.getCompetence();
//			// Liste des enfants de la compétence modifiée
//			List<Competence> children = deletedCompetence.getCompetences();
//			// modification des enfants, leur père devient le père de la
//			// compétence
//			// supprimée
//			System.out.println("delete Competence deplacement des enfants");
//			for (Competence comp : children) {
//				comp.setCompetence(father); // modification onde objet
//				updateCompetence(comp); // modification coté persistance
//			}
//			System.out.println("après deplacement des enfants");
//			// Recherche et suppression de toutes les relations avec cette commp
//			// creation d'un modèle vide
//			Possede relation = new Possede();
//			// on initialise le modèle de relation avec la competence à
//			// supprimer
//			relation.setCompetence(deletedCompetence);
//			// on remplie le liste de toutes les relations ayant cette
//			// compétence
//			List<Possede> listRelation = rechercherPossede(relation);
//			// on supprime toutes les relation trouvée dans la classe
//			System.out.println("delete Competence suppression relations");
//			// on eleve la dependance FK possede de la Table Possede
//			for (Possede rel : listRelation) {
//				// la relation rel doit necessairement posseder un Id
//				deletePossede(rel);
//			}
//
//			System.out.println("delete Competence suppression PropositionComp");
//			// meme chose proposition suppression
//			PropositionComp prop = new PropositionComp();
//			prop.setCompetence(deletedCompetence);
//			System.out
//					.println("Competence settée sur le modèle pour permmetre la recherche");
//			List<PropositionComp> listProp = rechercherPropComp(prop);
//			for (PropositionComp propit : listProp) {
//				System.out.println("dans boucle proposition");
//				// la relation rel doit necessairement posseder un Id
//				deletePropComp(propit);
//			}
//
//			System.out.println("delete Competence dans base");
//			// enfin on supprime la competence
//			entityManager.remove(deletedCompetence);
//		}
//	}

	// Delete Competence
	// --------------------------------------------------------
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	private void deletePropComp(PropositionComp propcomp) {
//		// TODO Auto-generated method stub
//		System.out.println("deletePropComp");
//		if (propcomp.getIdNotif() != null) {
//			// la prop à supprimer necessite de posseder un Id
//			propcomp = entityManager.find(PropositionComp.class,
//					propcomp.getIdNotif());
//			entityManager.remove(propcomp);
//		}
//	}

//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public List<PropositionComp> rechercherPropComp(PropositionComp propcomp) {
//		// TODO Auto-generated method stub
//		System.out.println("rechercherPropComp");
//
//		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
//
//		CriteriaQuery<PropositionComp> query = qb
//				.createQuery(PropositionComp.class);
//		Root<PropositionComp> propcompRoot = query.from(PropositionComp.class);
//
//		List<Predicate> criteria = new ArrayList<Predicate>();
//		// List<PropositionComp> listProp = null;
//		if (propcomp.getIdNotif() != null) {
//			criteria.add(qb.equal(propcompRoot.get("id_notif"),
//					propcomp.getIdNotif()));
//		}
//
//		if (propcomp.getCompetence() != null) {
//			criteria.add(qb.equal(propcompRoot.<String> get("competence"),
//					propcomp.getCompetence()));
//		}
//
//		// if (propcomp.getActionanotifier() != null) {
//		// criteria.add(qb.like(propcompRoot.<String> get("???"), "*"
//		// + personne.getNom() + "*"));}}
//		// @JoinColumn(name="id_notif")
//		// private Actionanotifier actionanotifier;
//
//		query.where(criteria.toArray(new Predicate[] {}));
//
//		List<PropositionComp> result = entityManager.createQuery(query)
//				.getResultList();
//
//		return result;
//	}

//	@Override
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public void insertCompetence(Competence newCompetence) {
//		// TODO Auto-generated method stub
//		// doit necessairement posséder un pere
//		System.out.println("est passé par le insert compétence ");
//		// if (newCompetence.getCompetence() != null) {
//		// dans ce cas là, on met la compétence à la racine
//		entityManager.persist(newCompetence);
//		// }
//	}

	
////EJB Invocation failed on component ServiceGestionEcoleJPA for method public abstract void org.imie.service.ServiceGestionEcoleJPALocal.movedCompetence(model.Competence,model.Competence)
//	@Override
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	//public void movedCompetence(Competence movedComp, Competence father) {
//	public void movedCompetence(Competence movedCompetence) {
//		if (movedCompetence.getCompId() != null) {
//
//			// pere de la competence à modifier
//			Competence father = movedCompetence.getCompetence();
//			System.out.println("ComptoMove id non nul: "+movedCompetence.getCompId());
//			System.out.println("Move id parent non nul: "+father.getCompId());
//			
//			// passage du monde objet au monde relationnel ?? ou juste
//			// completion de
//			// l'entité ?
//			//Competence model = movedCompetence;
//			movedCompetence = entityManager.find(Competence.class,
//					movedCompetence.getCompId());
//			// rechercher si le nouveau pere n'appartient pas aux enfants
//			// Liste des enfants de la compétence modifiée
//			List<Competence> children = new ArrayList<Competence>();
//			children.add(movedCompetence);
//			setChildCompetence(children);
//			
//			children =	movedCompetence.getCompetences();
//			// modification des enfants, si besoin
//			Boolean testParent = true;
//			for (Competence comp : children) {
//				System.out.println("Listes d'enfants ");
//				if (comp.getCompId() == father.getCompId()) {
//					testParent = false ;
//				}
//				
//			}
//			
//			// enfin on supprime la competence
//			if (testParent) { 
//				movedCompetence.setCompetence(father);
//				entityManager.merge(movedCompetence);
//				}
//
//		}
//		
//	}

//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	@Override
//	public void updateCompetence(Competence updatedCompetence) {
//		// TODO Auto-generated method stub
//		System.out.println("updateCompetence");
//		Competence compToUpdate = entityManager.find(Competence.class,
//				updatedCompetence.getCompId());
//		
//		
//		// doit necessairement posséder un id
//		if (updatedCompetence.getCompId() != null) {
//			System.out.println("updateCompetence id non nul");
//			// Attention : ERROR: null value in column "comp_valide" violates
//			// not-null constraint faire ? Boolean compValide = true;
//			//updatedCompetence.setCompValide(compValide);
//			compToUpdate.setCompIntitule(updatedCompetence.getCompIntitule());
//			entityManager.merge(compToUpdate);
//		}
//	}

//	// -------------------------------------------------------------------
//	// Ajout Méthodes JM
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public void setChildCompetence(List<Competence> competences) {
//		// Affectation de la liste des enfants comme attributs
//		for (Competence comp : competences) {
//			Competence searchCompChild = new Competence();
//			// initialisation de modèle : compétence vide
//			searchCompChild.setCompetence(comp); // affectation du père
//			List<Competence> resultChild = rechercherCompetence(searchCompChild);
//			// recherche de toute les compétences ayant comp pour père
//			// affectation de cette liste à comp
//			comp.setCompetences(resultChild);
//		}
//	}

//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public void deletePossede(Possede possede) {
//		if (possede.getPossId() != null) {
//			// la relation à supprimer necessite de posszeder un Id
//			possede = entityManager.find(Possede.class, possede.getPossId());
//			entityManager.remove(possede);
//		}
//	}

//	@Override
//	public void movedCompetence(Competence movedComp, Competence father) {
//	// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updatePropComp(PropositionComp propitToUpdate) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updatePossede(Possede relToUpdate) {
//		// TODO Auto-generated method stub
//		
//	}

}
