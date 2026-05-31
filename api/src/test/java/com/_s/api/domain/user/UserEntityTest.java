package com._s.api.domain.user;

import com._s.api.domain.contract.Contract;
import com._s.api.domain.contract.ContractReferenceType;
import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.costumer.service.CreateCostumerCommand;
import com._s.api.domain.order.Order;
import com._s.api.domain.order.OrderStatus;
import com._s.api.domain.rent.Rent;
import com._s.api.domain.rent.RentStatus;
import com._s.api.domain.shared.Address;
import com._s.api.domain.user.service.CreateUserCommand;
import com._s.api.domain.user.service.UpdateUserCommand;
import com._s.api.domain.valueobject.Cpf;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    private Address buildAddress() {
        return Address.mount("30130-010", "Rua A", "Centro", "BH", "10");
    }

    private CreateUserCommand buildCommand() {
        return new CreateUserCommand(
                "João", "Silva", "joao@email.com",
                "12345678901", "senha123", buildAddress(), "João Pizzaria", "@joao", null
        );
    }

    @Test
    void deveCriarUsuarioAPartirDeCommand() {
        User user = new User(buildCommand());

        assertEquals("João", user.getName());
        assertEquals("Silva", user.getLastName());
        assertEquals("joao@email.com", user.getEmail());
        assertNotNull(user.getCpf());
        assertEquals("12345678901", user.getCpf().getValue());
        assertEquals("João Pizzaria", user.getSocialName());
        assertEquals("@joao", user.getInstagram());
    }

    @Test
    void deveAtualizarSenha() {
        User user = new User(buildCommand());
        user.updatePassword("nova_senha_encoded");
        assertEquals("nova_senha_encoded", user.getPassword());
    }

    @Test
    void deveAtualizarPerfilComTodosOsCampos() {
        User user = new User(buildCommand());
        UpdateUserCommand command = new UpdateUserCommand(
                null, "Maria", "Santos", "maria@email.com",
                "98765432100", null, "Maria Eventos", "@maria", "https://logo.com/img.png"
        );
        user.updateProfile(command);

        assertEquals("Maria", user.getName());
        assertEquals("Santos", user.getLastName());
        assertEquals("maria@email.com", user.getEmail());
        assertEquals("Maria Eventos", user.getSocialName());
        assertEquals("@maria", user.getInstagram());
        assertEquals("https://logo.com/img.png", user.getLogo());
    }

    @Test
    void deveManterCamposOriginaisComUpdateCommandNulo() {
        User user = new User(buildCommand());
        UpdateUserCommand command = new UpdateUserCommand(null, null, null, null, null, null, null, null, null);
        user.updateProfile(command);

        assertEquals("João", user.getName());
        assertEquals("Silva", user.getLastName());
        assertEquals("joao@email.com", user.getEmail());
    }

    @Test
    void deveAdicionarPedidoAoUsuario() {
        User user = new User(buildCommand());
        Order order = new Order("user-1", "c-1", OrderStatus.REALIZADO, new ArrayList<>(),
                buildAddress(), LocalDateTime.now(), LocalDateTime.now().plusDays(5));

        user.addOrder(order);

        assertEquals(1, user.getOrders().size());
    }

    @Test
    void deveAdicionarAluguelAoUsuario() {
        User user = new User(buildCommand());
        Rent rent = new Rent("user-1", "c-1", RentStatus.REALIZADO, new ArrayList<>(),
                BigDecimal.ZERO, buildAddress(), LocalDateTime.now(), LocalDateTime.now().plusDays(7));

        user.addRent(rent);

        assertEquals(1, user.getRents().size());
    }

    @Test
    void deveAdicionarClienteAoUsuario() {
        User user = new User(buildCommand());
        Costumer costumer = new Costumer(
                new CreateCostumerCommand("Ana", "Costa", "ana@email.com", "55566677788", buildAddress()),
                "user-1"
        );

        user.addCostumer(costumer);

        assertEquals(1, user.getCostumers().size());
    }

    @Test
    void deveAdicionarContratoAoUsuario() {
        User user = new User(buildCommand());
        Contract contract = new Contract("user-1", "c-1", "ref-1", ContractReferenceType.ORDER, new ArrayList<>());

        user.addContract(contract);

        assertEquals(1, user.getContracts().size());
    }

    @Test
    void deveMontarUsuarioComTodosOsCampos() {
        LocalDateTime now = LocalDateTime.now();
        Cpf cpf = new Cpf("12345678901");

        User user = User.mount(
                "u-1", "Pedro", "Alves", "pedro@email.com", cpf,
                "hash", now, now, List.of(), List.of(), buildAddress(),
                List.of(), List.of(), "Pedro Eventos", "@pedro", "https://logo.png"
        );

        assertEquals("u-1", user.getId());
        assertEquals("Pedro", user.getName());
        assertEquals("pedro@email.com", user.getEmail());
        assertEquals(cpf, user.getCpf());
        assertEquals("Pedro Eventos", user.getSocialName());
    }

    @Test
    void deveMontarUsuarioComListasNulas() {
        Cpf cpf = new Cpf("12345678901");
        User user = User.mount(
                "u-1", "Ana", "Lima", "ana@email.com", cpf,
                "hash", LocalDateTime.now(), LocalDateTime.now(),
                null, null, buildAddress(), null, null, null, null, null
        );

        assertNotNull(user.getOrders());
        assertNotNull(user.getRents());
        assertTrue(user.getOrders().isEmpty());
        assertTrue(user.getRents().isEmpty());
    }
}
