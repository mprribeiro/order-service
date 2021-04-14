package com.mprribeiro.orderservice.entities;

import java.io.Serializable;

public class StatusPayload implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String status;
	private Integer itensAprovados;
	private Double valorAprovado;
	private String pedido;
	
	public StatusPayload() {}

	public StatusPayload(String status, Integer itensAprovados, Double valorAprovado, String pedido) {
		this.status = status;
		this.itensAprovados = itensAprovados;
		this.valorAprovado = valorAprovado;
		this.pedido = pedido;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getItensAprovados() {
		return itensAprovados;
	}

	public void setItensAprovados(Integer itensAprovados) {
		this.itensAprovados = itensAprovados;
	}

	public Double getValorAprovado() {
		return valorAprovado;
	}

	public void setValorAprovado(Double valorAprovado) {
		this.valorAprovado = valorAprovado;
	}

	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}
}
