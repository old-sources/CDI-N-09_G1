package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the invitation_projet database table.
 * 
 */
@Entity
@Table(name="invitation_projet")
public class InvitationProjet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_notif")
	private Integer idNotif;

	//bi-directional one-to-one association to Actionanotifier
	@OneToOne
	@JoinColumn(name="id_notif")
	private Actionanotifier actionanotifier;

	//bi-directional many-to-one association to Personne
	@ManyToOne
	@JoinColumn(name="pers_id")
	private Personne personne;

	//bi-directional many-to-one association to Projet
	@ManyToOne
	@JoinColumn(name="proj_id")
	private Projet projet;

	public InvitationProjet() {
	}

	public Integer getIdNotif() {
		return this.idNotif;
	}

	public void setIdNotif(Integer idNotif) {
		this.idNotif = idNotif;
	}

	public Actionanotifier getActionanotifier() {
		return this.actionanotifier;
	}

	public void setActionanotifier(Actionanotifier actionanotifier) {
		this.actionanotifier = actionanotifier;
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