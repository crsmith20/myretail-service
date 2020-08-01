package com.myretail.restservice.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractRestService<DTO> {

	public abstract Class<DTO> getDTOClass();

	public ResponseEntity<DTO> get(String url) {
		final RestTemplate get = new RestTemplate();
		final ResponseEntity<DTO> response = get.getForEntity(url, getDTOClass());
		return response;
	}

	public ResponseEntity<DTO> post(String url, DTO request) {
		final RestTemplate post = new RestTemplate();
		final ResponseEntity<DTO> response = post.postForEntity(url, request, getDTOClass());
		return response;
	}

	public void put(String url, DTO request) {
		final RestTemplate put = new RestTemplate();
		put.put(url, request);
	}

	public void delete(String url) {
		final RestTemplate delete = new RestTemplate();
		delete.delete(url);
	}
}
