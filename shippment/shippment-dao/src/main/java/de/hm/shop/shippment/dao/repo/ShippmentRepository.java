package de.hm.shop.shippment.dao.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import de.hm.shop.shippment.dao.entity.ShippmentEntity;


/**
 * Repository
 * @author Maximilian.Auch
 */
@Repository
public interface ShippmentRepository extends PagingAndSortingRepository<ShippmentEntity, Long> {

}
