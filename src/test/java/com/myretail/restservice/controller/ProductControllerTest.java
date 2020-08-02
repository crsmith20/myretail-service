package com.myretail.restservice.controller;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
	public void testGetProduct_NoId() {
		fail("not implemented yet");
	}

	@Test
	public void testGetProduct_IdNotFound() {
		fail("not implemented yet");
	}

	@Test
	public void testGetProduct_OtherError() {
		fail("not implemented yet");
	}

	@Test
	public void testGetProduct_Pass() {
		fail("not implemented yet");
	}

	@Test
	public void testPutProduct_NoId() {
		fail("not implemeneted yet");
	}

	@Test
	public void testPutProduct_NoProduct() {
		fail("not implemeneted yet");
	}

	@Test
	public void testPutProduct_IllegalId() {
		fail("not implemeneted yet");
	}

	@Test
	public void testPutProduct_IllegalPrice() {
		fail("not implemeneted yet");
	}

	@Test
	public void testPutProduct_OtherError() {
		fail("not implemeneted yet");
	}

	@Test
	public void testPutProduct_Pass() {
		fail("not implemeneted yet");
	}
}
