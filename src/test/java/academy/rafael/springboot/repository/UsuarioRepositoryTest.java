package academy.rafael.springboot.repository;

import academy.rafael.springboot.domain.Usuario;
import academy.rafael.springboot.util.UsuarioCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;


@DataJpaTest
@DisplayName("Tests for Usuario Repository")
@Log4j2
class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Save persists usuario when successful")
    void save_PersistUsuario_WhenSuccessful(){
        Usuario usuarioToBeSaved = UsuarioCreator.createUsuarioToBeSaved();

        Usuario usuarioSaved = this.usuarioRepository.save(usuarioToBeSaved);

        Assertions.assertThat(usuarioSaved).isNotNull();

        Assertions.assertThat(usuarioSaved.getId()).isNotNull();

        Assertions.assertThat(usuarioSaved.getName()).isEqualTo(usuarioToBeSaved.getName());

    }
    @Test
    @DisplayName("Save updates usuario when successful")
    void save_UpdateUsuario_WhenSuccessful(){
        Usuario usuarioToBeSaved = UsuarioCreator.createUsuarioToBeSaved();

        Usuario usuarioSaved = this.usuarioRepository.save(usuarioToBeSaved);

        usuarioSaved.setName("Overlord");

        Usuario usuarioUpdated = this.usuarioRepository.save(usuarioSaved);

        Assertions.assertThat(usuarioUpdated).isNotNull();

        Assertions.assertThat(usuarioUpdated.getId()).isNotNull();

        Assertions.assertThat(usuarioUpdated.getName()).isEqualTo(usuarioSaved.getName());

    }
    @Test
    @DisplayName("Delete removes usuario when Successful")
    void delete_RemovesUsuario_WhenSuccessful(){
        Usuario usuarioToBeSaved = UsuarioCreator.createUsuarioToBeSaved();

        Usuario usuarioSaved = this.usuarioRepository.save(usuarioToBeSaved);

        this.usuarioRepository.delete(usuarioSaved);

        Optional<Usuario> usuarioOptional = this.usuarioRepository.findById(usuarioSaved.getId());

        Assertions.assertThat(usuarioOptional).isEmpty();

    }

    @Test
    @DisplayName("Find By Name returns list of usuario when successful")
    void findByName_ReturnsListOfUsuario_WhenSuccessful(){
        Usuario usuarioToBeSaved = UsuarioCreator.createUsuarioToBeSaved();

        Usuario usuarioSaved = this.usuarioRepository.save(usuarioToBeSaved);

        String name = usuarioSaved.getName();

        List<Usuario> usuarios = this.usuarioRepository.findByName(name);

        Assertions.assertThat(usuarios)
                .isNotEmpty()
                .contains(usuarioSaved);

    }

    @Test
    @DisplayName("Find By Name returns empty list when no usuario is found")
    void findByName_ReturnsEmptyList_WhenUsuarioNotFound(){
        List<Usuario> usuarios = this.usuarioRepository.findByName("xaxa");

        Assertions.assertThat(usuarios).isEmpty();

    }

    @Test
    @DisplayName("Save throw constraintViolationException when name is empty")
    void save_ThrowConstraintViolationException_WhenNameIsEmpty(){
        Usuario usuario = new Usuario();
//        Assertions.assertThatThrownBy(() -> this.usuarioRepository.save(usuario))
//                .isInstanceOf(ConstraintViolationException.class);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                  .isThrownBy(() -> this.usuarioRepository.save(usuario))
                  .withMessageContaining("The usuario name cannot be empty");

    }


}