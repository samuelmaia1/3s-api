package com._s.api.domain.user.service;

import com._s.api.domain.user.PasswordEncoder;
import com._s.api.domain.user.User;
import com._s.api.domain.user.UserPolicy;
import com._s.api.domain.user.UserRepository;
import com._s.api.domain.user.exception.EmailAlreadyInUseException;
import com._s.api.domain.user.exception.IdentityAlreadyInUseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserPolicy userPolicy;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserService service;

    private CreateUserCommand command;

    @BeforeEach
    void setUp() {
        command = new CreateUserCommand(
                "João", "Silva", "joao@email.com",
                "12345678901", "senha123", null, null, null, null
        );
    }

    @Test
    void deveExecutarComSucessoCriarUsuario() {
        when(passwordEncoder.encode("senha123")).thenReturn("hash_senha");
        User savedUser = mock(User.class);
        when(repository.save(any(User.class))).thenReturn(savedUser);

        User result = service.execute(command);

        assertNotNull(result);
        verify(userPolicy).validateEmailIsUnique("joao@email.com");
        verify(userPolicy).validateIdentityIsUnique("12345678901");
        verify(passwordEncoder).encode("senha123");
        verify(repository).save(any(User.class));
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaEstaEmUso() {
        doThrow(new EmailAlreadyInUseException("E-mail já em uso"))
                .when(userPolicy).validateEmailIsUnique(anyString());

        assertThrows(EmailAlreadyInUseException.class, () -> service.execute(command));
        verify(repository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoCpfJaEstaEmUso() {
        doNothing().when(userPolicy).validateEmailIsUnique(anyString());
        doThrow(new IdentityAlreadyInUseException("CPF já em uso"))
                .when(userPolicy).validateIdentityIsUnique(anyString());

        assertThrows(IdentityAlreadyInUseException.class, () -> service.execute(command));
        verify(repository, never()).save(any());
    }

    @Test
    void deveCodificarSenhaAntesDePersistar() {
        when(passwordEncoder.encode("senha123")).thenReturn("senha_encoded");
        when(repository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User result = service.execute(command);

        verify(passwordEncoder).encode("senha123");
        assertEquals("senha_encoded", result.getPassword());
    }

    @Test
    void deveValidarEmailAntesDoIdentity() {
        when(passwordEncoder.encode(anyString())).thenReturn("hash");
        when(repository.save(any())).thenReturn(mock(User.class));

        service.execute(command);

        var order = inOrder(userPolicy);
        order.verify(userPolicy).validateEmailIsUnique("joao@email.com");
        order.verify(userPolicy).validateIdentityIsUnique("12345678901");
    }
}
