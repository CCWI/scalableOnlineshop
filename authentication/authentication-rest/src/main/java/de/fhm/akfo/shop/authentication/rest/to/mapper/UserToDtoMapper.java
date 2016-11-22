package de.fhm.akfo.shop.authentication.rest.to.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import de.fhm.akfo.shop.authentication.rest.to.RoleTo;
import de.fhm.akfo.shop.authentication.rest.to.UserTo;
import de.fhm.akfo.shop.authentication.service.api.dto.RoleDto;
import de.fhm.akfo.shop.authentication.service.api.dto.UserDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserToDtoMapper {

	UserToDtoMapper INSTANCE = Mappers.getMapper( UserToDtoMapper.class );
	
	@Mapping(target = "roles", ignore=true)
	UserDto toToDto(UserTo to);
	
	@Mapping(target = "roles", ignore=true)
	UserTo dtoToTo(UserDto dto);

	RoleDto toToDto(RoleTo to);
	
	RoleTo dtoToTo(RoleDto dto);
}
