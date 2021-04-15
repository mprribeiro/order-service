package com.mprribeiro.orderservice.entities.enums;

public enum Status {
	APROVADO("APROVADO"), 
	APROVADO_VALOR_A_MENOR("APROVADO_VALOR_A_MENOR"),
	APROVADO_QTD_A_MENOR("APROVADO_QTD_A_MENOR"),
	APROVADO_VALOR_A_MAIOR("APROVADO_VALOR_A_MAIOR"),
	APROVADO_QTD_A_MAIOR("APROVADO_QTD_A_MAIOR"),
	CODIGO_PEDIDO_INVALIDO("CODIGO_PEDIDO_INVALIDO"),
	REPROVADO("REPROVADO"),
	STATUS_INVALIDO("STATUS_INVALIDO");
	
	private String descricao;

    Status (String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
