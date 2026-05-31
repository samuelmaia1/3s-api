package com._s.api.domain.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void deveCriarEnderecoComTodosOsCampos() {
        Address address = Address.mount("30130-010", "Rua das Flores", "Centro", "Belo Horizonte", "100");

        assertEquals("30130-010", address.getCep());
        assertEquals("Rua das Flores", address.getStreet());
        assertEquals("Centro", address.getNeighborhood());
        assertEquals("Belo Horizonte", address.getCity());
        assertEquals("100", address.getNumber());
    }

    @Test
    void deveCriarEnderecoComCamposNulos() {
        Address address = Address.mount(null, null, null, null, null);

        assertNull(address.getCep());
        assertNull(address.getStreet());
        assertNull(address.getNeighborhood());
        assertNull(address.getCity());
        assertNull(address.getNumber());
    }

    @Test
    void doisEnderecosMontadosComMesmosValoresDevemTerCamposIguais() {
        Address a1 = Address.mount("01310-100", "Av. Paulista", "Bela Vista", "São Paulo", "1000");
        Address a2 = Address.mount("01310-100", "Av. Paulista", "Bela Vista", "São Paulo", "1000");

        assertEquals(a1.getCep(), a2.getCep());
        assertEquals(a1.getStreet(), a2.getStreet());
        assertEquals(a1.getCity(), a2.getCity());
    }
}
