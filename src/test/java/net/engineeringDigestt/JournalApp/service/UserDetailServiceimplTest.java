package net.engineeringDigestt.JournalApp.service;

import net.engineeringDigestt.JournalApp.Controller.entity.User;
import net.engineeringDigestt.JournalApp.Repository.UserRepository;
import net.engineeringDigestt.JournalApp.Services.UserDetailServiceimpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailServiceimplTest {
    @InjectMocks
    private UserDetailServiceimpl userDetailService;

    @Mock
    private UserRepository userRepository;

    @Test
    void loadbyusrnameTest() {
        User mockUser = User.builder()
                .username("kapil")
                .password("kapil")
                .roles(new ArrayList<>())
                .build();

        when(userRepository.findByUsername(anyString())).thenReturn(mockUser);

        UserDetails user = userDetailService.loadUserByUsername("kapil");
        Assertions.assertNotNull(user);
    }
}
