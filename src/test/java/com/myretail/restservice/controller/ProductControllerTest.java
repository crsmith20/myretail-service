package com.myretail.restservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.restservice.dto.Product;
import com.myretail.restservice.exception.NotFoundException;
import com.myretail.restservice.model.ProductPrice;
import com.myretail.restservice.service.ProductService;

@SpringBootTest
public class ProductControllerTest {

	private MockMvc mockMvc;

	@Mock private ProductService productService;

	@InjectMocks private ProductController controller;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testGetProduct_InvalidUrl() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isNotFound()).andExpect(jsonPath("$").doesNotExist());
	}

	@Test
	public void testGetProduct_NoId() throws Exception {
		mockMvc.perform(get("/products/")).andExpect(status().isNotFound()).andExpect(jsonPath("$").doesNotExist());
	}

	@Test
	public void testGetProduct_IdNotFound() throws Exception {
		doThrow(new NotFoundException("id not found")).when(productService).getProduct(anyLong());

		mockMvc.perform(get("/products/1")).andExpect(status().isNotFound()).andExpect(jsonPath("$").doesNotExist());

		verify(productService).getProduct(1L);
	}

	@Test
	public void testGetProduct_OtherError() throws Exception {
		doThrow(new NullPointerException("shouldn't happen but for behavior accountability")).when(productService).getProduct(anyLong());

		mockMvc.perform(get("/products/1")).andExpect(status().isInternalServerError()).andExpect(jsonPath("$").doesNotExist());

		verify(productService).getProduct(1L);
	}

	@Test
	public void testGetProduct_Pass() throws Exception {
		Product product = new Product();
		product.setId(1);

		doReturn(product).when(productService).getProduct(anyLong());

		mockMvc.perform(get("/products/1")).andExpect(status().isOk())
				.andExpect(content().string(new ObjectMapper().writeValueAsString(product)));

		verify(productService).getProduct(1L);
	}

	@Test
	public void testPutProduct_NoId() throws Exception {
		mockMvc.perform(put("/")).andExpect(status().isNotFound()).andExpect(jsonPath("$").doesNotExist());
	}

	@Test
	public void testPutProduct_NoProduct() throws Exception {
		doThrow(new IllegalArgumentException("price is null")).when(productService).saveProduct(anyLong(), any());

		mockMvc.perform(put("/products/1")).andExpect(status().isNoContent()).andExpect(jsonPath("$").doesNotExist());

		verify(productService).saveProduct(1L, null);
	}

	@Test
	public void testPutProduct_IllegalId() throws Exception {
		ProductPrice price = new ProductPrice();
		price.setCurrencyCode("USD");
		price.setValue(new BigDecimal(9.99));
		Product product = new Product();
		product.setId(-1);
		product.setName("Some Movie");
		product.setCurrentPrice(price);

		doThrow(new IllegalArgumentException("id is invalid")).when(productService).saveProduct(anyLong(), any());

		mockMvc.perform(put("/products/-1").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(product)))
				.andExpect(status().isNoContent()).andExpect(jsonPath("$").doesNotExist());

		ArgumentCaptor<Long> arg0 = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<Product> arg1 = ArgumentCaptor.forClass(Product.class);
		verify(productService).saveProduct(arg0.capture(), arg1.capture());
		assertEquals(-1L, arg0.getValue());
		assertEquals(-1L, arg1.getValue().getId());
		assertEquals("Some Movie", arg1.getValue().getName());
		assertEquals("USD", arg1.getValue().getCurrentPrice().getCurrencyCode());
		assertEquals(0, arg1.getValue().getCurrentPrice().getValue().compareTo(new BigDecimal(9.99)));
	}

	@Test
	public void testPutProduct_IllegalPrice() throws Exception {
		ProductPrice price = new ProductPrice();
		price.setCurrencyCode("USD");
		price.setValue(new BigDecimal(-1));
		Product product = new Product();
		product.setId(1);
		product.setName("Some Movie");
		product.setCurrentPrice(price);

		doThrow(new IllegalArgumentException("price is invalid")).when(productService)
				.saveProduct(anyLong(), any());

		mockMvc.perform(put("/products/1").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(product)))
				.andExpect(status().isNoContent()).andExpect(jsonPath("$").doesNotExist());

		ArgumentCaptor<Long> arg0 = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<Product> arg1 = ArgumentCaptor.forClass(Product.class);
		verify(productService).saveProduct(arg0.capture(), arg1.capture());
		assertEquals(1L, arg0.getValue());
		assertEquals(1L, arg1.getValue().getId());
		assertEquals("Some Movie", arg1.getValue().getName());
		assertEquals("USD", arg1.getValue().getCurrentPrice().getCurrencyCode());
		assertEquals(0, arg1.getValue().getCurrentPrice().getValue().compareTo(new BigDecimal(-1)));
	}

	@Test
	public void testPutProduct_OtherError() throws Exception {
		ProductPrice price = new ProductPrice();
		price.setCurrencyCode("USD");
		price.setValue(new BigDecimal(9.99));
		Product product = new Product();
		product.setId(1);
		product.setName("Some Movie");
		product.setCurrentPrice(price);

		doThrow(new NullPointerException("should not happen but test for completion")).when(productService)
				.saveProduct(anyLong(), any());

		mockMvc.perform(put("/products/1").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(product)))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$").doesNotExist());

		ArgumentCaptor<Long> arg0 = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<Product> arg1 = ArgumentCaptor.forClass(Product.class);
		verify(productService).saveProduct(arg0.capture(), arg1.capture());
		assertEquals(1L, arg0.getValue());
		assertEquals(1L, arg1.getValue().getId());
		assertEquals("Some Movie", arg1.getValue().getName());
		assertEquals("USD", arg1.getValue().getCurrentPrice().getCurrencyCode());
		assertEquals(0, arg1.getValue().getCurrentPrice().getValue().compareTo(new BigDecimal(9.99)));
	}

	@Test
	public void testPutProduct_Pass() throws Exception {
		ProductPrice price = new ProductPrice();
		price.setCurrencyCode("USD");
		price.setValue(new BigDecimal(9.99));
		Product product = new Product();
		product.setId(1);
		product.setName("Some Movie");
		product.setCurrentPrice(price);

		doReturn(product).when(productService).saveProduct(anyLong(), any());

		mockMvc.perform(put("/products/1").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(product)))
				.andExpect(status().isOk());

		ArgumentCaptor<Long> arg0 = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<Product> arg1 = ArgumentCaptor.forClass(Product.class);
		verify(productService).saveProduct(arg0.capture(), arg1.capture());
		assertEquals(1L, arg0.getValue());
		assertEquals(1L, arg1.getValue().getId());
		assertEquals("Some Movie", arg1.getValue().getName());
		assertEquals("USD", arg1.getValue().getCurrentPrice().getCurrencyCode());
		assertEquals(0, arg1.getValue().getCurrentPrice().getValue().compareTo(new BigDecimal(9.99)));
	}
}
