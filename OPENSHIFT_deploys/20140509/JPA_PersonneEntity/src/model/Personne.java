package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the personne database table.
 * 
 */
@Entity
public class Personne implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pers_id")
	private Integer id;

	private Boolean cgu;

	@Temporal(TemporalType.DATE)
	private Date dateNaiss;

	private Boolean disponibilite;

	private String email;

	@Column(name="ident_connexion")
	private String identConnexion;

	private String infos;

	private String nom;

	private String passw;

	private String prenom;

	//uni-directional many-to-many association to Actionanotifier
	@ManyToMany
	@JoinTable(
		name="envoyer"
		, joinColumns={
			@JoinColumn(name="pers_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_notif")
			}
		)
	private List<Actionanotifier> actionanotifiers;

	//uni-directional many-to-one association to Promotion
	@ManyToOne
	@JoinColumn(name="prm_id")
	private Promotion promotion;

	//uni-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;

	//bi-directional many-to-one association to Possede
	@OneToMany(mappedBy="personne")
	private List<Possede> possedes;

	//bi-directional many-to-one association to Projet
	@OneToMany(mappedBy="personne")
	private List<Projet> projets;

	//bi-directional many-to-one association to Travaille
	@OneToMany(mappedBy="personne")
	private List<Travaille> travailles;

	public Personne() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer persId) {
		this.id = persId;
	}

	public Boolean getCgu() {
		return this.cgu;
	}

	public void setCgu(Boolean cgu) {
		this.cgu = cgu;
	}

	public Date getDateNaiss() {
		return this.dateNaiss;
	}

	public void setDateNaiss(Date datenaiss) {
		this.dateNaiss = datenaiss;
	}

	public Boolean getDisponibilite() {
		return this.disponibilite;
	}

	public void setDisponibilite(Boolean disponibilite) {
		this.disponibilite = disponibilite;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdentConnexion() {
		return this.identConnexion;
	}

	public void setIdentConnexion(String identConnexion) {
		this.identConnexion = identConnexion;
	}

	public String getInfos() {
		return this.infos;
	}

	public void setInfos(String infos) {
		this.infos = infos;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPassw() {
		return this.passw;
	}

	public void setPassw(String passw) {
		this.passw = passw;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public List<Actionanotifier> getActionanotifiers() {
		return this.actionanotifiers;
	}

	public void setActionanotifiers(List<Actionanotifier> actionanotifiers) {
		this.actionanotifiers = actionanotifiers;
	}

	public Promotion getPromotion() {
		return this.promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Possede> getPossedes() {
		return this.possedes;
	}

	public void setPossedes(List<Possede> possedes) {
		this.possedes = possedes;
	}

	public Possede addPossede(Possede possede) {
		getPossedes().add(possede);
		possede.setPersonne(this);

		return possede;
	}

	public Possede removePossede(Possede possede) {
		getPossedes().remove(possede);
		possede.setPersonne(null);

		return possede;
	}

	public List<Projet> getProjets() {
		return this.projets;
	}

	public void setProjets(List<Projet> projets) {
		this.projets = projets;
	}

	public Projet addProjet(Projet projet) {
		getProjets().add(projet);
		projet.setPersonne(this);

		return projet;
	}

	public Projet removeProjet(Projet projet) {
		getProjets().remove(projet);
		projet.setPersonne(null);

		return projet;
	}

	public List<Travaille> getTravailles() {
		return this.travailles;
	}

	public void setTravailles(List<Travaille> travailles) {
		this.travailles = travailles;
	}

	public Travaille addTravaille(Travaille travaille) {
		getTravailles().add(travaille);
		travaille.setPersonne(this);

		return travaille;
	}

	public Travaille removeTravaille(Travaille travaille) {
		getTravailles().remove(travaille);
		travaille.setPersonne(null);

		return travaille;
	}

}