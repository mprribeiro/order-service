package com.mprribeiro.orderservice.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mprribeiro.orderservice.entities.Pedido;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class PedidoRepositoryTest {

	@Autowired
    TestEntityManager entityManager;

    @Autowired
    PedidoRepository repository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir o pedido na base com o código informado")
    public void returnTrueWhenOrderExists () {
        String ped = "123456";
        Pedido pedido = new Pedido(null, "123456");
        entityManager.persist(pedido);

        boolean orderExists = repository.existsByPedido(ped);

        Assertions.assertThat(orderExists).isTrue();
    }
    
    @Test
    @DisplayName("Deve retornar falso quando não existir um pedido na base com o código informado")
    public void returnFalseWhenOrderDoesntExist () {
    	String ped = "123456";

    	boolean orderExists = repository.existsByPedido(ped);

    	Assertions.assertThat(orderExists).isFalse();
    }
    
    
}
