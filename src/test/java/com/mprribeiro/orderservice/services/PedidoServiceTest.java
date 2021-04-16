package com.mprribeiro.orderservice.services;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mprribeiro.orderservice.entities.Item;
import com.mprribeiro.orderservice.entities.Pedido;
import com.mprribeiro.orderservice.repositories.PedidoRepository;
import com.mprribeiro.orderservice.services.exception.ObjectAlreadyExistsException;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PedidoServiceTest {

	PedidoService service;

	@MockBean
	PedidoRepository repository;

	@BeforeEach
	public void setUp() {
		this.service = new PedidoService(repository);
	}
	
	private Pedido createNewPedido() {
		Pedido pedido = new Pedido(1, "123456");
        Item item1 = new Item(1, pedido, "Item A", 5.0, 2);
        Item item2 = new Item(2, pedido, "Item B", 8.0, 7);
        pedido.addItem(item1);
        pedido.addItem(item2);
        
        return pedido;
    }
	
	@Test
    @DisplayName("Deve salvar o pedido")
    public void saveOrderTest() {
		Pedido pedido = createNewPedido();
		
        Mockito.when(repository.save(Mockito.any(Pedido.class))).thenReturn(pedido);

        Pedido pedidoSalvo = service.insert(pedido);

        Assertions.assertThat(pedidoSalvo.getId()).isNotNull();
        Assertions.assertThat(pedidoSalvo.getPedido()).isEqualTo("123456");
    }
	
	@Test
    @DisplayName("Deve lançar erro de negócio ao tentar salvar pedido com código repetido")
    public void shouldNotSaveOrder() {
		Pedido pedido = createNewPedido();
        String messageError = "Pedido já cadastrado!";
        Mockito.when(repository.existsByPedido(Mockito.anyString())).thenReturn(true);

        Throwable ex = Assertions.catchThrowable(() -> service.insert(pedido));

        Assertions.assertThat(ex).isInstanceOf(ObjectAlreadyExistsException.class).hasMessage(messageError);
        Mockito.verify(repository, Mockito.never()).save(pedido);
    }
	
	@Test
    @DisplayName("Deve obter o pedido pelo campo pedido")
    public void getByOrderTest() {
        String ped = "123456";

        Pedido pedido = createNewPedido();
        Mockito.when(repository.findByPedido(ped)).thenReturn(Optional.of(pedido));

        Pedido pedidoEncontrado =  service.find(ped);

        Assertions.assertThat(pedidoEncontrado.getPedido()).isEqualTo(ped);
    }
	
	@Test
    @DisplayName("Deve atualizar um pedido com sucesso")
    public void updateOrderTest() {	
        Pedido pedidoParaAtualizar = new Pedido(1, "123456");
        Pedido pedidoAtualizado = createNewPedido();

        Mockito.when(repository.findByPedido(Mockito.anyString())).thenReturn(Optional.of(pedidoParaAtualizar));
        Mockito.when(repository.save(Mockito.any(Pedido.class))).thenReturn(pedidoAtualizado);
        Pedido pedido = service.update(pedidoParaAtualizar);

        Assertions.assertThat(pedido.getId()).isEqualTo(pedidoAtualizado.getId());
        Assertions.assertThat(pedido.getPedido()).isEqualTo(pedidoAtualizado.getPedido());
    }
}
