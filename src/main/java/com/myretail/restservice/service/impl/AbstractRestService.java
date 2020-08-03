package com.myretail.restservice.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractRestService<DTO> {

	/**
	 * Abstract method for inherited class to override. This sets the object for
	 * which the get call is requesting
	 *
	 * @return class of the set entity type
	 */
	public abstract Class<DTO> getDTOClass();

	/**
	 * Gets entity for a given url.
	 *
	 * @param url string to make get request to
	 * @return response entity containing the object
	 */
	public ResponseEntity<DTO> get(String url) {
		final RestTemplate get = new RestTemplate();
		final ResponseEntity<DTO> response = get.getForEntity(url, getDTOClass());
		return response;
	}

	/**
	 * Posts entity to a given url.
	 *
	 * @param url     string to post entity to
	 * @param request object to post
	 * @return Response entity containing returned posted object
	 */
	public ResponseEntity<DTO> post(String url, DTO request) {
		final RestTemplate post = new RestTemplate();
		final ResponseEntity<DTO> response = post.postForEntity(url, request, getDTOClass());
		return response;
	}

	/**
	 * Put entity to a given url.
	 *
	 * @param url     string to put entity
	 * @param request entity to put
	 */
	public void put(String url, DTO request) {
		final RestTemplate put = new RestTemplate();
		put.put(url, request);
	}

	/**
	 * Delete request to a given url.
	 *
	 * @param url string to send delete request
	 */
	public void delete(String url) {
		final RestTemplate delete = new RestTemplate();
		delete.delete(url);
	}
}
