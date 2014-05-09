package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the projet database table.
 * 
 */
@Entity
public class Projet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="proj_id")
	private Integer projId;

	@Column(name="proj_avancement")
	private String projAvancement;

	@Temporal(TemporalType.DATE)
	@Column(name="proj_datedebut")
	private Date projDatedebut;

	@Temporal(TemporalType.DATE)
	@Column(name="proj_datedefin")
	private Date projDatedefin;

	@Column(name="proj_description")
	private String projDescription;

	@Column(name="proj_nom")
	private String projNom;

	@Column(name="proj_wiki_cdp")
	private String projWikiCdp;

	@Column(name="proj_wiki_membre")
	private String projWikiMembre;

	//bi-directional many-to-one association to Candidature
	@OneToMany(mappedBy="projet")
	private List<Candidature> candidatures;

	//bi-directional many-to-one association to Personne
	@ManyToOne
	@JoinColumn(name="pers_id")
	private Personne chefDeProjet;

	//bi-directional many-to-many association to personne
	@ManyToMany
	@JoinTable(name = "travaille",
		joinColumns = {@JoinColumn(name = "proj_id", referencedColumnName = "proj_id")}, 
		inverseJoinColumns = {@JoinColumn(name = "pers_id", referencedColumnName = "pers_id")}
	)
	private List<Personne> membres;

	public Projet() {
	}

	public Integer getProjId() {
		return this.projId;
	}

	public void setProjId(Integer projId) {
		this.projId = projId;
	}

	public String getProjAvancement() {
		return this.projAvancement;
	}

	public void setProjAvancement(String projAvancement) {
		this.projAvancement = projAvancement;
	}

	public Date getProjDatedebut() {
		return this.projDatedebut;
	}

	public void setProjDatedebut(Date projDatedebut) {
		this.projDatedebut = projDatedebut;
	}

	public Date getProjDatedefin() {
		return this.projDatedefin;
	}

	public void setProjDatedefin(Date projDatedefin) {
		this.projDatedefin = projDatedefin;
	}

	public String getProjDescription() {
		return this.projDescription;
	}

	public void setProjDescription(String projDescription) {
		this.projDescription = projDescription;
	}

	public String getProjNom() {
		return this.projNom;
	}

	public void setProjNom(String projNom) {
		this.projNom = projNom;
	}

	public String getProjWikiCdp() {
		return this.projWikiCdp;
	}

	public void setProjWikiCdp(String projWikiCdp) {
		this.projWikiCdp = projWikiCdp;
	}

	public String getProjWikiMembre() {
		return this.projWikiMembre;
	}

	public void setProjWikiMembre(String projWikiMembre) {
		this.projWikiMembre = projWikiMembre;
	}

	public List<Candidature> getCandidatures() {
		return this.candidatures;
	}

	public void setCandidatures(List<Candidature> candidatures) {
		this.candidatures = candidatures;
	}

	public Candidature addCandidature(Candidature candidature) {
		getCandidatures().add(candidature);
		candidature.setProjet(this);

		return candidature;
	}

	public Candidature removeCandidature(Candidature candidature) {
		getCandidatures().remove(candidature);
		candidature.setProjet(null);

		return candidature;
	}

	public Personne getChefDeProjet() {
		return this.chefDeProjet;
	}

	public void setChefDeProjet(Personne chefDeProjet) {
		this.chefDeProjet = chefDeProjet;
	}

	public List<Personne> getMembres() {
		return this.membres;
	}

	public void setMembres(List<Personne> membres) {
		this.membres = membres;
	}

}