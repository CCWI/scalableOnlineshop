package de.hm.shop.article.rest.resource;

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
import javax.ws.rs.QueryParam;
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

import de.hm.shop.article.rest.dto.ArticleDto;
import de.hm.shop.article.rest.dto.ArticleDtoList;
import de.hm.shop.article.rest.dto.mapper.ArticleDtoBoMapper;
import de.hm.shop.article.service.api.ArticleService;
import de.hm.shop.article.service.api.bo.ArticleBo;
import de.hm.shop.article.service.api.exception.ArticleException;


@Component
@Path("articles")
@ExposesResourceFor(ArticleDto.class)
@CrossOrigin
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class ArticleResource {

	/** Logger dieser Klasse. */
	private static final Logger LOG = LoggerFactory.getLogger(ArticleResource.class);
	
	@Inject
	private ArticleService articleService;

	@Inject
	private ArticleDtoBoMapper articleMapper;

	@Context
	private UriInfo uriInfo;
	
	@Context
	private transient HttpServletRequest servletRequest;

	@Inject
	private EntityLinks entityLinks;
	

	@GET
	@RolesAllowed(value = { "user" })
	public Response getAllArticle() {
		LOG.info("Aufruf der getAllArticle()-Methode");
		
		final Collection<ArticleBo> articleBos = articleService.getAll();
		final Collection<ArticleDto> articleDtos = mapBosToDtos(articleBos);

		for (final ArticleDto articleDto : articleDtos) {
			addSelfLink(articleDto);
		}

		final ArticleDtoList articleDtoList = new ArticleDtoList(articleDtos,
				entityLinks.linkToCollectionResource(ArticleDto.class));
		return Response.ok(articleDtoList).build();
	}
	
	
	@GET
	@Path("search")
	@RolesAllowed(value = { "user" })
	public Response getByTitleDistanceSearch(@QueryParam("enter") final String searchString, 
											 @QueryParam("distance") final Double distance) {
		LOG.info("Aufruf der getById()-Methode mit dem Suchstring: {}, und der Distanz: {}", searchString, distance);
		
		Validate.notNull(searchString);
		Long userId = ((Integer) this.servletRequest.getAttribute("realUserId")).longValue();
		String userToken = (String) this.servletRequest.getAttribute("userToken");
		Validate.notNull(userId);
		Validate.notNull(userToken);
		
		final Collection<ArticleBo> articleBos = articleService.getByTitleDistanceSearch(userId, searchString, distance, userToken);
		final Collection<ArticleDto> articleDtos = mapBosToDtos(articleBos);
		
		if (articleDtos != null) {
			for (final ArticleDto articleDto : articleDtos) {
				addSelfLink(articleDto);
			}

			final ArticleDtoList articleDtoList = new ArticleDtoList(articleDtos,
					entityLinks.linkToCollectionResource(ArticleDto.class));
			return Response.ok(articleDtoList).build();
		} else {
			return Response.status(Status.NOT_FOUND).entity(Status.NOT_FOUND.getReasonPhrase()).build();
		}
	}



	@GET
	@Path("{id}")
	public Response getById(@PathParam("id") final Long id) {
		LOG.info("Aufruf der getById()-Methode mit der id: {}", id);
		
		Validate.notNull(id);

		final ArticleBo articleBo = articleService.getById(id);
		if (articleBo != null) {
			final ArticleDto articleDto = articleMapper.mapBoToDto(articleBo);
			return okResponseWithSelfLink(articleDto);
		} else {
			return Response.status(Status.NOT_FOUND).entity(Status.NOT_FOUND.getReasonPhrase()).build();
		}
	}

	

	@POST
	@RolesAllowed(value = { "user" })
	public Response createNewArticle(final ArticleDto articleDto) {
		LOG.info("Aufruf der createNewArticle()-Methode");
		
		Validate.notNull(articleDto);
		Validate.validState(articleDto.getId() == null);

		final ArticleDto articleDtoSaved = saveImpl(articleDto);
		final Link selfLink = addSelfLink(articleDtoSaved);

		return Response.created(URI.create(selfLink.getHref())).entity(articleDtoSaved).build();
	}



	@PUT
	@Path("{id}")
	public Response updateArticle(@PathParam(value = "id") String id, final ArticleDto articleDto) {
		LOG.info("Aufruf der updateArticle()-Methode mit der id: {}", id);
		
		Validate.notNull(articleDto);
		Validate.notNull(articleDto.getId());

		if(Long.decode(id) != articleDto.getId()){
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		final ArticleDto articleDtoSaved = saveImpl(articleDto);
		return okResponseWithSelfLink(articleDtoSaved);
	}



	@Path("{id}")
	@DELETE
	public Response deleteById(@PathParam("id") final Long id) {
		Validate.notNull(id);

		articleService.delete(id);
		return Response.noContent().build();
	}



	private ArticleDto saveImpl(final ArticleDto articleDto) {
		try {
			final ArticleBo articleBo = articleMapper.mapDtoToBo(articleDto);
			final ArticleBo articleBoSaved = articleService.save(articleBo);
			return articleMapper.mapBoToDto(articleBoSaved);
		} catch (final ArticleException e) {
			throw new WebApplicationException(e);
		}
	}



	private Collection<ArticleDto> mapBosToDtos(final Collection<ArticleBo> articleBos) {
		final Collection<ArticleDto> articleDtos = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(articleBos)) {
			for (final ArticleBo articleBo : articleBos) {
				articleDtos.add(articleMapper.mapBoToDto(articleBo));
			}
		}
		return articleDtos;
	}
	
	

	private Response okResponseWithSelfLink(final ArticleDto articleDto) {
		addSelfLink(articleDto);
		return Response.ok(articleDto).build();
	}



	private Link addSelfLink(final ArticleDto articleDto) {
		Validate.notNull(articleDto);

		final Link selfLink = entityLinks.linkToSingleResource(articleDto);
		articleDto.getLinks().add(selfLink);

		return selfLink;
	}
}
