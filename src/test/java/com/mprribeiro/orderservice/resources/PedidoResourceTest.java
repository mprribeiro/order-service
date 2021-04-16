package com.mprribeiro.orderservice.resources;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mprribeiro.orderservice.dto.ItemDTO;
import com.mprribeiro.orderservice.dto.PedidoDTO;
import com.mprribeiro.orderservice.entities.Item;
import com.mprribeiro.orderservice.entities.Pedido;
import com.mprribeiro.orderservice.services.PedidoService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = PedidoResource.class)
@AutoConfigureMockMvc
public class PedidoResourceTest {

	static String PEDIDO_API = "/api/pedido";

	@Autowired
	MockMvc mvc;

	@MockBean
	PedidoService service;

	private Pedido createNewPedido() {
		Pedido pedido = new Pedido(1, "123456");
		Item item1 = new Item(1, pedido, "Item A", 5.0, 2);
		Item item2 = new Item(2, pedido, "Item B", 8.0, 7);
		pedido.addItem(item1);
		pedido.addItem(item2);

		return pedido;
	}

	private PedidoDTO createNewPedidoDTO() {
		PedidoDTO pedidoDTO = new PedidoDTO("123456");
		ItemDTO it1 = new ItemDTO("Item A", 5.0, 2);
		ItemDTO it2 = new ItemDTO("Item B", 10.0, 1);
		pedidoDTO.addItem(it1);
		pedidoDTO.addItem(it2);

		return pedidoDTO;
	}

	@Test
	@DisplayName("Deve criar um pedido com sucesso")
	public void createOrderTest() throws Exception {

		PedidoDTO pedidoDTO = createNewPedidoDTO();
		Pedido pedidoSalvo = createNewPedido();

		BDDMockito.given(service.fromDTO(Mockito.any(PedidoDTO.class))).willReturn(new Pedido(null, "123456"));
		BDDMockito.given(service.insert(Mockito.any(Pedido.class))).willReturn(pedidoSalvo);
		String json = new ObjectMapper().writeValueAsString(pedidoDTO);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(PEDIDO_API)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(
				MockMvcResultMatchers.redirectedUrl("http://localhost/api/pedido/" + pedidoSalvo.getPedido()));
	}

	@Test
	@DisplayName("Deve lançar erro de validação quando não houver dados suficientes para criação do pedido")
	public void createInvalidOrderTest() throws Exception {

		String json = new ObjectMapper().writeValueAsString(new PedidoDTO());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(PEDIDO_API)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
    @DisplayName("Deve retornar um pedido")
    public void getOrderDetails() throws Exception {
        String ped = "123456";
        Pedido pedido = createNewPedido();
        BDDMockito.given(service.find(ped)).willReturn(pedido);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PEDIDO_API.concat("/" + ped))
                .accept(MediaType.APPLICATION_JSON);

        mvc.
                perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("pedido").value(ped))
                .andExpect(MockMvcResultMatchers.jsonPath("itens").isArray());
    }
	
	@Test
    @DisplayName("Deletar pedido com sucesso")
    public void deleteOrderTest() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(PEDIDO_API.concat("/" + "123456"));

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
	
	
	@Test
    @DisplayName("Deve atualizar os dados de um pedido")
    public void updateOrderTest() throws Exception {
		String ped = "123456";
        String json = new ObjectMapper().writeValueAsString(createNewPedido());
        Pedido pedidoAtualizado = createNewPedido();

        BDDMockito
                .given(service.find(ped))
                .willReturn(pedidoAtualizado);
        BDDMockito
                .given(service.update(pedidoAtualizado))
                .willReturn(pedidoAtualizado);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(PEDIDO_API.concat("/" + ped))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
	


}
