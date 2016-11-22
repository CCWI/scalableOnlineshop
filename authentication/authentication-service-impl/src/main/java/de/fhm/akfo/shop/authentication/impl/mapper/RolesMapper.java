package de.fhm.akfo.shop.authentication.impl.mapper;

import java.util.HashSet;
import java.util.Set;

import de.fhm.akfo.shop.authentication.entity.Role;
import de.fhm.akfo.shop.authentication.entity.User;

public class RolesMapper {

	
	public Set<String> rolesMapping(User user){
		Set<String> roleSet = new HashSet<String>();
		
		if(user != null && user.getRoles() != null){
			for(Role role : user.getRoles()){
				roleSet.add(role.getRole());
			}
		}
		
		return roleSet;
	}
	
//	public Set<Role> mapStringsToRoles(Set<String> roleStrings){
//		Set<String> roleSet = new HashSet<String>();
//		
//		if(user != null && user.getRoles() != null){
//			for(Role role : user.getRoles()){
//				roleSet.add(role.getRole());
//			}
//		}
//		
//		return roleSet;
//	}
}
