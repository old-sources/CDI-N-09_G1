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
//import javax.xml.rpc.ServiceException;

import model.Competence;
//import model.Personne;
import model.Possede;


/**
 * Session Bean implementation class ServiceGestionEcoleJPA
 */
@Stateless(mappedName = "ServiceGestionEcole")
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceGestionCompetenceJPA implements	ServiceGestionCompJPALocal {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Default constructor.
	 */
	public ServiceGestionCompetenceJPA() {
		// TODO Auto-generated constructor stub
	}

	// ----------------------------------------------------------
	// JM méthodes compétences à implémenter
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Competence> rechercherCompetence(Competence comp) {

		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Competence> query = qb.createQuery(Competence.class);
		Root<Competence> compRoot = query.from(Competence.class);
		List<Predicate> criteria = new ArrayList<Predicate>();

		if (comp.getCompId() != null) {
			criteria.add(qb.equal(compRoot.get("compId"), comp.getCompId()));
		}
		if (comp.getCompIntitule() != null) {
			criteria.add(qb.equal(compRoot.get("compIntitule"),
					comp.getCompIntitule()));
		}

		if (comp.getCompetences() != null) {
			// criteria.add(qb.equal(compRoot.<String>get("competences"),
			// comp.getCompetences()));
			criteria.add(qb.equal(compRoot.<String> get("competences"),
					comp.getCompetences()));
		}

		if (comp.getCompetence() != null) {
			criteria.add(qb.equal(compRoot.<String> get("competence"),
					comp.getCompetence()));
		}

		query.where(criteria.toArray(new Predicate[] {}));
		List<Competence> result = entityManager.createQuery(query)
				.getResultList();
		return result;
	}

	// JM méthodes compétences à implémenter
	// --------------------------------------------------------
	// Delete Competence
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteCompetence(Competence deletedCompetence) {
		// TODO Auto-generated method stub

		if (deletedCompetence.getCompId() != null) {
			// passage du monde objet au monde relationnel ?? ou juste
			// completion de
			// l'entité ?
			deletedCompetence = entityManager.find(Competence.class,
					deletedCompetence.getCompId());
			// pere de la competence à modifier
			Competence father = deletedCompetence.getCompetence();
			// Liste des enfants de la compétence modifiée
			List<Competence> children = deletedCompetence.getCompetences();
			// modification des enfants, leur père devient le père de la
			// compétence
			// supprimée
			for (Competence comp : children) {
				comp.setCompetence(father); // modification onde objet
				updateCompetence(comp); // modification coté persistance
			}

			// Recherche et suppression de toutes les relations avec cette commp
			// creation d'un modèle vide
			Possede relation = new Possede();
			// on initialise le modèle de relation avec la competence à
			// supprimer
			relation.setCompetence(deletedCompetence);
			// on remplie le liste de toutes les relations ayant cette
			// compétence
			List<Possede> listRelation = rechercherPossede(relation);
			// on supprime toutes les relation trouvée dans la classe

			// on eleve la dependance FK possede de la Table Possede
			for (Possede rel : listRelation) {
				// la relation rel doit necessairement posseder un Id
				deletePossede(rel);
			}

			// enfin on supprime la competence
			entityManager.remove(deletedCompetence);
		}
	}

	// Delete Competence
	// --------------------------------------------------------

	private List<Possede> rechercherPossede(Possede relation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insertCompetence(Competence newCompetence) {
		// TODO Auto-generated method stub
		// doit necessairement posséder un pere
		if (newCompetence.getCompetence() != null) {
			entityManager.persist(newCompetence);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void updateCompetence(Competence updatedCompetence) {
		// TODO Auto-generated method stub
		// doit necessairement posséder un id
		if (updatedCompetence.getCompId() != null) {
			// Attention : ERROR: null value in column "comp_valide" violates not-null constraint
			Boolean compValide = true;
			updatedCompetence.setCompValide(compValide);		
			entityManager.merge(updatedCompetence);
		}
		// return entityManager.merge(updatedCompetence);
	}

	// -------------------------------------------------------------------
	// Ajout Méthodes JM
	public void setChildCompetence(List<Competence> competences) {
		// Affectation de la liste des enfants comme attributs
		for (Competence comp : competences) {
			Competence searchCompChild = new Competence();
			// initialisation de modèle : compétence vide
			searchCompChild.setCompetence(comp); // affectation du père
			List<Competence> resultChild = rechercherCompetence(searchCompChild);
			// recherche de toute les compétences ayant comp pour père
			// affectation de cette liste à comp
			comp.setCompetences(resultChild);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deletePossede(Possede possede) {
		if (possede.getPossId() != null) {
		// la relation à supprimer necessite de posszeder un Id
		possede = entityManager.find(Possede.class, possede.getPossId());
		entityManager.remove(possede);
		}
	}

}
