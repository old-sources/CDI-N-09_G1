package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


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

	//bi-directional many-to-one association to InvitationProjet
	@OneToMany(mappedBy="projet")
	private List<InvitationProjet> invitationProjets;

	//bi-directional many-to-one association to Personne
	@ManyToOne
	@JoinColumn(name="id")
	private Personne personne;

	//bi-directional many-to-many association to Personne
	@ManyToMany
	@JoinTable(
		name="travaille"
		, joinColumns={
			@JoinColumn(name="proj_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id")
			}
		)
	private List<Personne> personnes;

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

	public List<InvitationProjet> getInvitationProjets() {
		return this.invitationProjets;
	}

	public void setInvitationProjets(List<InvitationProjet> invitationProjets) {
		this.invitationProjets = invitationProjets;
	}

	public InvitationProjet addInvitationProjet(InvitationProjet invitationProjet) {
		getInvitationProjets().add(invitationProjet);
		invitationProjet.setProjet(this);

		return invitationProjet;
	}

	public InvitationProjet removeInvitationProjet(InvitationProjet invitationProjet) {
		getInvitationProjets().remove(invitationProjet);
		invitationProjet.setProjet(null);

		return invitationProjet;
	}

	public Personne getPersonne() {
		return this.personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public List<Personne> getPersonnes() {
		return this.personnes;
	}

	public void setPersonnes(List<Personne> personnes) {
		this.personnes = personnes;
	}

}