package de.fhm.akfo.shop.authentication.service.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhm.akfo.shop.authentication.entity.User;
import de.fhm.akfo.shop.authentication.impl.mapper.UserDtoEntityMapper;
import de.fhm.akfo.shop.authentication.repo.UserRepository;
import de.fhm.akfo.shop.authentication.service.api.dto.UserDto;

public class FailedLoginCounter {

	/** Logger dieser Klasse. */
	private static final Logger LOG = LoggerFactory.getLogger(FailedLoginCounter.class);
	
	/** Datenzugriffsschicht auf die Entitaet {@link Authentication}. */
    @Inject
	private UserRepository userRepo;

	
	public void handleFailedLoginOnSuccessfulLogin(UserDto dto){
		LOG.info("Methode handleFailedLoginOnSuccessfulLogin() mit den Nutzerdaten {} aufgerufen", dto);
		if(dto.getFailedlogins() > 0){
			
			if(dto.getFailedlogins() > 20){
				// TODO Dem Nutzer informieren und ein Passwortwechsel vorschlagen
			}
			
			try {
				UserDto newDto = (UserDto) dto.clone();
				LOG.info("Cloned Object: {}", newDto );
				newDto.setFailedlogins(0);
				User u = UserDtoEntityMapper.INSTANCE.dtoToEntity(newDto);
				LOG.info("Cloned Entity: {}", u );
				userRepo.save(u);
			} catch (CloneNotSupportedException e) {
				LOG.error("Cloning of UserDto is failed. HandleFailedLoginOnSuccessfulLogin skiped!");
			}
		}
	}
}
