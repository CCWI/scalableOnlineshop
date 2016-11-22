package de.fhm.akfo.shop.authentication.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import de.fhm.akfo.shop.authentication.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
}
