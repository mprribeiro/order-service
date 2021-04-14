package com.mprribeiro.orderservice.services;

import org.springframework.stereotype.Service;

import com.mprribeiro.orderservice.entities.Item;
import com.mprribeiro.orderservice.entities.Pedido;
import com.mprribeiro.orderservice.entities.StatusPayload;
import com.mprribeiro.orderservice.entities.StatusResponse;
import com.mprribeiro.orderservice.entities.enums.Status;

@Service
public class StatusService {
	
	public StatusResponse verifyTotalItems(StatusPayload status, Pedido pedido, StatusResponse statusRes) {
		Integer pedidoQtd = pedido.getItens().stream().map(Item::getQtd).reduce(0, (a,b)-> a+b);
		
		if (status.getItensAprovados() < pedidoQtd) {
			statusRes.addStatus(Status.APROVADO_QTD_A_MENOR);
		} else if (status.getItensAprovados() > pedidoQtd) {
			statusRes.addStatus(Status.APROVADO_QTD_A_MAIOR);
		}
		
		return statusRes;
	}
	
	public StatusResponse verifyTotalPrice(StatusPayload status, Pedido pedido, StatusResponse statusRes) {
		Double pedidoPrice = 0.0;
		for (Item x : pedido.getItens()) {
			pedidoPrice += x.getQtd() * x.getPrecoUnitario();
		}
		
		if (status.getValorAprovado() < pedidoPrice) {
			statusRes.addStatus(Status.APROVADO_VALOR_A_MENOR);
		} else if (status.getValorAprovado() > pedidoPrice) {
			statusRes.addStatus(Status.APROVADO_VALOR_A_MAIOR);
		}
		
		return statusRes;
	}

}
