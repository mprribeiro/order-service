package com.mprribeiro.orderservice.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.mprribeiro.orderservice.entities.enums.Status;

public class StatusResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String pedido;
	private Set<Status> statuses = new HashSet<>();
	
	public StatusResponse() {}
	
	public StatusResponse(String pedido) {
		this.pedido = pedido;
	}

	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}

	public Set<Status> getStatuses() {
		return statuses;
	}

	public void addStatus(Status status) {
		statuses.add(status);
	}
	
	public void removeStatus(Status status) {
		statuses.remove(status);
	}
	
}
