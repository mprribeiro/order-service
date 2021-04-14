package com.mprribeiro.orderservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mprribeiro.orderservice.entities.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{

	public Item findByDescricao(String descricao);
}
