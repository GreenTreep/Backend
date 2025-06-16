package fr.parisnanterre.greentrip.backend.service;

import fr.parisnanterre.greentrip.backend.entity.Role;
import fr.parisnanterre.greentrip.backend.entity.User;
import fr.parisnanterre.greentrip.backend.repository.UserRepository;
import fr.parisnanterre.greentrip.backend.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = User.builder()
                .id(1L)
                .email("admin@example.com")
                .role(Role.valueOf("ADMIN"))
                .build();
    }

    @Test
    public void testFindByEmail() {
        when(userRepository.findByEmail("admin@example.com")).thenReturn(user);

        User result = userService.findByEmail("admin@example.com");

        assertNotNull(result);
        assertEquals("admin@example.com", result.getEmail());
        verify(userRepository).findByEmail("admin@example.com");
    }

    @Test
    public void testFindUserById_Found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findUserById(1L);

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getId());
        verify(userRepository).findById(1L);
    }

    @Test
    public void testFindUserById_NotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        User result = userService.findUserById(2L);

        assertNull(result);
        verify(userRepository).findById(2L);
    }
}

