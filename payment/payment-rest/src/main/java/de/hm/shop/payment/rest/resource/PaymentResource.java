package de.hm.shop.payment.rest.resource;

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

import de.hm.shop.payment.rest.dto.PaymentDto;
import de.hm.shop.payment.rest.dto.PaymentDtoList;
import de.hm.shop.payment.rest.dto.mapper.PaymentDtoBoMapper;
import de.hm.shop.payment.service.api.PaymentService;
import de.hm.shop.payment.service.api.bo.PaymentBo;
import de.hm.shop.payment.service.api.exception.PaymentException;


@Component
@Path("payments")
@ExposesResourceFor(PaymentDto.class)
@CrossOrigin
public class PaymentResource {

	/** Logger dieser Klasse. */
	private static final Logger LOG = LoggerFactory.getLogger(PaymentResource.class);
	
	@Inject
	private PaymentService paymentService;

	@Inject
	private PaymentDtoBoMapper paymentMapper;

	@Context
	private UriInfo uriInfo;

	@Inject
	private EntityLinks entityLinks;

	

	@GET
	@Path("{supplierId}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getAllBySupplierId(@PathParam("supplierId") final Long supplierId) {
		LOG.info("Aufruf der getById()-Methode mit der id: {}", supplierId);
		
		Validate.notNull(supplierId);

		final Collection<PaymentBo> paymentBos = paymentService.getBySupplierId(supplierId);
		
		if(paymentBos == null || paymentBos.isEmpty()){
			return Response.status(404).entity("Not Found").build();
		}
		
		final Collection<PaymentDto> shoppingcartDtos = mapBosToDtos(paymentBos);
		
		for (final PaymentDto shoppingcartDto : shoppingcartDtos) {
			addSelfLink(shoppingcartDto);
		}
		
		final PaymentDtoList shoppingcartDtoList = new PaymentDtoList(shoppingcartDtos,
				entityLinks.linkToCollectionResource(PaymentDto.class));
		return Response.ok(shoppingcartDtoList).build();
	}

	

	@POST
	@RolesAllowed(value = { "user" })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createNewPayment(final PaymentDto paymentDto) {
		LOG.info("Aufruf der createNewPayment()-Methode");
		
		Validate.notNull(paymentDto);
		Validate.validState(paymentDto.getId() == null);

		final PaymentDto paymentDtoSaved = saveImpl(paymentDto);
		final Link selfLink = addSelfLink(paymentDtoSaved);

		return Response.created(URI.create(selfLink.getHref())).entity(paymentDtoSaved).build();
	}



	@PUT
	@Path("{id}")
	@RolesAllowed(value = { "user" })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response updatePayment(@PathParam(value = "id") String id, final PaymentDto paymentDto) {
		LOG.info("Aufruf der updatePayment()-Methode mit der id: {}", id);
		
		Validate.notNull(paymentDto);
		Validate.notNull(paymentDto.getId());

		if(Long.decode(id) != paymentDto.getId()){
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		final PaymentDto paymentDtoSaved = saveImpl(paymentDto);
		return okResponseWithSelfLink(paymentDtoSaved);
	}



	@Path("{id}")
	@DELETE
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteById(@PathParam("id") final Long id) {
		Validate.notNull(id);

		paymentService.delete(id);
		return Response.noContent().build();
	}



	private PaymentDto saveImpl(final PaymentDto paymentDto) {
		try {
			final PaymentBo paymentBo = paymentMapper.mapDtoToBo(paymentDto);
			final PaymentBo paymentBoSaved = paymentService.save(paymentBo);
			return paymentMapper.mapBoToDto(paymentBoSaved);
		} catch (final PaymentException e) {
			throw new WebApplicationException(e);
		}
	}



	private Collection<PaymentDto> mapBosToDtos(final Collection<PaymentBo> paymentBos) {
		final Collection<PaymentDto> paymentDtos = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(paymentBos)) {
			for (final PaymentBo paymentBo : paymentBos) {
				paymentDtos.add(paymentMapper.mapBoToDto(paymentBo));
			}
		}
		return paymentDtos;
	}



	private Response okResponseWithSelfLink(final PaymentDto paymentDto) {
		addSelfLink(paymentDto);
		return Response.ok(paymentDto).build();
	}



	private Link addSelfLink(final PaymentDto paymentDto) {
		Validate.notNull(paymentDto);

		final Link selfLink = entityLinks.linkToSingleResource(paymentDto);
		paymentDto.getLinks().add(selfLink);

		return selfLink;
	}
}
