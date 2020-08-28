package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ers_user_roles")
public class Role {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_role_id")
	private int roleID;
	@Column(name="user_role")
	private String roleName;
	public Role() {
		super();
	}
	public Role(int roleID, String role) {
		super();
		this.roleID = roleID;
		this.roleName = role;
	}
	public Role(String role) {
		super();
		this.roleName = role;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
		result = prime * result + roleID;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (roleName == null) {
			if (other.roleName != null)
				return false;
		} else if (!roleName.equals(other.roleName))
			return false;
		if (roleID != other.roleID)
			return false;
		return true;
	}
	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int role_id) {
		this.roleID = role_id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String role) {
		this.roleName = role;
	}
	@Override
	public String toString() {
		return "Role [role_id=" + roleID + ", role=" + roleName + "]";
	}

}
