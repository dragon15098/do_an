package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "role")
public class Role extends BaseModel {

	private String roleName;

	@ManyToMany(mappedBy = "roles")
	private List<User> users;

	public Role(Long id) {
		this.setId(id);
	}
}
