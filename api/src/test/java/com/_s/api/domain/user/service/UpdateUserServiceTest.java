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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UpdateUserService service;

    @Test
    void deveAtualizarUsuarioComSucesso() {
        User user = mock(User.class);
        UpdateUserCommand command = new UpdateUserCommand("user-1", "Novo Nome", null, null, null, null, null, null, null);

        when(repository.findById("user-1")).thenReturn(Optional.of(user));
        when(repository.save(any(User.class))).thenReturn(user);

        User result = service.execute(command);

        assertNotNull(result);
        verify(user).updateProfile(command);
        verify(repository).save(user);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        UpdateUserCommand command = new UpdateUserCommand("inexistente", null, null, null, null, null, null, null, null);
        when(repository.findById("inexistente")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.execute(command));
        verify(repository, never()).save(any());
    }
}
