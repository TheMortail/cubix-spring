package hu.cobix.airport.security;

import hu.cobix.airport.model.AirportUser;
import hu.cobix.airport.repository.AirportUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AirportUserDetailService implements UserDetailsService {
    @Autowired
    AirportUserRepository airportUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AirportUser user = airportUserRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(username, user.getPassword(), user.getRoles().stream().map(SimpleGrantedAuthority::new).toList());
    }
}
