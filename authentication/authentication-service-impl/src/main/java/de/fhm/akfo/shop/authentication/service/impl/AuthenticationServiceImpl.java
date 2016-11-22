package de.fhm.akfo.shop.authentication.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.fhm.akfo.shop.authentication.entity.User;
import de.fhm.akfo.shop.authentication.impl.mapper.UserDtoEntityMapper;
import de.fhm.akfo.shop.authentication.repo.UserRepository;
import de.fhm.akfo.shop.authentication.service.api.AuthenticationService;
import de.fhm.akfo.shop.authentication.service.api.dto.RoleDto;
import de.fhm.akfo.shop.authentication.service.api.dto.UserDto;
import de.fhm.akfo.shop.authentication.service.api.exception.AuthenticationValidationException;


/**
 * Implementierung des Business Service zur Erzeugung einer neuen 
 * bzw. zum Ueberschreiben eines bestehenden Authentication-Objekts.
 * 
 * @author Maximilian.Auch
 */
@Service(value = "AuthenticationService")
@Transactional
@ExposesResourceFor(User.class)
public class AuthenticationServiceImpl implements AuthenticationService {

	/** Logger dieser Klasse. */
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	
	/** Datenzugriffsschicht auf die Entitaet {@link User}. */
    @Inject
	private UserRepository userRepo;

    
//	/** Datenzugriffsschicht auf die Entitaet {@link Role}. */
//    @Inject
//	private RoleRepository roleRepo;
    
    
    private final String ISSUER = "shopsystem";
    private final String SUBJECT = "authentication";
    private final long EXPIRATION_MILLISECONDS = 1800000;
	
    
    
	public String getAuthenticationToken(String username, String password) {
		LOG.info("Die Methode getAuthenticationToken() wird ausgeführt mit den Credentials " + username + ", " + password);
		
		List<User> userList = userRepo.findUserByNameAndPassword(username, password);
		
		if(userList == null || userList.isEmpty()){
			return null;
		}
		if(userList.get(0) == null){
			return null;
		}
		UserDto userDto = UserDtoEntityMapper.INSTANCE.entityToDto(userList.get(0));
		LOG.info("Die Methode saveUserData() wird ausgeführt. Die gefunden Nutzerdaten aus der DB sind {} ", userDto);
		// TODO update method
//		new FailedLoginCounter().handleFailedLoginOnSuccessfulLogin(userDto);
		String token = new JWTTokenGenerator().createJWT(userDto.getId().toString(), 
				ISSUER, SUBJECT, EXPIRATION_MILLISECONDS, createCredentials(userDto));
		
		return token;
	}
	
	
	
	public Long saveUserData(UserDto dto) throws AuthenticationValidationException {
		LOG.info("Die Methode saveUserData() wird ausgeführt mit dem Dto " + dto.toString());
		
		if(!validateRegistrationCredentialsForSave(dto)){
			throw new AuthenticationValidationException(ISSUER);
		}
		
		dto.setRoles(getStandardRolesForNewUser());
		dto.setFailedlogins(0);
		
		User u = UserDtoEntityMapper.INSTANCE.dtoToEntity(dto);		
		User user = userRepo.save(u);
		
		if(user == null) {
			return null;
		}
		
		UserDto userDto = UserDtoEntityMapper.INSTANCE.entityToDto(user);
		LOG.info(" >>> saved Dto: " + userDto.toString());
		
		return userDto.getId();
	}
	
	
	
	private List<RoleDto> getStandardRolesForNewUser() {
		LOG.info("Die Methode getStandardRolesForNewUser() wird ausgeführt");
		List<RoleDto> roleList = new ArrayList<RoleDto>();
		roleList.add(new RoleDto("user"));
		return roleList;
	}


	
	public boolean validateRegistrationCredentialsForSave(UserDto dto){
		LOG.info("Die Methode validateRegistrationCredentials() wird ausgeführt mit den Credentials {} ", dto);
		boolean goodCredentials = true;
		
		// Check for required Credentials
		if(dto.getPassword() == null || dto.getUsername() == null){
			goodCredentials = false;
		};
		// Check for double username
		if(!userRepo.findUserByUsername(dto.getUsername()).isEmpty()){
			goodCredentials = false;
		}
		
		return goodCredentials;
	}
	
	
	
	public Map<String, Object> createCredentials(UserDto dto){
		LOG.info("Die Methode createCredentials() wird ausgeführt mit den Credentials {} ", dto);
		Map<String, Object> credentials = new HashMap<String, Object>();
		
		credentials.put("userId", dto.getId());
		credentials.put("username", dto.getUsername());
		credentials.put("role", dto.getRoles());
		credentials.put("failedLogins", dto.getFailedlogins());
		
		return credentials;
	}


	
	public UserDto getUserData(String username) throws AuthenticationValidationException {
		LOG.info("Die Methode getUserData() wird für den User {} ausgeführt.", username);
		
		if(username == null) {
			throw new AuthenticationValidationException("Some usercredentials in Token are not available");
		}
		
		List<User> user = userRepo.findUserByUsername(username);
		UserDto userDto = UserDtoEntityMapper.INSTANCE.entityToDto(user.get(0));
		
		return userDto;
	}
}
