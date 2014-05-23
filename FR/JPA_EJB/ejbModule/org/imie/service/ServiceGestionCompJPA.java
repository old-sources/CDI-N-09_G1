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
import model.arbre.Branche;

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

	// private ServiceGestionEcoleJPALocal serviceGestionEcole = new
	// ServiceGestionEcoleJPA();
	// static Boolean rootexist;

	// ----------------------------------------------------------
	// JM méthodes compétences à implémenter
	// TODO Auto-generated method stub
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Competence> rechercherCompetence(Competence comp) {
		// searchCompetences
		// id peut etre nul ?
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

		//actualisation des infos liées aux personnes participant aux projets
		for(Competence compIt : result){
			compIt.getCompetences().size();
			//compIt.getCompetence().equals(null);
		}
		System.out.println("recherche-----------------");
		return result;
	}

	// ----------------------------------------------------------
	// JM méthodes compétences
	// necessite rechercherPossede et deletePossede
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteCompetence(Competence deletedCompetence) {
		// TODO Auto-generated method stub
		if (deletedCompetence.getCompId() != null) {

			if (!"root".equals(deletedCompetence.getCompIntitule())) {
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

				// Recherche et suppression de toutes les relations avec cette
				// commp
				// creation d'un modèle vide
				Possede relation = new Possede();
				// on initialise le modèle de relation avec la competence à
				// supprimer
				relation.setCompetence(deletedCompetence);
				// on remplie le liste de toutes les relations ayant cette
				// compétence

				if (relation != null) {
					List<Possede> listRelation = // serviceGestionEcole
					rechercherPossedeC(relation);
					// on supprime toutes les relation trouvée dans la classe

					// on eleve la dependance FK possede de la Table Possede
					for (Possede rel : listRelation) {
						// la relation rel doit necessairement posseder un Id
						deletePossede(rel);
					}
					System.out.println("relations supprimées");
				}

				// meme chose proposition suppression
				PropositionComp prop = new PropositionComp();
				prop.setCompetence(deletedCompetence);

				List<PropositionComp> listProp = rechercherPropComp(prop);
				for (PropositionComp propit : listProp) {
					// la relation rel doit necessairement posseder un Id
					deletePropComp(propit);
				}
				System.out.println("propositions supprimées");

				// enfin on supprime la competence
				entityManager.remove(deletedCompetence);
			}
		}
	}

	// --------------------------------------------------------
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deletePropComp(PropositionComp propcomp) {
		// TODO Auto-generated method stub

		if (propcomp.getIdNotif() != null) {
			// la prop à supprimer necessite de posseder un Id
			propcomp = entityManager.find(PropositionComp.class,
					propcomp.getIdNotif());
			entityManager.remove(propcomp);
		}
	}

	// --------------------------------------------------------
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Integer insertCompetence(Competence newCompetence) {
		// TODO Auto-generated method stub
		// doit necessairement posséder un pere ??
		// if (newCompetence.getCompetence() != null)
		// dans ce cas là, on met la compétence à la racine
		// mettre à faux si seulement proposition
		Boolean compValide = true;

		Integer nbInsert = 0;
		if (rechercherCompetence(newCompetence).isEmpty()) {
			if (!"root".equals(newCompetence.getCompIntitule())) {
				System.out.println("**********************");
				System.out.println("insertComp"
						+ newCompetence.getCompIntitule());
				newCompetence.setCompValide(compValide);
				entityManager.persist(newCompetence);
				// synchro ??
				nbInsert = newCompetence.getCompId();
			}
		}

		return nbInsert;

	}

	// -------------------------------------------------------------------
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void updateCompetence(Competence updatedCompetence) {
		// TODO Auto-generated method stub
		Competence compToUpdate = entityManager.find(Competence.class,
				updatedCompetence.getCompId());

		// doit necessairement posséder un id
		if (updatedCompetence.getCompId() != null) {
			if (rechercherCompetence(updatedCompetence).isEmpty()) {
				if (!"root".equals(updatedCompetence.getCompIntitule())) {
					compToUpdate.setCompIntitule(updatedCompetence
							.getCompIntitule());
					entityManager.merge(compToUpdate);
				} else {
					System.out.println("## Pas d'update root immuable ##");
				}

			} else {
				System.out.println("## Pas d'update existe deja ##");
			}
		} else {
			System.out.println("## Pas d'update id nulle ##");
		}
	}

	// -------------------------------------------------------------------
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public Integer addRoot(List<Competence> competences) {

		// Boolean rootExist = false; // static
		Competence rootModel = new Competence();
		Integer rootId = 0;
		rootModel = racine();

		List<Competence> foundRacine = rechercherCompetence(rootModel);

		if (foundRacine.size() > 0) {
			// rootExist = true;

			setChildCompetence(foundRacine);
			rootModel = foundRacine.get(0);
			rootId = rootModel.getCompId();
			;

		} else {
			rootModel = racine();
			// rootExist = true;
			// rootModel = entityManager.find(Competence.class,
			// rootModel.getCompId());
			// rootId = rootModel.getCompId();

			// if (rechercherCompetence(rootModel).isEmpty())
			entityManager.persist(rootModel);
			rootId = rootModel.getCompId();
			// rootId = insertCompetence(rootModel);
		}

		// ajout de la racine sur les comp
		for (Competence compToUpdate : competences) {
			// doit necessairement posséder un id
			if (compToUpdate.getCompetence() == null) {

				if (!"root".equals(compToUpdate.getCompIntitule())) {
					compToUpdate.setCompetence(rootModel);
					// updateCompetence(compToUpdate);
					movedCompetence(compToUpdate);
				}
			}
		}

		setChildRoot(rootModel);
		// return rootExist;
		return rootId;
	}

	// -------------------------------------------------------------------
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void setChildRoot(Competence racine) {

		// Affectation de la liste des enfants comme attributs
		Competence searchCompChild = new Competence();
		// initialisation de modèle : compétence vide
		searchCompChild.setCompetence(racine); // affectation du père
		List<Competence> resultChild = rechercherCompetence(searchCompChild);
		// recherche de toute les compétences ayant comp pour père
		// affectation de cette liste à comp
		racine.setCompetences(resultChild);
	}

	// -------------------------------------------------------------------
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public Competence racine() {
		// creation du model de racine
		Competence rootModel = new Competence();
		rootModel.setCompIntitule("root");
		rootModel.setCompValide(true);
		// rootModel.setCompId(12);
		return rootModel;
	}

	// -------------------------------------------------------------------
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public Competence racineBase(Competence root) {
		Competence racine = new Competence();
		racine = entityManager.find(Competence.class, root.getCompId());
		return racine;
	}

	// -------------------------------------------------------------------
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Competence> rechercherRacines() {
		System.out.println("rechercherRacines");
		// on crée un modèle vide de type compétence
		Competence modelCompetence = new Competence();
		System.out.println("creation du modele à chercher");
		modelCompetence.setCompetence(racine());
		System.out.println("recherche des comp ayant root comme pere");
		// renvoie la liste des racines
		List<Competence> foundRacines = rechercherCompetence(modelCompetence);
		System.out.println("retour liste");
		return foundRacines;
		// searchCompetences
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

		CriteriaBuilder qb = entityManager.getCriteriaBuilder();

		CriteriaQuery<PropositionComp> query = qb
				.createQuery(PropositionComp.class);
		Root<PropositionComp> propcompRoot = query.from(PropositionComp.class);

		List<Predicate> criteria = new ArrayList<Predicate>();

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
	public List<Branche> constructionArbre(List<Competence> competencesRacine,
			Integer nivp) {

		System.out.println("constructionArbre");
		List<Branche> couple = new ArrayList<Branche>();
		setChildCompetence(competencesRacine);
		int nbEnfant ;
		for (Competence comp : competencesRacine) {
			List<Competence> listeEnfants = new ArrayList<Competence>();
			listeEnfants = comp.getCompetences();
			nivp = nivp + 1;
			nbEnfant = couple.size() + 1;
			couple.add(new Branche(comp, nivp,nbEnfant));
			// liste enfants = comp.getCompetences()
			constructionArbre(listeEnfants, nivp);
		}
		return couple;

	}
	

	// --------------------------------------------------------
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Integer tailleArbre(List<Branche> branches) {
		System.out.println("tailleArbre");
		int taille =0;
		for (Branche branche : branches) {
			taille = Math.max(taille,branche.getNiv());
		}
		return taille;
	}
	
	// --------------------------------------------------------
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Integer[] tailleBranche(List<Branche> branches) {
		System.out.println("tailleBranche");
				
		Integer tailleB[] = new Integer[tailleArbre(branches)];
	
		
		System.out.println("tailleBranche init 0"+tailleArbre(branches));
		for (Branche branche : branches) {
			int niv = branche.getNiv();
			tailleB[niv-1] = 0;
		}
		
		System.out.println("tailleBranche init");
		
		for (Branche branche : branches) {
			
			int niv = branche.getNiv();
			tailleB[niv-1] = Math.max(tailleB[niv-1],branche.getNbenfants());
			System.out.println("boucle "+niv);
		}
		
		System.out.println("Fini init");
		
		return tailleB;
	}
	
	// --------------------------------------------------------
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String[][] tableauArbre(List<Branche> branches,Integer[] tailleB) {
		
		System.out.println("tableauArbre");
		int nEnfantsmax = 0;
		
		System.out.println("calcul enfants max");
		for (Branche branche : branches) {
			nEnfantsmax = Math.max(nEnfantsmax,branche.getNbenfants());		
		}
	
		System.out.println("Init à chaine vide");
		String tab[][] = new String[tailleB.length][nEnfantsmax] ;
		for (int i = 0; i < tailleB.length; i++) {
			for (int j = 0; j < nEnfantsmax; j++) {
				tab[i][j] = "";
			}	
		}
			
//		for (int i = 0; i < tailleB.length; i++) {
//			for (int j = 0; j < nEnfantsmax; j++) {
//				tab[i][j] = branche.getComp(i,j).getCompIntitule();;
//			}	
//		}
		
		System.out.println("init tableau");
		for (Branche branche : branches) {
			tab[branche.getNiv()-1][branche.getNbenfants()-1] = branche.getComp().getCompIntitule();
		}
		
		System.out.println("retour");
		return tab;
	}
	
	// --------------------------------------------------------
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int tabMax(List<Branche> branches) {
		
		System.out.println("tabMax");
		int nEnfantsmax = 0;
		
		for (Branche branche : branches) {
			nEnfantsmax = Math.max(nEnfantsmax,branche.getNbenfants());		
		}
		return nEnfantsmax;
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
			// completion / synchro
			movedCompetence = entityManager.find(Competence.class,
					movedCompetence.getCompId());

			// rechercher si nouveau pere appartient aux enfants
			// Liste enfants de la compétence modifiée : children
			List<Competence> parent = new ArrayList<Competence>();
			List<Competence> children = new ArrayList<Competence>();
			parent.add(movedCompetence);
			// initialisation des enfants des enfants
			setChildCompetence(parent);
			// remplissage
			children = parent.get(0).getCompetences();

			// modification des enfants, si besoin
			Boolean testParent = true;
			// pour tous les enfants on regarde si nouveau pere
			// n'est pas dans la liste
			for (Competence comp : children) {

				if (comp.getCompId() == father.getCompId()) {
					testParent = false;
					System.out.println("UPDATE impossible parent = enfant");
				}
				if ("root".equals(comp.getCompIntitule())) {
					System.out.println("UPDATE impossible comp = root");
					testParent = false;
				}
			}

			// enfin on merge la competence
			if (testParent) {
				movedCompetence.setCompetence(father);
				entityManager.merge(movedCompetence);
			}

		}

	}

	// // --------------------------------------------------------
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deletePossede(Possede possede) {
		if (possede.getPossId() != null) {
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
	public List<Possede> rechercherPossedeC(Possede possede) {
		System.out.println("rechercherPossede"
				+ possede.getCompetence().getCompIntitule());

		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		System.out.println("query");
		CriteriaQuery<Possede> query = qb.createQuery(Possede.class);

		Root<Possede> possedeRoot = query.from(Possede.class);

		List<Predicate> criteria = new ArrayList<Predicate>();

		if (possede.getPossId() != null) {
			criteria.add(qb.equal(possedeRoot.get("possId"),
					possede.getPossId()));
		}
		if (possede.getCompNiveau() != null) {
			criteria.add(qb.equal(possedeRoot.get("compNiveau"),
					possede.getCompNiveau()));
		}
		if (possede.getCompetence() != null) {
			criteria.add(qb.equal(possedeRoot.get("competence"),
					possede.getCompetence()));
		}
		if (possede.getPersonne() != null) {
			criteria.add(qb.equal(possedeRoot.get("personne"),
					possede.getPersonne()));
		}

		query.where(criteria.toArray(new Predicate[] {}));

		List<Possede> result = entityManager.createQuery(query).getResultList();

		return result;
	}

}
