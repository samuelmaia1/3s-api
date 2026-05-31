package com._s.api.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CpfTest {

    @Test
    void deveCriarCpfComValorNormalizado() {
        Cpf cpf = new Cpf("12345678901");
        assertEquals("12345678901", cpf.getValue());
    }

    @Test
    void deveCriarCpfAPartirDeStringFormatada() {
        Cpf cpf = new Cpf("123.456.789-01");
        assertEquals("12345678901", cpf.getValue());
    }

    @Test
    void deveRetornarCpfFormatado() {
        Cpf cpf = new Cpf("12345678901");
        assertEquals("123.456.789-01", cpf.getFormatted());
    }

    @Test
    void deveLancarExcecaoParaCpfComMenosDe11Digitos() {
        assertThrows(IllegalArgumentException.class, () -> new Cpf("1234567890"));
    }

    @Test
    void deveLancarExcecaoParaCpfComMaisDe11Digitos() {
        assertThrows(IllegalArgumentException.class, () -> new Cpf("123456789012"));
    }

    @Test
    void deveLancarExcecaoParaCpfVazio() {
        assertThrows(IllegalArgumentException.class, () -> new Cpf(""));
    }

    @Test
    void deveLancarExcecaoParaCpfSomenteLetras() {
        assertThrows(IllegalArgumentException.class, () -> new Cpf("abcdefghijk"));
    }

    @Test
    void doisCpfsComMesmoValorDevemSerIguais() {
        Cpf cpf1 = new Cpf("12345678901");
        Cpf cpf2 = new Cpf("123.456.789-01");
        assertEquals(cpf1, cpf2);
    }

    @Test
    void doisCpfsComValoresDiferentesNaoDevemSerIguais() {
        Cpf cpf1 = new Cpf("12345678901");
        Cpf cpf2 = new Cpf("98765432100");
        assertNotEquals(cpf1, cpf2);
    }

    @Test
    void cpfComMesmoValorDeveTermesmoHashCode() {
        Cpf cpf1 = new Cpf("12345678901");
        Cpf cpf2 = new Cpf("123.456.789-01");
        assertEquals(cpf1.hashCode(), cpf2.hashCode());
    }

    @Test
    void toStringSdeveRetornarCpfFormatado() {
        Cpf cpf = new Cpf("12345678901");
        assertEquals("123.456.789-01", cpf.toString());
    }

    @Test
    void cpfNaoDeveSerIgualANull() {
        Cpf cpf = new Cpf("12345678901");
        assertNotEquals(null, cpf);
    }
}
