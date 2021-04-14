package com.mprribeiro.orderservice.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mprribeiro.orderservice.dto.ItemDTO;
import com.mprribeiro.orderservice.dto.PedidoDTO;
import com.mprribeiro.orderservice.entities.Item;
import com.mprribeiro.orderservice.entities.Pedido;
import com.mprribeiro.orderservice.repositories.PedidoRepository;
import com.mprribeiro.orderservice.services.exception.DataIntegrityException;
import com.mprribeiro.orderservice.services.exception.ObjectAlreadyExistsException;
import com.mprribeiro.orderservice.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	public PedidoService(PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
	}
	
	public Pedido find(String pedido) {
		Optional<Pedido> obj = pedidoRepository.findByPedido(pedido);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + pedido + ", Type: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido pedido) {
		if (pedidoRepository.existsByPedido(pedido.getPedido())) {
			throw new ObjectAlreadyExistsException("Pedido já cadastrado!");
		}
		
		return pedidoRepository.save(pedido);
	}
	
	@Transactional
	public void delete(String pedido) {
		find(pedido);
		try {
			pedidoRepository.deleteByPedido(pedido);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("It's not possible to delete because there are entities related to!");
		}
	}
	
	@Transactional
	public Pedido update(Pedido pedido) {
		Pedido newPedido = find(pedido.getPedido());
		updateData(newPedido, pedido);
		newPedido =  pedidoRepository.save(newPedido);
		return newPedido;
	}
	
	public void updateData(Pedido newPedido, Pedido pedido) {
		pedido.getItens().forEach(item -> {
			if (!newPedido.getItens().contains(item)) {
				newPedido.getItens().add(item);
			} else {
				for(Item x : newPedido.getItens()) {
					if (item.equals(x)) {
						x.setQtd(item.getQtd());
						x.setPrecoUnitario(item.getPrecoUnitario());
					}
				}
			}
		});
		
		newPedido.getItens().forEach(item -> {
			if (!pedido.getItens().contains(item)) {
				newPedido.getItens().remove(item);
			}
		});
		
		for (Item x : newPedido.getItens()) {
			x.setPedido(newPedido);
		}
	}
	
	public Pedido fromDTO(PedidoDTO pedidoDTO) {
		Pedido pedido = new Pedido(null, pedidoDTO.getPedido());
		
		List<ItemDTO> itensDTO = pedidoDTO.getItens();
		List<Item> itens = itensDTO.stream().map(x -> new Item(null, pedido, x.getDescricao(), x.getPrecoUnitario(), x.getQtd())).collect(Collectors.toList());
		
		itens.forEach(item -> pedido.addItem(item));
		
		return pedido;
	}
}
