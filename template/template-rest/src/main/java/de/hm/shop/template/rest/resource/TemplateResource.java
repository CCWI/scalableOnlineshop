package de.hm.shop.template.rest.resource;

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

import de.hm.shop.template.rest.dto.TemplateDto;
import de.hm.shop.template.rest.dto.TemplateDtoList;
import de.hm.shop.template.rest.dto.mapper.TemplateDtoBoMapper;
import de.hm.shop.template.service.api.TemplateService;
import de.hm.shop.template.service.api.bo.TemplateBo;
import de.hm.shop.template.service.api.exception.TemplateException;


@Component
@Path("templates")
@ExposesResourceFor(TemplateDto.class)
@CrossOrigin
public class TemplateResource {

	/** Logger dieser Klasse. */
	private static final Logger LOG = LoggerFactory.getLogger(TemplateResource.class);
	
	@Inject
	private TemplateService templateService;

	@Inject
	private TemplateDtoBoMapper templateMapper;

	@Context
	private UriInfo uriInfo;

	@Inject
	private EntityLinks entityLinks;



	@GET
	@RolesAllowed(value = { "user" })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getAllTemplate() {
		LOG.info("Aufruf der getAllTemplate()-Methode");
		
		final Collection<TemplateBo> templateBos = templateService.getAll();
		final Collection<TemplateDto> templateDtos = mapBosToDtos(templateBos);

		for (final TemplateDto templateDto : templateDtos) {
			addSelfLink(templateDto);
		}

		final TemplateDtoList templateDtoList = new TemplateDtoList(templateDtos,
				entityLinks.linkToCollectionResource(TemplateDto.class));
		return Response.ok(templateDtoList).build();
	}



	@GET
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getById(@PathParam("id") final Long id) {
		LOG.info("Aufruf der getById()-Methode mit der id: {}", id);
		
		Validate.notNull(id);

		final TemplateBo templateBo = templateService.getById(id);
		if (templateBo != null) {
			final TemplateDto templateDto = templateMapper.mapBoToDto(templateBo);
			return okResponseWithSelfLink(templateDto);
		} else {
			return Response.status(Status.NOT_FOUND).entity(Status.NOT_FOUND.getReasonPhrase()).build();
		}
	}



	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createNewTemplate(final TemplateDto templateDto) {
		LOG.info("Aufruf der createNewTemplate()-Methode");
		
		Validate.notNull(templateDto);
		Validate.validState(templateDto.getId() == null);

		final TemplateDto templateDtoSaved = saveImpl(templateDto);
		final Link selfLink = addSelfLink(templateDtoSaved);

		return Response.created(URI.create(selfLink.getHref())).entity(templateDtoSaved).build();
	}



	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response updateTemplate(@PathParam(value = "id") String id, final TemplateDto templateDto) {
		LOG.info("Aufruf der updateTemplate()-Methode mit der id: {}", id);
		
		Validate.notNull(templateDto);
		Validate.notNull(templateDto.getId());

		if(Long.decode(id) != templateDto.getId()){
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		final TemplateDto templateDtoSaved = saveImpl(templateDto);
		return okResponseWithSelfLink(templateDtoSaved);
	}



	@Path("{id}")
	@DELETE
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteById(@PathParam("id") final Long id) {
		Validate.notNull(id);

		templateService.delete(id);
		return Response.noContent().build();
	}



	private TemplateDto saveImpl(final TemplateDto templateDto) {
		try {
			final TemplateBo templateBo = templateMapper.mapDtoToBo(templateDto);
			final TemplateBo templateBoSaved = templateService.save(templateBo);
			return templateMapper.mapBoToDto(templateBoSaved);
		} catch (final TemplateException e) {
			throw new WebApplicationException(e);
		}
	}



	private Collection<TemplateDto> mapBosToDtos(final Collection<TemplateBo> templateBos) {
		final Collection<TemplateDto> templateDtos = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(templateBos)) {
			for (final TemplateBo templateBo : templateBos) {
				templateDtos.add(templateMapper.mapBoToDto(templateBo));
			}
		}
		return templateDtos;
	}



	private Response okResponseWithSelfLink(final TemplateDto templateDto) {
		addSelfLink(templateDto);
		return Response.ok(templateDto).build();
	}



	private Link addSelfLink(final TemplateDto templateDto) {
		Validate.notNull(templateDto);

		final Link selfLink = entityLinks.linkToSingleResource(templateDto);
		templateDto.getLinks().add(selfLink);

		return selfLink;
	}
}
