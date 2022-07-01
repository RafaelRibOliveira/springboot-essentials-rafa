package academy.rafael.springboot.repository;

import academy.rafael.springboot.domain.DevUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevUserRepository extends JpaRepository<DevUser, Long> {

    DevUser findByUsername(String username);
}
