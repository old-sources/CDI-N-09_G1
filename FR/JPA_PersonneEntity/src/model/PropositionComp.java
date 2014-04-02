package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the proposition_comp database table.
 * 
 */
@Entity
@Table(name="proposition_comp")
public class PropositionComp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_notif")
	private Integer idNotif;

	//bi-directional one-to-one association to Actionanotifier
	@OneToOne
	@JoinColumn(name="id_notif")
	private Actionanotifier actionanotifier;

	//bi-directional many-to-one association to Competence
	@ManyToOne
	@JoinColumn(name="comp_id")
	private Competence competence;

	public PropositionComp() {
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

	public Competence getCompetence() {
		return this.competence;
	}

	public void setCompetence(Competence competence) {
		this.competence = competence;
	}

}