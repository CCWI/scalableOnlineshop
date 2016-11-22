package de.hm.shop.shoppingcart.rest.resource;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
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

import de.hm.shop.shoppingcart.rest.dto.ShoppingcartDto;
import de.hm.shop.shoppingcart.rest.dto.ShoppingcartDtoList;
import de.hm.shop.shoppingcart.rest.dto.mapper.ShoppingcartDtoBoMapper;
import de.hm.shop.shoppingcart.service.api.ShoppingcartService;
import de.hm.shop.shoppingcart.service.api.bo.ShoppingcartBo;
import de.hm.shop.shoppingcart.service.api.exception.ShoppingcartException;


/**
 * Die REST-Ressource für Shoppingcart
 * @author Maximilian.Auch
 */
@Component
@Path("shoppingcart")
@ExposesResourceFor(ShoppingcartDto.class)
@CrossOrigin
public class ShoppingcartResource {

	/** Logger dieser Klasse. */
	private static final Logger LOG = LoggerFactory.getLogger(ShoppingcartResource.class);
	
	@Inject
	private ShoppingcartService shoppingcartService;

	@Inject
	private ShoppingcartDtoBoMapper shoppingcartMapper;

	@Context
	private transient HttpServletRequest servletRequest;
	
	@Context
	private UriInfo uriInfo;

	@Inject
	private EntityLinks entityLinks;



	@GET
	@Path("{userId}")
	@RolesAllowed(value = { "user" })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getById(@PathParam("userId") final Long userId) {
		LOG.info("Aufruf der getById()-Methode mit der id: {}", userId);
		
		Validate.notNull(userId);

		Long realUserId = ((Integer) this.servletRequest.getAttribute("realUserId")).longValue();

		if(!userId.equals(realUserId)){
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		
		final Collection<ShoppingcartBo> shoppingcartBos = shoppingcartService.getAllForUser(userId);
		
		
		if(shoppingcartBos == null || shoppingcartBos.isEmpty()){
			return Response.status(404).entity("Not Found").build();
		}
		
		final Collection<ShoppingcartDto> shoppingcartDtos = mapBosToDtos(shoppingcartBos);
		
		for (final ShoppingcartDto shoppingcartDto : shoppingcartDtos) {
			addSelfLink(shoppingcartDto);
		}

		final ShoppingcartDtoList shoppingcartDtoList = new ShoppingcartDtoList(shoppingcartDtos,
				entityLinks.linkToCollectionResource(ShoppingcartDto.class));
		return Response.ok(shoppingcartDtoList).build();
	}



	@POST
	@RolesAllowed(value = { "user" })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createNewShoppingcart(final ShoppingcartDto shoppingcartDto) {
		LOG.info("Aufruf der createNewShoppingcart()-Methode mit den Parametern: {}", shoppingcartDto.toString());
		
		Validate.notNull(shoppingcartDto);

		Long userId = ((Integer) this.servletRequest.getAttribute("realUserId")).longValue();

		if(!userId.equals(shoppingcartDto.getUserId())){
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		final ShoppingcartDto shoppingcartDtoSaved;
		
		try {
			final ShoppingcartBo shoppingcartBo = shoppingcartMapper.mapDtoToBo(shoppingcartDto);
			final ShoppingcartBo shoppingcartBoSaved = shoppingcartService.save(shoppingcartBo);
			shoppingcartDtoSaved = shoppingcartMapper.mapBoToDto(shoppingcartBoSaved);
		} catch (final ShoppingcartException e) {
			throw new WebApplicationException(e);
		}
		final Link selfLink = addSelfLink(shoppingcartDtoSaved);

		return Response.created(URI.create(selfLink.getHref())).entity(shoppingcartDtoSaved).build();
	}



	@PUT
	@Path("{id}")
	@RolesAllowed(value = { "user" })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response updateShoppingcart(@PathParam(value = "id") String articleId, final ShoppingcartDto shoppingcartDto) {
		LOG.info("Aufruf der updateShoppingcart()-Methode mit der id: {}", articleId);
		
		Validate.notNull(shoppingcartDto);
		Validate.notNull(shoppingcartDto.getArticleId());
		Validate.notNull(shoppingcartDto.getUserId());
		Validate.notNull(shoppingcartDto.getQuantity());
		
		Long userId = ((Integer) this.servletRequest.getAttribute("realUserId")).longValue();

		if(Long.decode(articleId) != shoppingcartDto.getArticleId()
				|| !userId.equals(shoppingcartDto.getUserId())){
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		final ShoppingcartDto shoppingcartDtoSaved;
		
		try {
			final ShoppingcartBo shoppingcartBo = shoppingcartMapper.mapDtoToBo(shoppingcartDto);
			final ShoppingcartBo shoppingcartBoSaved = shoppingcartService.update(shoppingcartBo);
			shoppingcartDtoSaved = shoppingcartMapper.mapBoToDto(shoppingcartBoSaved);
		} catch (final ShoppingcartException e) {
			throw new WebApplicationException(e);
		}
		return okResponseWithSelfLink(shoppingcartDtoSaved);
	}



	@Path("{articleId}")
	@DELETE
	@RolesAllowed(value = { "user" })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteById(@PathParam("articleId") final Long articleId) {
		Validate.notNull(articleId);
		
		if(!(this.servletRequest.getAttribute("realUserId") instanceof Integer)){
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		Long userId = ((Integer) this.servletRequest.getAttribute("realUserId")).longValue();
		shoppingcartService.delete(articleId, userId);
		
		return Response.noContent().build();
	}


	private Collection<ShoppingcartDto> mapBosToDtos(final Collection<ShoppingcartBo> shoppingcartBos) {
		final Collection<ShoppingcartDto> shoppingcartDtos = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(shoppingcartBos)) {
			for (final ShoppingcartBo shoppingcartBo : shoppingcartBos) {
				shoppingcartDtos.add(shoppingcartMapper.mapBoToDto(shoppingcartBo));
			}
		}
		return shoppingcartDtos;
	}



	private Response okResponseWithSelfLink(final ShoppingcartDto shoppingcartDto) {
		addSelfLink(shoppingcartDto);
		return Response.ok(shoppingcartDto).build();
	}



	private Link addSelfLink(final ShoppingcartDto shoppingcartDto) {
		Validate.notNull(shoppingcartDto);

		final Link selfLink = entityLinks.linkToSingleResource(shoppingcartDto);
		shoppingcartDto.getLinks().add(selfLink);

		return selfLink;
	}
}
