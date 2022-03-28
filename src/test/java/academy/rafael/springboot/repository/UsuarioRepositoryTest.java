package academy.rafael.springboot.repository;

import academy.rafael.springboot.domain.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@DisplayName("Tests for Usuario Repository")
class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Save creates usuario when successful")
    void save_PersistUsuario_WhenSuccessful(){
        Usuario usuarioToBeSaved = createUsuario();
        Usuario usuarioSaved = this.usuarioRepository.save(usuarioToBeSaved);
        Assertions.assertThat(usuarioSaved).isNotNull();
        Assertions.assertThat(usuarioSaved.getId()).isNotNull();
        Assertions.assertThat(usuarioSaved.getName()).isEqualTo(usuarioSaved.getName());

    }

    private Usuario createUsuario(){
        return Usuario.builder()
                .name("Aline")
                .build();

    }

}