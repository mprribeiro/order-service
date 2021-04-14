package com.mprribeiro.orderservice.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mprribeiro.orderservice.entities.CEP;

@RestController
@RequestMapping(value = "/api/consultacep")
public class CEPResource {
	
	RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping(value="/{cep}", method=RequestMethod.GET)
	public ResponseEntity<CEP> find(@PathVariable String cep) {
		CEP cepFound = restTemplate.getForObject("http://www.viacep.com.br/ws/" + cep + "/json/", CEP.class);
		return ResponseEntity.ok(cepFound);
	}

}
