package com.myretail.restservice.service;

import org.springframework.http.ResponseEntity;

public interface RestService<DTO> {

	ResponseEntity<DTO> get();

	ResponseEntity<DTO> post(DTO dto);

	ResponseEntity<DTO> put(DTO dto);

	ResponseEntity<DTO> patch(DTO dto);

	ResponseEntity<DTO> delete(DTO dto);
}
