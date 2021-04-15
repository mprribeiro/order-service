package com.mprribeiro.orderservice.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

public class PedidoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty
	private String pedido;
	
	private List<ItemDTO> itens = new ArrayList<>();

	public PedidoDTO() {}
	
	public PedidoDTO(String pedido) {
		this.pedido = pedido;
	}
	
	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}
	
	public List<ItemDTO> getItens() {
		return itens;
	}

	public void addItem(ItemDTO item) {
		itens.add(item);
	}
	
	public void removeItem(ItemDTO item) {
		itens.remove(item);
	}

}
