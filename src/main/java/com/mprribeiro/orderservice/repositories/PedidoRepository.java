package com.mprribeiro.orderservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mprribeiro.orderservice.entities.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, String>{

	public Optional<Pedido> findByPedido(String pedido);
	public void deleteByPedido(String pedido);
	public Boolean existsByPedido(String pedido);
}
