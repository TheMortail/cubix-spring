package hu.cobix.airport.service;

import hu.cobix.airport.model.AirportUser;
import hu.cobix.airport.repository.AirportUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class InitDbService {

    AirportUserRepository userrepository;
    PasswordEncoder passwordEncoder;

    public InitDbService(AirportUserRepository userrepository, PasswordEncoder passwordEncoder) {
        this.userrepository = userrepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createUserIfNeeded(){
        if (!userrepository.existsById("admin")){
            userrepository.save(new AirportUser("admin", passwordEncoder.encode("pass"), Set.of("user", "admin")));
        }

        if (!userrepository.existsById("user")){
            userrepository.save(new AirportUser("user", passwordEncoder.encode("pass"), Set.of("user")));
        }
    }
}
