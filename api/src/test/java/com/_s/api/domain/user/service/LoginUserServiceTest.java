package com._s.api.domain.user.service;

import com._s.api.domain.user.PasswordEncoder;
import com._s.api.domain.user.User;
import com._s.api.domain.user.UserPolicy;
import com._s.api.domain.user.UserRepository;
import com._s.api.domain.user.exception.UserNotFoundException;
import com._s.api.infra.auth.InvalidCredentialsException;
import com._s.api.presentation.dto.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LoginUserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserPolicy userPolicy;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginUserService service;

    private LoginRequest loginRequest;
    private User mockUser;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest();
        loginRequest.setEmail("joao@email.com");
        loginRequest.setPassword("senha123");

        mockUser = mock(User.class);
        when(mockUser.getPassword()).thenReturn("hash_senha");
    }

    @Test
    void deveRetornarUsuarioComCredenciaisCorretas() {
        when(repository.findByEmail("joao@email.com")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("senha123", "hash_senha")).thenReturn(true);

        User result = service.execute(loginRequest);

        assertNotNull(result);
        assertEquals(mockUser, result);
    }

    @Test
    void deveLancarExcecaoQuandoEmailNaoExiste() {
        when(repository.findByEmail("joao@email.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.execute(loginRequest));
    }

    @Test
    void deveLancarExcecaoQuandoSenhaEstaErrada() {
        when(repository.findByEmail("joao@email.com")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("senha123", "hash_senha")).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> service.execute(loginRequest));
    }

    @Test
    void naoDeveChamarPasswordEncoderSeEmailNaoExiste() {
        when(repository.findByEmail("joao@email.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.execute(loginRequest));
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void mensagemDeUserNotFoundDeveSerDescritiva() {
        when(repository.findByEmail("joao@email.com")).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class,
                () -> service.execute(loginRequest));
        assertNotNull(ex.getMessage());
        assertFalse(ex.getMessage().isEmpty());
    }
}
