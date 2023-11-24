package com.sponet.principal;

import com.sponet.user.UserEntity;
import com.sponet.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByLoginId(username);

        if (user.equals(null)) {
            new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
        }

        return new PrincipalDetails(user);
    }
}
