package com.javaegitimleri.petclinic.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.javaegitimleri.petclinic.model.Owner;

public class PetClinicRestControllerTests {
	private RestTemplate restTemplate;

	@Before
	public void setUp() {
		restTemplate = new RestTemplate();
	}

	@Test
	public void testGetOwnerById() {
		ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8080/rest/owner/1", Owner.class);
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("Kenan"));
	}

	@Test
	public void testGetOwnersByLastName() {
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owner?ln=Sevindik",
				List.class);

		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		List<Map<String, String>> body = response.getBody();

		List<String> firstNames = body.stream().map(e -> e.get("firstName")).collect(Collectors.toList());

		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Kenan", "Hümeyra", "Salim"));
	}
	
	@Test
	public void testGetOwners() {
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owners", List.class);
		List<Map<String,String>> body = response.getBody();

		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		
		List<String> firstNames = body.stream().map(e->e.get("firstName")).collect(Collectors.toList());
		
		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Kenan", "Hümeyra", "Salim", "Muammer"));
	}
}