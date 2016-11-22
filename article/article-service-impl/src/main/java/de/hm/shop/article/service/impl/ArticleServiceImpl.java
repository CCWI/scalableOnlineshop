package de.hm.shop.article.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.hm.shop.article.dao.entity.ArticleEntity;
import de.hm.shop.article.dao.repo.ArticleRepository;
import de.hm.shop.article.service.api.ArticleService;
import de.hm.shop.article.service.api.bo.ArticleBo;
import de.hm.shop.article.service.api.exception.ArticleException;
import de.hm.shop.article.service.impl.dto.UserDto;
import de.hm.shop.article.service.impl.location.AddressCoordinatesController;
import de.hm.shop.article.service.impl.location.DistanceCalculator;
import de.hm.shop.article.service.impl.mapper.ArticleBoEntityMapper;

/**
 * Implementierung von {@link ArticleService}.
 * 
 * @author Maximilian.Auch
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

	private static final Logger LOG = LoggerFactory.getLogger(ArticleServiceImpl.class);
	private Client client = ClientBuilder.newClient();

	@Inject
	private ArticleRepository articleRepository;

	@Inject
	private ArticleBoEntityMapper articleMapper;

	@Transactional(readOnly = true)
	public List<ArticleBo> getAll() {
		final List<ArticleBo> articleBos = new ArrayList<ArticleBo>();

		final Iterable<ArticleEntity> articleEntities = articleRepository.findAll();
		if (articleEntities != null) {
			for (final ArticleEntity articleEntity : articleEntities) {
				articleBos.add(articleMapper.mapEntityToBo(articleEntity));
			}
		}

		return articleBos;
	}

	@Transactional(readOnly = true)
	public ArticleBo getById(final long id) {
		final ArticleEntity articleEntity = articleRepository.findOne(id);
		return articleEntity != null ? articleMapper.mapEntityToBo(articleEntity) : null;
	}

	public ArticleBo save(final ArticleBo articleBo) throws ArticleException {
		Validate.notNull(articleBo);
		LOG.debug("Speichere Article mit Namen {}", articleBo.getArticleTitle());

		final ArticleEntity articleEntity = articleMapper.mapBoToEntity(articleBo);
		final ArticleEntity articleEntitySaved = articleRepository.save(articleEntity);

		return articleMapper.mapEntityToBo(articleEntitySaved);
	}

	public void delete(final Long id) {
		Validate.notNull(id);
		LOG.debug("Lösche Example mit Id {}", id);

		if (articleRepository.exists(id)) {
			articleRepository.delete(id);
		}
	}

	@Override
	public List<ArticleBo> getByTitleDistanceSearch(Long userId, String searchString, Double distanceMaximum, String userToken) {

		final List<ArticleBo> articleBos = new ArrayList<ArticleBo>();
		final Iterable<ArticleEntity> articleEntities = articleRepository.findArticleByTitle(searchString);

		if (articleEntities != null && distanceMaximum != null) {

			Validate.inclusiveBetween(0.01, 1000.00, distanceMaximum.doubleValue());
			UserDto userDto = getUser("user/" + userId, userToken);
			LOG.info("Userdaten des Suchenden abgefragt: {}", userDto);

			if (userDto != null) {
				AddressCoordinatesController acc = new AddressCoordinatesController();
				DistanceCalculator distCalculator = new DistanceCalculator();

				if (userDto.getCity() != null && userDto.getPostcode() != null) {
					Float[] userCoordinates = acc.getCoordinates(userDto.getCity(), userDto.getPostcode());

					for (final ArticleEntity articleEntity : articleEntities) {

						// get User-Entry of SupplierId
						UserDto supplier = getUser("user/supplier/" + articleEntity.getSupplierId(), userToken);
						LOG.info("Userdaten eines Suppliers abgefragt: {}", supplier);

						if (supplier != null) {
							if (supplier.getCity() != null && supplier.getPostcode() != null) {
								Float[] supplierCoordinates = acc.getCoordinates(supplier.getCity(),
										supplier.getPostcode());

								float realDistance = distCalculator.calcDist(userCoordinates[0], userCoordinates[1],
										supplierCoordinates[0], supplierCoordinates[1]);

								if ((Float.compare(realDistance, distanceMaximum.floatValue())) <= 0) {
									articleBos.add(articleMapper.mapEntityToBo(articleEntity));
								}
							}
						}
					}
					return articleBos;
				}
			}
		}
		return null;
	}

	private UserDto getUser(String path, String userToken) {
		LOG.info("Die Methode getUser() wird aufgerufen und hierfür wird der subpath: {} mit dem Token {} abgefragt.",
				path, userToken);
		WebTarget target = client.target("http://172.17.0.1:8087").path(path);
		Response response = target.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).header("Authorization", userToken).get();
		if (checkResponse(response)) {
			return response.readEntity(UserDto.class);
		} else {
			return null;
		}
	}

	private boolean checkResponse(Response response) {
		Validate.notNull(response);
		if (response.getStatus() == 200) {
			return true;
		}
		return false;
	}
}
