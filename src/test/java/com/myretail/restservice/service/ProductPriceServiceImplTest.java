package com.myretail.restservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.myretail.restservice.exception.NotFoundException;
import com.myretail.restservice.model.ProductPrice;
import com.myretail.restservice.repository.ProductPriceRepository;
import com.myretail.restservice.service.impl.ProductPriceServiceImpl;

@SpringBootTest
public class ProductPriceServiceImplTest {

	@Mock private ProductPriceRepository productPriceRepository;

	@InjectMocks private ProductPriceServiceImpl productPriceService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetProductPrice_InvalidId() {
		Optional<ProductPrice> price = Optional.ofNullable(null);

		doReturn(price).when(productPriceRepository).findById(anyLong());

		NotFoundException result = assertThrows(NotFoundException.class, () -> productPriceService.getProductPrice(1L));

		verify(productPriceRepository).findById(1L);
		assertEquals("Price for product 1 not found", result.getMessage());
	}

	@Test
	public void testGetProductPrice_Pass() {
		Optional<ProductPrice> price = Optional.ofNullable(new ProductPrice());

		doReturn(price).when(productPriceRepository).findById(anyLong());

		ProductPrice result = productPriceService.getProductPrice(1L);

		verify(productPriceRepository).findById(1L);
		assertEquals(price.get(), result);
	}

	@Test
	public void testSaveProductPrice_Null() {
		doReturn(returnsFirstArg()).when(productPriceRepository).save(any());

		IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
				() -> productPriceService.saveProductPrice(null));

		assertEquals("Cannot save null object", result.getMessage());
	}

	@Test
	public void testSaveProductPrice_InvalidId() {
		ProductPrice price = new ProductPrice();

		IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
				() -> productPriceService.saveProductPrice(price));

		assertEquals("Cannot save price for invalid id", result.getMessage());
	}

	@Test
	public void testSaveProductPrice_Pass() {
		ProductPrice price = new ProductPrice();
		price.setId(1L);

		doAnswer(returnsFirstArg()).when(productPriceRepository).save(any());

		ProductPrice result = productPriceService.saveProductPrice(price);

		verify(productPriceRepository).save(price);
		assertEquals(price, result);
	}
}
