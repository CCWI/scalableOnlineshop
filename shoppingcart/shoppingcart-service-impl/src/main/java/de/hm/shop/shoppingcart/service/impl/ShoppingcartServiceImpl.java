package de.hm.shop.shoppingcart.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.hm.shop.shoppingcart.dao.entity.ShoppingcartEntity;
import de.hm.shop.shoppingcart.dao.repo.ShoppingcartRepository;
import de.hm.shop.shoppingcart.service.api.ShoppingcartService;
import de.hm.shop.shoppingcart.service.api.bo.ShoppingcartBo;
import de.hm.shop.shoppingcart.service.api.exception.ShoppingcartException;
import de.hm.shop.shoppingcart.service.impl.mapper.ShoppingcartBoEntityMapper;


/**
 * Implementierung von {@link ShoppingcartService}.
 * @author Maximilian.Auch
 */
@Service
@Transactional
public class ShoppingcartServiceImpl implements ShoppingcartService {

	private static final Logger LOG = LoggerFactory.getLogger(ShoppingcartServiceImpl.class);

	@Inject
	private ShoppingcartRepository shoppingcartRepository;

	@Inject
	private ShoppingcartBoEntityMapper shoppingcartMapper;



	@Transactional(readOnly = true)
	public List<ShoppingcartBo> getAllForUser(Long userId) {
		final List<ShoppingcartBo> shoppingcartBos = new ArrayList<ShoppingcartBo>();

		final Iterable<ShoppingcartEntity> shoppingcartEntities = shoppingcartRepository.findEntriesByUserId(userId);
		if (shoppingcartEntities != null) {
			for (final ShoppingcartEntity shoppingcartEntity : shoppingcartEntities) {
				if(shoppingcartEntity != null){
					shoppingcartBos.add(shoppingcartMapper.mapEntityToBo(shoppingcartEntity));
				}
			}
		}

		return shoppingcartBos;
	}



	@Transactional(readOnly = true)
	public ShoppingcartBo getByUserId(final long id) {
		final ShoppingcartEntity shoppingcartEntity = shoppingcartRepository.findOne(id);
		return shoppingcartEntity != null ? shoppingcartMapper.mapEntityToBo(shoppingcartEntity) : null;
	}



	public ShoppingcartBo save(final ShoppingcartBo shoppingcartBo) throws ShoppingcartException {
		Validate.notNull(shoppingcartBo);
		LOG.info("Speichere Shoppingcart-Eintrag mit der artikelId {} auf die userId {}", 
				shoppingcartBo.getArticleId(), shoppingcartBo.getUserId());

		final ShoppingcartEntity shoppingcartEntity = shoppingcartMapper.mapBoToEntity(shoppingcartBo);
		
		Iterable<ShoppingcartEntity> entriesOfUser = shoppingcartRepository.findEntriesByUserId(shoppingcartEntity.getUserId());

		ShoppingcartEntity shoppingcartEntitySaved = null;
		
		Boolean saved = false;
		if(entriesOfUser != null) {
			Integer counter = 0;
			while (entriesOfUser.iterator().hasNext() && counter < 20) {
	            ShoppingcartEntity sce = entriesOfUser.iterator().next();
	            if (sce.getArticleId().equals(shoppingcartEntity.getArticleId())) {
	            	shoppingcartEntity.setQuantity(shoppingcartEntity.getQuantity()+sce.getQuantity());
	            	delete(sce.getArticleId(), sce.getUserId());
	            	shoppingcartEntitySaved = shoppingcartRepository.save(shoppingcartEntity);
	            	saved = true;
	            	break;
	            }
	            counter++;
	        }
		}
		
		if(saved == false) {
			shoppingcartEntitySaved = shoppingcartRepository.save(shoppingcartEntity);
		}
		
		return shoppingcartMapper.mapEntityToBo(shoppingcartEntitySaved);
	}


	
	
	public ShoppingcartBo update(final ShoppingcartBo shoppingcartBo) throws ShoppingcartException {
		Validate.notNull(shoppingcartBo);
		LOG.info("Speichere Shoppingcart-Eintrag mit der artikelId {} auf die userId {}", 
				shoppingcartBo.getArticleId(), shoppingcartBo.getUserId());

		final ShoppingcartEntity shoppingcartEntity = shoppingcartMapper.mapBoToEntity(shoppingcartBo);
		
		Iterable<ShoppingcartEntity> entriesOfUser = shoppingcartRepository.findEntriesByUserId(shoppingcartEntity.getUserId());

		ShoppingcartEntity shoppingcartEntitySaved = null;
		
		if(entriesOfUser != null) {
			while (entriesOfUser.iterator().hasNext()) {
	            ShoppingcartEntity sce = entriesOfUser.iterator().next();
	            if (sce.getArticleId().equals(shoppingcartEntity.getArticleId()) &&
	            		sce.getQuantity() != shoppingcartBo.getQuantity()) {
	            	shoppingcartEntity.setQuantity(shoppingcartEntity.getQuantity());
	            	delete(sce.getArticleId(), sce.getUserId());
	            	shoppingcartEntitySaved = shoppingcartRepository.save(shoppingcartEntity);
	            	break;
	            }
	        }
		}
		return shoppingcartMapper.mapEntityToBo(shoppingcartEntitySaved);
	}
	
	

	public void delete(final Long articleId, final Long userId) {
		LOG.info("Lösche Example mit Id {}", articleId);
		
		Validate.notNull(articleId);
		Validate.notNull(userId);

		Iterable<ShoppingcartEntity> entriesOfUser = shoppingcartRepository.findEntriesByUserId(userId);
		if(entriesOfUser != null) {
			Integer counter = 0;
			while (entriesOfUser.iterator().hasNext() && counter < 50) {
	            ShoppingcartEntity sce = entriesOfUser.iterator().next();
	            if (sce.getArticleId().equals(articleId)) {
	            	shoppingcartRepository.delete(sce);
	            	break;
	            }
	            counter++;
	        }
		}else{
			LOG.info("Es wurden keine Artikeleinträge gefunden.");
		}
	}
}
