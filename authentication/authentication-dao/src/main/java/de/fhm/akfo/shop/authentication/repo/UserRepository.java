package de.fhm.akfo.shop.authentication.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.fhm.akfo.shop.authentication.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Find User by username and password.
	 * @param username
	 * @param password
	 * @return A List of Users with this Credentials. Normally only one User or null expected.
	 */
	@Query("SELECT p FROM User p WHERE p.username = :username AND p.password = :password")
	public List<User> findUserByNameAndPassword(@Param("username") String username, @Param("password") String password);
	
	
	/**
	 * Find User by username
	 * @param username
	 * @return A List of Users with this Credentials. Normally only one User or null expected.
	 */
	@Query("SELECT p FROM User p WHERE p.username = :username")
	public List<User> findUserByUsername(@Param("username") String username);

}
