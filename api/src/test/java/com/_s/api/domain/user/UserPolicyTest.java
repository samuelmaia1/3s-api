package com._s.api.domain.user;

import com._s.api.domain.user.exception.EmailAlreadyInUseException;
import com._s.api.domain.user.exception.IdentityAlreadyInUseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserPolicyTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserPolicy policy;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = mock(User.class);
    }

    @Test
    void validateEmailIsUnique_devePassarQuandoEmailNaoExiste() {
        when(repository.findByEmail("novo@email.com")).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> policy.validateEmailIsUnique("novo@email.com"));
    }

    @Test
    void validateEmailIsUnique_deveLancarExcecaoQuandoEmailJaExiste() {
        when(repository.findByEmail("existente@email.com")).thenReturn(Optional.of(mockUser));
        assertThrows(EmailAlreadyInUseException.class,
                () -> policy.validateEmailIsUnique("existente@email.com"));
    }

    @Test
    void validateEmailIsUnique_mensagemDeveConterEmail() {
        when(repository.findByEmail("usado@email.com")).thenReturn(Optional.of(mockUser));
        EmailAlreadyInUseException ex = assertThrows(EmailAlreadyInUseException.class,
                () -> policy.validateEmailIsUnique("usado@email.com"));
        assertTrue(ex.getMessage().contains("usado@email.com"));
    }

    @Test
    void validateIdentityIsUnique_devePassarQuandoCpfNaoExiste() {
        when(repository.findByCpf("12345678901")).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> policy.validateIdentityIsUnique("12345678901"));
    }

    @Test
    void validateIdentityIsUnique_deveLancarExcecaoQuandoCpfJaExiste() {
        when(repository.findByCpf("12345678901")).thenReturn(Optional.of(mockUser));
        assertThrows(IdentityAlreadyInUseException.class,
                () -> policy.validateIdentityIsUnique("12345678901"));
    }

    @Test
    void validateIdentityIsUnique_mensagemDeveConterCpf() {
        when(repository.findByCpf("98765432100")).thenReturn(Optional.of(mockUser));
        IdentityAlreadyInUseException ex = assertThrows(IdentityAlreadyInUseException.class,
                () -> policy.validateIdentityIsUnique("98765432100"));
        assertTrue(ex.getMessage().contains("98765432100"));
    }
}
