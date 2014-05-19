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

import model.Competence;
import model.Possede;
import model.PropositionComp;

/**
 * Session Bean implementation class ServiceGestionEcoleJPA
 */
@Stateless(mappedName = "ServiceGestionComp")
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceGestionCompJPA implements // ServiceGestionCompJPARemote,
		ServiceGestionCompJPALocal {

	@PersistenceContext
	private EntityManager entityManager;
//	private ServiceGestionEcoleJPALocal serviceGestionEcole = new ServiceGestionEcoleJPA();

	// ----------------------------------------------------------
	// JM méthodes compétences à implémenter
	// TODO Auto-generated method stub
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Competence> rechercherCompetence(Competence comp) {
		// searchCompetences
		System.out.println("Entree recherche competence "
				+ comp.getCompIntitule());
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Competence> query = qb.createQuery(Competence.class);
		Root<Competence> compRoot = query.from(Competence.class);
		List<Predicate> criteria = new ArrayList<Predicate>();

		if (comp.getCompId() != null) {
			criteria.add(qb.equal(compRoot.get("compId"), comp.getCompId()));
		}
		if (comp.getCompIntitule() != null) {
			System.out.println("On devrait passeer par la"
					+ comp.getCompIntitule());
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

	// ----------------------------------------------------------
	// JM méthodes compétences
	// necessite rechercherPossede et deletePossede
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteCompetence(Competence deletedCompetence) {
		// TODO Auto-generated method stub
		System.out.println("deleteCompetence "+ deletedCompetence.getCompId());
		if (deletedCompetence.getCompId() != null) {

			// passage du monde objet au monde relationnel ?? ou juste
			// completion de l'entité ?
			deletedCompetence = entityManager.find(Competence.class,
					deletedCompetence.getCompId());
			
			
			// pere de la competence à modifier
			Competence father = deletedCompetence.getCompetence();
			// Liste des enfants de la compétence modifiée
			List<Competence> children = deletedCompetence.getCompetences();
			// modification enfants, le père devient le père de la
			// compétence supprimée
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

			if (relation != null) {
				System.out.println("relation non nulle");
				List<Possede> listRelation = //serviceGestionEcole
						rechercherPossede(relation);
				// on supprime toutes les relation trouvée dans la classe

				// on eleve la dependance FK possede de la Table Possede
				for (Possede rel : listRelation) {
					// la relation rel doit necessairement posseder un Id
					deletePossede(rel);
				}
				System.out.println("on a supprimé les relation");
			}

			System.out.println("Proposition");
			// meme chose proposition suppression
			PropositionComp prop = new PropositionComp();
			prop.setCompetence(deletedCompetence);

			List<PropositionComp> listProp = rechercherPropComp(prop);
			for (PropositionComp propit : listProp) {
				// la relation rel doit necessairement posseder un Id
				deletePropComp(propit);
			}
			System.out.println("Fin suppression des propsitions");

			// enfin on supprime la competence
			entityManager.remove(deletedCompetence);
		}
	}

	// --------------------------------------------------------
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void deletePropComp(PropositionComp propcomp) {
		// TODO Auto-generated method stub
		System.out.println("deletePropComp");
		if (propcomp.getIdNotif() != null) {
			// la prop à supprimer necessite de posseder un Id
			propcomp = entityManager.find(PropositionComp.class,
					propcomp.getIdNotif());
			entityManager.remove(propcomp);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insertCompetence(Competence newCompetence) {
		// TODO Auto-generated method stub
		// doit necessairement posséder un pere ??
		// if (newCompetence.getCompetence() != null)
		// dans ce cas là, on met la compétence à la racine
		entityManager.persist(newCompetence);

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void updateCompetence(Competence updatedCompetence) {
		// TODO Auto-generated method stub
		Competence compToUpdate = entityManager.find(Competence.class,
				updatedCompetence.getCompId());

		// doit necessairement posséder un id
		if (updatedCompetence.getCompId() != null) {
			// Attention : ERROR: null value in column "comp_valide" violates
			// not-null constraint faire ? Boolean compValide = true;
			// updatedCompetence.setCompValide(compValide);
			compToUpdate.setCompIntitule(updatedCompetence.getCompIntitule());
			entityManager.merge(compToUpdate);
		}
	}

	// -------------------------------------------------------------------
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
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

	// --------------------------------------------------------
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<PropositionComp> rechercherPropComp(PropositionComp propcomp) {
		// TODO Auto-generated method stub
		System.out.println("rechercherPropComp");

		CriteriaBuilder qb = entityManager.getCriteriaBuilder();

		CriteriaQuery<PropositionComp> query = qb
				.createQuery(PropositionComp.class);
		Root<PropositionComp> propcompRoot = query.from(PropositionComp.class);

		List<Predicate> criteria = new ArrayList<Predicate>();
		// List<PropositionComp> listProp = null;
		if (propcomp.getIdNotif() != null) {
			criteria.add(qb.equal(propcompRoot.get("id_notif"),
					propcomp.getIdNotif()));
		}

		if (propcomp.getCompetence() != null) {
			criteria.add(qb.equal(propcompRoot.<String> get("competence"),
					propcomp.getCompetence()));
		}

		query.where(criteria.toArray(new Predicate[] {}));

		List<PropositionComp> result = entityManager.createQuery(query)
				.getResultList();

		return result;
	}

	// --------------------------------------------------------
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	// public void movedCompetence(Competence movedComp, Competence father) {
	public void movedCompetence(Competence movedCompetence) {
		if (movedCompetence.getCompId() != null) {

			// pere de la competence à modifier
			Competence father = movedCompetence.getCompetence();

			// passage du monde objet au monde relationnel ??
			// juste completion entité ?
			// Competence model = movedCompetence;
			movedCompetence = entityManager.find(Competence.class,
					movedCompetence.getCompId());
			// rechercher si nouveau pere appartient aux enfants
			// Liste enfants de la compétence modifiée
			List<Competence> children = new ArrayList<Competence>();
			children.add(movedCompetence);
			setChildCompetence(children);

			children = movedCompetence.getCompetences();
			// modification des enfants, si besoin
			Boolean testParent = true;
			for (Competence comp : children) {
				if (comp.getCompId() == father.getCompId()) {
					testParent = false;
				}

			}

			// enfin on supprime la competence
			if (testParent) {
				movedCompetence.setCompetence(father);
				entityManager.merge(movedCompetence);
			}

		}

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deletePossede(Possede possede) {
		if (possede.getPossId() != null) {
			System.out.println("deletePossede");
			// la relation à supprimer necessite de posszeder un Id
			possede = entityManager.find(Possede.class, possede.getPossId());
			entityManager.remove(possede);
		}
	}

	// // --------------------------------------------------------
	// @Override
	// public void updatePropComp(PropositionComp propitToUpdate) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// // --------------------------------------------------------
	// @Override
	// public void updatePossede(Possede relToUpdate) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// // --------------------------------------------------------
	// @Override
	// public void movedCompetence(Competence movedComp, Competence father) {
	// // TODO Auto-generated method stub
	//
	// }
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Possede> rechercherPossede(Possede possede) {
		System.out.println("rechercherPossede"+possede.getCompetence().getCompIntitule());
		
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		System.out.println("query");
		CriteriaQuery<Possede> query = qb.createQuery(Possede.class);
		System.out.println("root");
		Root<Possede> possedeRoot = query.from(Possede.class);

		List<Predicate> criteria = new ArrayList<Predicate>();
		
		System.out.println("avant la recherche");
		if (possede.getPossId() != null) {
			criteria.add(qb.equal(possedeRoot.get("possId"),
					possede.getPossId()));
		}
		if (possede.getCompNiveau() != null) {
			criteria.add(qb.equal(possedeRoot.get("compNiveau"),
					possede.getCompNiveau()));
		}
		if (possede.getCompetence() != null) {
			System.out.println("on devrait passer par la");
			criteria.add(qb.equal(possedeRoot.get("competence"),
					possede.getCompetence()));
		}
		if (possede.getPersonne() != null) {
			criteria.add(qb.equal(possedeRoot.get("personne"),
					possede.getPersonne()));
		}
		
		System.out.println("Fin Test possede");
		
		query.where(criteria.toArray(new Predicate[] {}));

		List<Possede> result = entityManager.createQuery(query).getResultList();
				
		return result;
	}

}
