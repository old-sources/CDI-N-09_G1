package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the competence database table.
 * 
 */
@Entity
public class Competence implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="comp_id")
	private Integer compId;

	@Column(name="comp_intitule")
	private String compIntitule;

	@Column(name="comp_valide")
	private Boolean compValide;

	//bi-directional many-to-one association to Competence
	@ManyToOne
	@JoinColumn(name="comp_id_1")
	private Competence competence;

	//bi-directional many-to-one association to Competence
	@OneToMany(mappedBy="competence")
	private List<Competence> competences;

	//bi-directional many-to-one association to PropositionComp
	@OneToMany(mappedBy="competence")
	private List<PropositionComp> propositionComps;

	public Competence() {
	}

	public Integer getCompId() {
		return this.compId;
	}

	public void setCompId(Integer compId) {
		this.compId = compId;
	}

	public String getCompIntitule() {
		return this.compIntitule;
	}

	public void setCompIntitule(String compIntitule) {
		this.compIntitule = compIntitule;
	}

	public Boolean getCompValide() {
		return this.compValide;
	}

	public void setCompValide(Boolean compValide) {
		this.compValide = compValide;
	}

	public Competence getCompetence() {
		return this.competence;
	}

	public void setCompetence(Competence competence) {
		this.competence = competence;
	}

	public List<Competence> getCompetences() {
		return this.competences;
	}

	public void setCompetences(List<Competence> competences) {
		this.competences = competences;
	}

	public Competence addCompetence(Competence competence) {
		getCompetences().add(competence);
		competence.setCompetence(this);

		return competence;
	}

	public Competence removeCompetence(Competence competence) {
		getCompetences().remove(competence);
		competence.setCompetence(null);

		return competence;
	}

	public List<PropositionComp> getPropositionComps() {
		return this.propositionComps;
	}

	public void setPropositionComps(List<PropositionComp> propositionComps) {
		this.propositionComps = propositionComps;
	}

	public PropositionComp addPropositionComp(PropositionComp propositionComp) {
		getPropositionComps().add(propositionComp);
		propositionComp.setCompetence(this);

		return propositionComp;
	}

	public PropositionComp removePropositionComp(PropositionComp propositionComp) {
		getPropositionComps().remove(propositionComp);
		propositionComp.setCompetence(null);

		return propositionComp;
	}

}