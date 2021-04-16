package com.mprribeiro.orderservice.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mprribeiro.orderservice.dto.PedidoDTO;
import com.mprribeiro.orderservice.entities.Pedido;
import com.mprribeiro.orderservice.services.PedidoService;

@RestController
@RequestMapping(value = "/api/pedido")
public class PedidoResource {
	
	@Autowired
	private PedidoService pedidoService;

	@RequestMapping(value="/{pedido}", method=RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable String pedido) {
		Pedido obj = pedidoService.find(pedido);
		return ResponseEntity.ok(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody PedidoDTO pedidoDTO) {
		Pedido pedido = pedidoService.fromDTO(pedidoDTO);
		pedido = pedidoService.insert(pedido);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{pedido}").buildAndExpand(pedido.getPedido()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{pedido}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String pedido) {
		pedidoService.delete(pedido);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{pedido}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody PedidoDTO pedidoDTO, @PathVariable String pedido) {
		Pedido obj = pedidoService.fromDTO(pedidoDTO);
		obj = pedidoService.update(obj);
		return ResponseEntity.noContent().build();
	}	
}
