package academy.rafael.springboot.service;

import academy.rafael.springboot.domain.Usuario;
import academy.rafael.springboot.exception.BadRequestException;
import academy.rafael.springboot.repository.UsuarioRepository;
import academy.rafael.springboot.util.UsuarioCreator;
import academy.rafael.springboot.util.UsuarioPostRequestBodyCreator;
import academy.rafael.springboot.util.UsuarioPutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
class UsuarioServiceTest {
    @InjectMocks
    private UsuarioService usuarioService;
    @Mock
    private UsuarioRepository usuarioRepositoryMock;

    @BeforeEach
    void setUp(){
        PageImpl<Usuario> usuarioPage = new PageImpl<>(List.of(UsuarioCreator.createValidUsuario()));
        BDDMockito.when(usuarioRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(usuarioPage);

        BDDMockito.when(usuarioRepositoryMock.findAll())
                .thenReturn(List.of(UsuarioCreator.createValidUsuario()));

        BDDMockito.when(usuarioRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(UsuarioCreator.createValidUsuario()));

        BDDMockito.when(usuarioRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(UsuarioCreator.createValidUsuario()));

        BDDMockito.when(usuarioRepositoryMock.save(ArgumentMatchers.any(Usuario.class)))
                .thenReturn(UsuarioCreator.createValidUsuario());


        BDDMockito.doNothing().when(usuarioRepositoryMock).delete(ArgumentMatchers.any(Usuario.class));

    }
    @Test
    @DisplayName("ListAll returns list of usuario inside page object when successful")
    void listAll_ReturnsListOfUsuariosInsidePageObject_WhenSuccessful(){
        String expectedName = UsuarioCreator.createValidUsuario().getName();

        Page<Usuario> usuarioPage = usuarioService.listAll(PageRequest.of(1, 2));

        Assertions.assertThat(usuarioPage).isNotNull();

        Assertions.assertThat(usuarioPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(usuarioPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("ListAllNonPageable returns list of usuario when successful")
    void listAllNonPageable_ReturnsListOfUsuarios_WhenSuccessful(){
        String expectedName = UsuarioCreator.createValidUsuario().getName();

        List<Usuario> usuarios = usuarioService.listAllNonPageable();

        Assertions.assertThat(usuarios)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(usuarios.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns usuario when successful")
    void findByIdOrThrowBadRequestException_ReturnsUsuario_WhenSuccessful(){
        Long expectedId = UsuarioCreator.createValidUsuario().getId();

        Usuario usuario = usuarioService.findByIdOrThrowBadRequestException(1);

        Assertions.assertThat(usuario).isNotNull();

        Assertions.assertThat(usuario.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when usuario is not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenUsuarioIsNotFound(){
        BDDMockito.when(usuarioRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> usuarioService.findByIdOrThrowBadRequestException(1));
    }

    @Test
    @DisplayName("findByName returns a list of usuario when successful")
    void findByName_ReturnsListOfUsuario_WhenSuccessful(){
        String expectedName = UsuarioCreator.createValidUsuario().getName();

        List<Usuario> usuarios = usuarioService.findByName("usuario");

        Assertions.assertThat(usuarios)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(usuarios.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of usuario when usuario is not found")
    void findByName_ReturnsEmptyListOfUsuario_WhenUsuarioIsNotFound(){
        BDDMockito.when(usuarioRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Usuario> usuarios = usuarioService.findByName("usuario");

        Assertions.assertThat(usuarios)
                .isNotNull()
                .isEmpty();

    }

    @Test
    @DisplayName("save returns usuario when successful")
    void save_ReturnsUsuario_WhenSuccessful(){

        Usuario usuario = usuarioService.save(UsuarioPostRequestBodyCreator.createUsuarioPostRequestBody());

        Assertions.assertThat(usuario).isNotNull().isEqualTo(UsuarioCreator.createValidUsuario());

    }

    @Test
    @DisplayName("replace updates usuario when successful")
    void replace_UpdatesUsuario_WhenSuccessful(){

        Assertions.assertThatCode(() -> usuarioService.replace(UsuarioPutRequestBodyCreator.createUsuarioPutRequestBody()))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("delete removes usuario when successful")
    void delete_RemovesUsuario_WhenSuccessful(){

        Assertions.assertThatCode(() -> usuarioService.delete(1))
                .doesNotThrowAnyException();

    }
}