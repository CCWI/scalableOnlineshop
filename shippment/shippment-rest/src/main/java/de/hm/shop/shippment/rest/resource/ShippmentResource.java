package de.hm.shop.shippment.rest.resource;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import de.hm.shop.shippment.rest.dto.ShippmentDto;
import de.hm.shop.shippment.rest.dto.ShippmentDtoList;
import de.hm.shop.shippment.rest.dto.mapper.ShippmentDtoBoMapper;
import de.hm.shop.shippment.service.api.ShippmentService;
import de.hm.shop.shippment.service.api.bo.ShippmentBo;
import de.hm.shop.shippment.service.api.exception.ShippmentException;


@Component
@Path("shippments")
@ExposesResourceFor(ShippmentDto.class)
@CrossOrigin
public class ShippmentResource {

	/** Logger dieser Klasse. */
	private static final Logger LOG = LoggerFactory.getLogger(ShippmentResource.class);
	
	@Inject
	private ShippmentService shippmentService;

	@Inject
	private ShippmentDtoBoMapper shippmentMapper;

	@Context
	private UriInfo uriInfo;

	@Inject
	private EntityLinks entityLinks;



	@GET
	@RolesAllowed(value = { "user" })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getAllShippment() {
		LOG.info("Aufruf der getAllShippment()-Methode");
		
		final Collection<ShippmentBo> shippmentBos = shippmentService.getAll();
		final Collection<ShippmentDto> shippmentDtos = mapBosToDtos(shippmentBos);

		for (final ShippmentDto shippmentDto : shippmentDtos) {
			addSelfLink(shippmentDto);
		}

		final ShippmentDtoList shippmentDtoList = new ShippmentDtoList(shippmentDtos,
				entityLinks.linkToCollectionResource(ShippmentDto.class));
		return Response.ok(shippmentDtoList).build();
	}



	@GET
	@Path("{id}")
	@RolesAllowed(value = { "user" })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getById(@PathParam("id") final Long id) {
		LOG.info("Aufruf der getById()-Methode mit der id: {}", id);
		
		Validate.notNull(id);

		final ShippmentBo shippmentBo = shippmentService.getById(id);
		if (shippmentBo != null) {
			final ShippmentDto shippmentDto = shippmentMapper.mapBoToDto(shippmentBo);
			return okResponseWithSelfLink(shippmentDto);
		} else {
			return Response.status(Status.NOT_FOUND).entity(Status.NOT_FOUND.getReasonPhrase()).build();
		}
	}



	@POST
	@RolesAllowed(value = { "user" })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createNewShippment(final ShippmentDto shippmentDto) {
		LOG.info("Aufruf der createNewShippment()-Methode");
		
		Validate.notNull(shippmentDto);
		Validate.validState(shippmentDto.getId() == null);

		final ShippmentDto shippmentDtoSaved = saveImpl(shippmentDto);
		final Link selfLink = addSelfLink(shippmentDtoSaved);

		return Response.created(URI.create(selfLink.getHref())).entity(shippmentDtoSaved).build();
	}



	@PUT
	@Path("{id}")
	@RolesAllowed(value = { "user" })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response updateShippment(@PathParam(value = "id") String id, final ShippmentDto shippmentDto) {
		LOG.info("Aufruf der updateShippment()-Methode mit der id: {}", id);
		
		Validate.notNull(shippmentDto);
		Validate.notNull(shippmentDto.getId());

		if(Long.decode(id) != shippmentDto.getId()){
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		final ShippmentDto shippmentDtoSaved = saveImpl(shippmentDto);
		return okResponseWithSelfLink(shippmentDtoSaved);
	}



	@Path("{id}")
	@DELETE
	@RolesAllowed(value = { "user" })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteById(@PathParam("id") final Long id) {
		Validate.notNull(id);

		shippmentService.delete(id);
		return Response.noContent().build();
	}



	private ShippmentDto saveImpl(final ShippmentDto shippmentDto) {
		try {
			final ShippmentBo shippmentBo = shippmentMapper.mapDtoToBo(shippmentDto);
			final ShippmentBo shippmentBoSaved = shippmentService.save(shippmentBo);
			return shippmentMapper.mapBoToDto(shippmentBoSaved);
		} catch (final ShippmentException e) {
			throw new WebApplicationException(e);
		}
	}



	private Collection<ShippmentDto> mapBosToDtos(final Collection<ShippmentBo> shippmentBos) {
		final Collection<ShippmentDto> shippmentDtos = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(shippmentBos)) {
			for (final ShippmentBo shippmentBo : shippmentBos) {
				shippmentDtos.add(shippmentMapper.mapBoToDto(shippmentBo));
			}
		}
		return shippmentDtos;
	}



	private Response okResponseWithSelfLink(final ShippmentDto shippmentDto) {
		addSelfLink(shippmentDto);
		return Response.ok(shippmentDto).build();
	}



	private Link addSelfLink(final ShippmentDto shippmentDto) {
		Validate.notNull(shippmentDto);

		final Link selfLink = entityLinks.linkToSingleResource(shippmentDto);
		shippmentDto.getLinks().add(selfLink);

		return selfLink;
	}
}
