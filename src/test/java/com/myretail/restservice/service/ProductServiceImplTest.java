package com.myretail.restservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import com.myretail.restservice.dto.Product;
import com.myretail.restservice.exception.NotFoundException;
import com.myretail.restservice.model.ProductPrice;
import com.myretail.restservice.service.impl.ProductServiceImpl;

@SpringBootTest
public class ProductServiceImplTest {

	@Mock private ProductPriceService productPriceService;

	@Spy @InjectMocks private ProductServiceImpl productService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testProductService() {
		assertNotNull(productService);
	}

	@Test
	public void testGetProduct_InvalidId() {
		doThrow(new NotFoundException("product not found")).when(productService).get();

		NotFoundException result = assertThrows(NotFoundException.class, () -> productService.getProduct(1L));

		verify(productService).get();
		assertEquals("product not found", result.getMessage());
	}

	@Test
	public void testGetProduct_ValidIdNoPrice() {
		Product product = new Product();
		doReturn(product).when(productService).get();
		doThrow(new NotFoundException("product price not found")).when(productPriceService).getProductPrice(anyLong());

		NotFoundException result = assertThrows(NotFoundException.class, () -> productService.getProduct(1L));

		verify(productService).get();
		verify(productPriceService).getProductPrice(1L);
		assertEquals("product price not found", result.getMessage());
	}

	@Test
	public void testGetProduct_Pass() {
		Product product = new Product();
		ProductPrice price = new ProductPrice();
		doReturn(product).when(productService).get();
		doReturn(price).when(productPriceService).getProductPrice(anyLong());

		Product result = productService.getProduct(1L);

		verify(productService).get();
		verify(productPriceService).getProductPrice(1L);
		assertEquals(product, result);
		assertEquals(price, result.getCurrentPrice());
	}

	@Test
	public void testSaveProduct_ProductNull() {
		IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
				() -> productService.saveProduct(null));
		assertEquals("product is null", result.getMessage());
	}

	@Test
	public void testSaveProduct_ProductPriceNull() {
		Product product = new Product();

		IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
				() -> productService.saveProduct(product));
		assertEquals("price is null", result.getMessage());
	}

	@Test
	public void testSaveProduct_InvalidId() {
		ProductPrice price = new ProductPrice();
		Product product = new Product();
		product.setId(1L);
		product.setCurrentPrice(price);

		IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
				() -> productService.saveProduct(product));
		assertEquals("invalid id given", result.getMessage());
	}

	@Test
	public void testSaveProduct_Pass() {
		ProductPrice price = new ProductPrice();
		Product product = new Product();
		product.setId(1L);
		product.setCurrentPrice(price);

		doAnswer(returnsFirstArg()).when(productPriceService).saveProductPrice(any());

		Product result = productService.saveProduct(product);
		assertEquals(product, result);
		assertEquals(1L, result.getCurrentPrice().getId());
		assertEquals(price, result.getCurrentPrice());
	}

	@Test
	public void testGet() {
		fail("not implemented yet");
	}
}
