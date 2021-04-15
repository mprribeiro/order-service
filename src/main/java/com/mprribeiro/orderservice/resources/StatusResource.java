package com.mprribeiro.orderservice.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mprribeiro.orderservice.entities.Pedido;
import com.mprribeiro.orderservice.entities.StatusPayload;
import com.mprribeiro.orderservice.entities.StatusResponse;
import com.mprribeiro.orderservice.entities.enums.Status;
import com.mprribeiro.orderservice.services.PedidoService;
import com.mprribeiro.orderservice.services.StatusService;
import com.mprribeiro.orderservice.services.exception.ObjectNotFoundException;

@RestController
@RequestMapping(value = "/api/status")
public class StatusResource {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private StatusService statusService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<StatusResponse> getStatus(@RequestBody @Valid StatusPayload status) {
		Pedido pedido = new Pedido();
		StatusResponse statusRes = new StatusResponse(status.getPedido());
		
		if (status.getStatus().equalsIgnoreCase(Status.REPROVADO.getDescricao())) {
			System.out.println("Entrei REPROVADO!");
			statusRes.addStatus(Status.REPROVADO);
			return ResponseEntity.ok(statusRes);
		}

		try {
			pedido = pedidoService.find(status.getPedido());
		} catch (ObjectNotFoundException e) {
			statusRes.addStatus(Status.CODIGO_PEDIDO_INVALIDO);
			return ResponseEntity.ok(statusRes);
		}

		if (status.getStatus().equalsIgnoreCase(Status.APROVADO.getDescricao())) {
			statusRes = statusService.verifyTotalItems(status, pedido, statusRes);
			statusRes = statusService.verifyTotalPrice(status, pedido, statusRes);

			if (statusRes.getStatuses().isEmpty()) {
				statusRes.addStatus(Status.APROVADO);
			}
			
			return ResponseEntity.ok(statusRes);
		}

		statusRes.addStatus(Status.STATUS_INVALIDO);
		return ResponseEntity.ok(statusRes);

	}

}
