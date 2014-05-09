package model;

import java.io.Serializable;
import javax.persistence.*;


///**
// * The persistent class for the travaille database table.
// * 
// */
//@Entity
public class Travaille implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="trv_id")
	private Integer trvId;

	//bi-directional many-to-one association to Personne
	@ManyToOne
	@JoinColumn(name="pers_id")
	private Personne personne;

	//bi-directional many-to-one association to Projet
	@ManyToOne
	@JoinColumn(name="proj_id")
	private Projet projet;

	public Travaille() {
	}

	public Integer getTrvId() {
		return this.trvId;
	}

	public void setTrvId(Integer trvId) {
		this.trvId = trvId;
	}

	public Personne getPersonne() {
		return this.personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public Projet getProjet() {
		return this.projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}

}