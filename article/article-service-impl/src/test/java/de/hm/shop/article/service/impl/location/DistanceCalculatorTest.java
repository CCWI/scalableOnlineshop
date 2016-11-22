package de.hm.shop.article.service.impl.location;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.theInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import de.hm.shop.article.service.impl.location.DistanceCalculator;

/**
 * 
 * @author Maximilian.Auch
 */
@RunWith(MockitoJUnitRunner.class)
public class DistanceCalculatorTest {

	@Test
	@Ignore
	public void testDistanceCalculator() {
		DistanceCalculator dc = new DistanceCalculator();
		float coordinates = dc.calcDist(48.311465f, 11.918876f, 48.470167f, 11.935867f);
//		assertThat(Float.compare(coordinates, 17691.072f), is(equalTo(0)));
	}

	@Test
	@Ignore
	public void getCoordinatesTest(){
//		when(articleMapperMock.mapBoToEntity(articleDto1)).thenReturn(articleEntity1);
		AddressCoordinatesController acc = new AddressCoordinatesController();
		acc.getCoordinates("Moosburg", "85368");
	}
}
