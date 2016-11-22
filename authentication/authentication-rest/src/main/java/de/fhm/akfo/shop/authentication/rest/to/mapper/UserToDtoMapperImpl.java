package de.fhm.akfo.shop.authentication.rest.to.mapper;

import de.fhm.akfo.shop.authentication.rest.to.RoleTo;
import de.fhm.akfo.shop.authentication.rest.to.UserTo;
import de.fhm.akfo.shop.authentication.service.api.dto.RoleDto;
import de.fhm.akfo.shop.authentication.service.api.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2016-06-29T18:38:30+0200",
    comments = "version: 1.0.0.Final, compiler: javac, environment: Java 1.8.0_60 (Oracle Corporation)"
)
public class UserToDtoMapperImpl implements UserToDtoMapper {

    @Override
    public UserDto toToDto(UserTo to) {
        if ( to == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( to.getId() );
        userDto.setUsername( to.getUsername() );
        userDto.setPassword( to.getPassword() );
        userDto.setFailedlogins( to.getFailedlogins() );

        if(to.getRoles() != null){
        	List<RoleDto> roleList = new ArrayList<RoleDto>();
	        for(RoleTo r : to.getRoles()){
	        	roleList.add(toToDto(r));
	        }
	        userDto.setRoles(roleList);
        }
        
        return userDto;
    }

    @Override
    public UserTo dtoToTo(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        UserTo userTo = new UserTo();

        userTo.setId( dto.getId() );
        userTo.setUsername( dto.getUsername() );
        userTo.setPassword( dto.getPassword() );
        userTo.setFailedlogins( dto.getFailedlogins() );
        
        if(dto.getRoles() != null){
	        List<RoleTo> roleList = new ArrayList<RoleTo>();
	        for(RoleDto r : dto.getRoles()){
	        	roleList.add(dtoToTo(r));
	        }
	        userTo.setRoles(roleList);
        }
        
        return userTo;
    }

    @Override
    public RoleDto toToDto(RoleTo to) {
        if ( to == null ) {
            return null;
        }

        RoleDto roleDto = new RoleDto();

        roleDto.setRole( to.getRole() );

        return roleDto;
    }

    @Override
    public RoleTo dtoToTo(RoleDto dto) {
        if ( dto == null ) {
            return null;
        }

        RoleTo roleTo = new RoleTo();

        roleTo.setRole( dto.getRole() );

        return roleTo;
    }
}
