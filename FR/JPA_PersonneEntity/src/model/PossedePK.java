package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the possede database table.
 * 
 */
@Embeddable
public class PossedePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="comp_id")
	private Integer compId;

	private Integer id;

	public PossedePK() {
	}
	public Integer getCompId() {
		return this.compId;
	}
	public void setCompId(Integer compId) {
		this.compId = compId;
	}
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PossedePK)) {
			return false;
		}
		PossedePK castOther = (PossedePK)other;
		return 
			this.compId.equals(castOther.compId)
			&& this.id.equals(castOther.id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.compId.hashCode();
		hash = hash * prime + this.id.hashCode();
		
		return hash;
	}
}