package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="role_id")
	private Integer roleId;

	@Column(name="role_intitule")
	private String roleIntitule;

	public Role() {
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleIntitule() {
		return this.roleIntitule;
	}

	public void setRoleIntitule(String roleIntitule) {
		this.roleIntitule = roleIntitule;
	}

}