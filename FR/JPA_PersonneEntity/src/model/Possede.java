package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the possede database table.
 * 
 */
@Entity
public class Possede implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="poss_id")
	private Integer possId;

	@Column(name="comp_niveau")
	private Integer compNiveau;

	//bi-directional many-to-one association to Competence
	@ManyToOne
	@JoinColumn(name="comp_id")
	private Competence competence;

	//bi-directional many-to-one association to Personne
	@ManyToOne
	@JoinColumn(name="pers_id")
	private Personne personne;

	public Possede() {
	}

	public Integer getPossId() {
		return this.possId;
	}

	public void setPossId(Integer possId) {
		this.possId = possId;
	}

	public Integer getCompNiveau() {
		return this.compNiveau;
	}

	public void setCompNiveau(Integer compNiveau) {
		this.compNiveau = compNiveau;
	}

	public Competence getCompetence() {
		return this.competence;
	}

	public void setCompetence(Competence competence) {
		this.competence = competence;
	}

	public Personne getPersonne() {
		return this.personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

}