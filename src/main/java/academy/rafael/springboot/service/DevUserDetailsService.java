package academy.rafael.springboot.service;

import academy.rafael.springboot.repository.DevUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DevUserDetailsService implements UserDetailsService {
    private final DevUserRepository devUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        return Optional.ofNullable(devUserRepository.findByUsername(username))
                .orElseThrow(()-> new UsernameNotFoundException("Dev user not found"));
    }
}
