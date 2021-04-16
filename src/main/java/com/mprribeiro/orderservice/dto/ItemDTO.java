package com.mprribeiro.orderservice.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class ItemDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String descricao;
	
	@NotNull
	private Double precoUnitario;
	
	@NotNull
	private Integer qtd;
	
	public ItemDTO() {	}
	
	public ItemDTO(String descricao, Double precoUnitario, Integer qtd) {
		this.descricao = descricao;
		this.precoUnitario = precoUnitario;
		this.qtd = qtd;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(Double precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public Integer getQtd() {
		return qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}
	
}
