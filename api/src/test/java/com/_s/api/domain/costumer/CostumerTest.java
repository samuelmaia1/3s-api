package com._s.api.domain.costumer;

import com._s.api.domain.costumer.service.CreateCostumerCommand;
import com._s.api.domain.order.Order;
import com._s.api.domain.order.OrderStatus;
import com._s.api.domain.rent.Rent;
import com._s.api.domain.rent.RentStatus;
import com._s.api.domain.shared.Address;
import com._s.api.domain.valueobject.Cpf;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CostumerTest {

    private Address buildAddress() {
        return Address.mount("30130-010", "Rua A", "Centro", "BH", "10");
    }

    @Test
    void deveCriarClienteAPartirDeCommand() {
        CreateCostumerCommand command = new CreateCostumerCommand(
                "Maria", "Souza", "maria@email.com", "12345678901", buildAddress()
        );
        Costumer costumer = new Costumer(command, "user-1");

        assertEquals("Maria", costumer.getName());
        assertEquals("Souza", costumer.getLastName());
        assertEquals("maria@email.com", costumer.getEmail());
        assertEquals("user-1", costumer.getUserId());
        assertNotNull(costumer.getCpf());
    }

    @Test
    void deveAdicionarPedidoAoCliente() {
        CreateCostumerCommand command = new CreateCostumerCommand(
                "João", "Silva", "joao@email.com", "98765432100", buildAddress()
        );
        Costumer costumer = new Costumer(command, "user-1");

        Order order = new Order("user-1", "c-1", OrderStatus.REALIZADO, new ArrayList<>(),
                buildAddress(), LocalDateTime.now(), LocalDateTime.now().plusDays(5));
        costumer.addOrder(order);

        assertEquals(1, costumer.getOrders().size());
    }

    @Test
    void deveAdicionarAluguelAoCliente() {
        CreateCostumerCommand command = new CreateCostumerCommand(
                "Ana", "Costa", "ana@email.com", "11122233344", buildAddress()
        );
        Costumer costumer = new Costumer(command, "user-1");

        Rent rent = new Rent("user-1", "c-1", RentStatus.REALIZADO, new ArrayList<>(),
                BigDecimal.ZERO, buildAddress(), LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        costumer.addRent(rent);

        assertEquals(1, costumer.getRents().size());
    }

    @Test
    void deveMontarClienteComTodosOsCampos() {
        LocalDateTime now = LocalDateTime.now();
        Cpf cpf = new Cpf("12345678901");

        Costumer costumer = Costumer.mount(
                "c-1", "user-1", "Pedro", "Alves", "pedro@email.com",
                cpf, now, now, null, null, buildAddress()
        );

        assertEquals("c-1", costumer.getId());
        assertEquals("user-1", costumer.getUserId());
        assertEquals("Pedro", costumer.getName());
        assertEquals("pedro@email.com", costumer.getEmail());
        assertEquals(cpf, costumer.getCpf());
    }

    @Test
    void deveMontarClienteComPedidosERents() {
        Cpf cpf = new Cpf("12345678901");
        Order order = new Order("u-1", "c-1", OrderStatus.REALIZADO, new ArrayList<>(),
                buildAddress(), LocalDateTime.now(), LocalDateTime.now().plusDays(5));
        Rent rent = new Rent("u-1", "c-1", RentStatus.REALIZADO, new ArrayList<>(),
                BigDecimal.ZERO, buildAddress(), LocalDateTime.now(), LocalDateTime.now().plusDays(7));

        Costumer costumer = Costumer.mount(
                "c-1", "u-1", "Lucia", "Lima", "lucia@email.com",
                cpf, LocalDateTime.now(), LocalDateTime.now(), List.of(order), List.of(rent), buildAddress()
        );

        assertEquals(1, costumer.getOrders().size());
        assertEquals(1, costumer.getRents().size());
    }

    @Test
    void toStringDeveConterNome() {
        CreateCostumerCommand command = new CreateCostumerCommand(
                "Carlos", "Melo", "carlos@email.com", "55566677788", buildAddress()
        );
        Costumer costumer = new Costumer(command, "user-1");

        assertTrue(costumer.toString().contains("Carlos"));
    }
}
