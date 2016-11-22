package de.hm.shop.payment.dao.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.hm.shop.payment.dao.entity.PaymentEntity;


/**
 * Repository
 * @author Maximilian.Auch
 */
@Repository
public interface PaymentRepository extends PagingAndSortingRepository<PaymentEntity, Long> {

	@Query("SELECT p FROM PaymentEntity p WHERE p.supplierId = :id")
	public Iterable<PaymentEntity> findEntriesByArticleId(@Param("id") long id);
}
