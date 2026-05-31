package com._s.api.domain.user.service;

import com._s.api.domain.user.User;
import com._s.api.domain.user.UserRepository;
import com._s.api.domain.user.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private GetUserService service;

    @Test
    void deveRetornarUsuarioEncontrado() {
        User user = mock(User.class);
        when(repository.findById("user-1")).thenReturn(Optional.of(user));

        User result = service.executeById("user-1");

        assertEquals(user, result);
        verify(repository).findById("user-1");
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        when(repository.findById("inexistente")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.executeById("inexistente"));
    }
}
