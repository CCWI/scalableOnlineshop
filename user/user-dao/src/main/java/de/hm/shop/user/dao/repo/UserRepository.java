package de.hm.shop.user.dao.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.hm.shop.user.dao.entity.UserEntity;


/**
 * Repository
 * @author Maximilian.Auch
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

	@Query("SELECT p FROM UserEntity p WHERE p.id = :id")
	UserEntity findOne(@Param("id") Long id);

	
//	@Query("UPDATE UserEntity p SET p.firstname = :firstname, p.lastname = :lastname, "
//			+ "p.address = :address, p.postcode = :postcode, p.city = :city, p.country = :country "
//			+ "WHERE p.id = :id AND p.supplierId = :supplierId")
//	UserEntity update(@Param("id") Long id,
//					  @Param("firstname") String firstname,
//					  @Param("lastname") String lastname,
//					  @Param("address") String address,
//					  @Param("postcode") String postcode,
//					  @Param("city") String city,
//					  @Param("country") String country,
//					  @Param("supplierId") Long supplierId);
//	
	
	@Modifying
	@Query("UPDATE UserEntity p SET p.firstname = :#{#user.firstname}, p.lastname = :#{#user.lastname}, "
			+ "p.address = :#{#user.address}, p.postcode = :#{#user.postcode}, p.city = :#{#user.city}, "
			+ "p.country = :#{#user.country} WHERE p.id = :#{#user.id}")
	void update(@Param("user") UserEntity user);
	
	
	@Modifying
	@Query("DELETE FROM UserEntity p WHERE p.id = :id")
	void delete(@Param("id") Long id);
	
	
	
	/**
	 * Find User by SupplierId
	 * @param supplierId - unique
	 * @return Supplier-User
	 */
	@Query("SELECT p FROM UserEntity p WHERE p.supplierId = :supplierId")
	public UserEntity findSupplier(@Param("supplierId") Long supplierId);


}
