package de.hm.shop.shoppingcart.dao.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.hm.shop.shoppingcart.dao.entity.ShoppingcartEntity;


/**
 * Repository
 * @author Maximilian.Auch
 */
@Repository
public interface ShoppingcartRepository extends PagingAndSortingRepository<ShoppingcartEntity, Long> {

	@Query("SELECT p FROM ShoppingcartEntity p WHERE p.userId = :userId")
	public Iterable<ShoppingcartEntity> findEntriesByUserId(@Param("userId") Long userId);
}
