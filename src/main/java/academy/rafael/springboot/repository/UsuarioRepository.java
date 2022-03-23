package academy.rafael.springboot.repository;

import academy.rafael.springboot.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByName(String name);

}
