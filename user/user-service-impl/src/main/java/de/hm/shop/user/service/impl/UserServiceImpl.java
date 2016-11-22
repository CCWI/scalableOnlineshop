package de.hm.shop.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.hm.shop.user.dao.entity.UserEntity;
import de.hm.shop.user.dao.repo.UserRepository;
import de.hm.shop.user.service.api.UserService;
import de.hm.shop.user.service.api.bo.UserBo;
import de.hm.shop.user.service.api.exception.UserException;
import de.hm.shop.user.service.impl.mapper.UserBoEntityMapper;


/**
 * Implementierung von {@link UserService}.
 * @author Maximilian.Auch
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	@Inject
	private UserRepository userRepository;

	@Inject
	private UserBoEntityMapper userMapper;



	@Transactional(readOnly = true)
	public List<UserBo> getAll() {
		final List<UserBo> userBos = new ArrayList<UserBo>();

		final Iterable<UserEntity> userEntities = userRepository.findAll();
		if (userEntities != null) {
			for (final UserEntity userEntity : userEntities) {
				userBos.add(userMapper.mapEntityToBo(userEntity));
			}
		}
		return userBos;
	}



	@Transactional(readOnly = true)
	public UserBo getById(final long id) {
		LOG.info("Aufruf der getById()-Methode in der Businessschnicht mit der id: {}", id);
		final UserEntity userEntity = userRepository.findOne(id);
		return userEntity != null ? userMapper.mapEntityToBo(userEntity) : null;
	}

	

	@Transactional(readOnly = true)
	public UserBo getSupplier(Long supplierId){
		LOG.info("Aufruf der getBySupplierId()-Methode in der Businessschnicht mit der id: {}", supplierId);
		final UserEntity userEntity = userRepository.findSupplier(supplierId);
		return userEntity != null ? userMapper.mapEntityToBo(userEntity) : null;
	}
	


	public UserBo save(final UserBo userBo) throws UserException {
		Validate.notNull(userBo);
		LOG.debug("Speichere Example mit Namen {}, {}", userBo.getLastname(), userBo.getFirstname());

		final UserEntity userEntity = userMapper.mapBoToEntity(userBo);
		LOG.info("Entity: {}", userEntity.toString());
		final UserEntity userEntitySaved = userRepository.save(userEntity);

		return userMapper.mapEntityToBo(userEntitySaved);
	}


	public UserBo update(final UserBo userBo) throws UserException {
		Validate.notNull(userBo);
		LOG.debug("Speichere Example mit Namen {}, {}", userBo.getLastname(), userBo.getFirstname());

		final UserEntity user = userMapper.mapBoToEntity(userBo);
		
		UserBo bo = getById(user.getId());
		
		if(bo.getId() != user.getId()){
			throw new UserException("User konnte nicht upgedated werden!");
		}
		
		userRepository.update(user);

		//LOG.info("User wurde auf die ID {} ({}) upgedated und wird mit der ID {} abgeglichen", id, id.longValue(), user.getId());
		
		//if(id.longValue() == user.getId()){
			return userMapper.mapEntityToBo(user);
		//}
		//LOG.error("Die ID {} ist ungleich der ID {}!! Fehlerbehandlung folgt!", id.longValue(), user.getId());
		//throw new UserException("User konnte nicht upgedated werden!");
	}
	

	public void delete(final Long id) {
		Validate.notNull(id);
		LOG.debug("Lösche Example mit Id {}", id);

		if (userRepository.findOne(id) != null) {
			userRepository.delete(id);
		}
	}
	
}
