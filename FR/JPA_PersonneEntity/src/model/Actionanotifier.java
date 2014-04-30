package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the actionanotifier database table.
 * 
 */
@Entity
public class Actionanotifier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_notif")
	private Integer idNotif;

	@Temporal(TemporalType.DATE)
	private Date dateenvoi;

	@Column(name="notif_validee")
	private Boolean notifValidee;

	//bi-directional one-to-one association to Candidature
	@OneToOne(mappedBy="actionanotifier")
	private Candidature candidature;

	public Actionanotifier() {
	}

	public Integer getIdNotif() {
		return this.idNotif;
	}

	public void setIdNotif(Integer idNotif) {
		this.idNotif = idNotif;
	}

	public Date getDateenvoi() {
		return this.dateenvoi;
	}

	public void setDateenvoi(Date dateenvoi) {
		this.dateenvoi = dateenvoi;
	}

	public Boolean getNotifValidee() {
		return this.notifValidee;
	}

	public void setNotifValidee(Boolean notifValidee) {
		this.notifValidee = notifValidee;
	}

	public Candidature getCandidature() {
		return this.candidature;
	}

	public void setCandidature(Candidature candidature) {
		this.candidature = candidature;
	}

}