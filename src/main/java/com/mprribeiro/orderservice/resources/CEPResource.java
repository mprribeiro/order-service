package com.mprribeiro.orderservice.resources;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mprribeiro.orderservice.entities.CEP;
import com.mprribeiro.orderservice.services.exception.ObjectNotFoundException;

@RestController
@RequestMapping(value = "/api/consultacep")
public class CEPResource {
	
	RestTemplate restTemplate = new RestTemplate();
	
	@Cacheable("ceps")
	@RequestMapping(value="/{cep}", method=RequestMethod.GET)
	public ResponseEntity<CEP> find(@PathVariable String cep) {
		CEP cepFound = restTemplate.getForObject("http://www.viacep.com.br/ws/" + cep + "/json/", CEP.class);
		
		if (cepFound.getLogradouro() == null) {
			throw new ObjectNotFoundException("CEP não encontrado.");
		}
		
		return ResponseEntity.ok(cepFound);
	}

}
