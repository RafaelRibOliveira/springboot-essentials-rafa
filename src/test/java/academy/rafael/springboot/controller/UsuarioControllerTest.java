package academy.rafael.springboot.controller;

import academy.rafael.springboot.domain.Usuario;
import academy.rafael.springboot.requests.UsuarioPostRequestBody;
import academy.rafael.springboot.requests.UsuarioPutRequestBody;
import academy.rafael.springboot.service.UsuarioService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;


@ExtendWith(SpringExtension.class)
class UsuarioControllerTest {
    @InjectMocks
    private UsuarioController usuarioController;
    @Mock
    private UsuarioService usuarioServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Usuario> usuarioPage = new PageImpl<>(List.of(UsuarioCreator.createValidUsuario()));
        BDDMockito.when(usuarioServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(usuarioPage);

        BDDMockito.when(usuarioServiceMock.listAllNonPageable())
                .thenReturn(List.of(UsuarioCreator.createValidUsuario()));

        BDDMockito.when(usuarioServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(UsuarioCreator.createValidUsuario());

        BDDMockito.when(usuarioServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(UsuarioCreator.createValidUsuario()));

        BDDMockito.when(usuarioServiceMock.save(ArgumentMatchers.any(UsuarioPostRequestBody.class)))
                .thenReturn(UsuarioCreator.createValidUsuario());

        BDDMockito.doNothing().when(usuarioServiceMock).replace(ArgumentMatchers.any(UsuarioPutRequestBody.class));

        BDDMockito.doNothing().when(usuarioServiceMock).delete(ArgumentMatchers.anyLong());

    }
    @Test
    @DisplayName("List returns list of usuario inside page object when successful")
    void list_ReturnsListOfUsuariosInsidePageObject_WhenSuccessful(){
        String expectedName = UsuarioCreator.createValidUsuario().getName();

        Page<Usuario> usuarioPage = usuarioController.list(null).getBody();

        Assertions.assertThat(usuarioPage).isNotNull();

        Assertions.assertThat(usuarioPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(usuarioPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("ListAll returns list of usuario when successful")
    void listAll_ReturnsListOfUsuarios_WhenSuccessful(){
        String expectedName = UsuarioCreator.createValidUsuario().getName();

        List<Usuario> usuarios = usuarioController.listAll().getBody();

        Assertions.assertThat(usuarios)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(usuarios.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("FindById returns a list of usuario when successful")
    void findById_ReturnsUsuario_WhenSuccessful(){
        Long expectedId = UsuarioCreator.createValidUsuario().getId();

        Usuario usuario = usuarioController.findById(1).getBody();

        Assertions.assertThat(usuario).isNotNull();

        Assertions.assertThat(usuario.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns a list of usuario when successful")
    void findByName_ReturnsListOfUsuario_WhenSuccessful(){
        String expectedName = UsuarioCreator.createValidUsuario().getName();

        List<Usuario> usuarios = usuarioController.findByName("Aline").getBody();

        Assertions.assertThat(usuarios)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(usuarios.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of usuario when usuario is not found")
    void findByName_ReturnsEmptyListOfUsuario_WhenUsuarioIsNotFound(){
        BDDMockito.when(usuarioServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Usuario> usuarios = usuarioController.findByName("usuario").getBody();

        Assertions.assertThat(usuarios)
                .isNotNull()
                .isEmpty();

    }

    @Test
    @DisplayName("save returns usuario when successful")
    void save_ReturnsUsuario_WhenSuccessful(){

        Usuario usuario = usuarioController.save(UsuarioPostRequestBodyCreator.createUsuarioPostRequestBody()).getBody();

        Assertions.assertThat(usuario).isNotNull().isEqualTo(UsuarioCreator.createValidUsuario());

    }

    @Test
    @DisplayName("replace updates usuario when successful")
    void replace_UpdatesUsuario_WhenSuccessful(){

        Assertions.assertThatCode(() -> usuarioController.replace(UsuarioPutRequestBodyCreator.createUsuarioPutRequestBody()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = usuarioController.replace(UsuarioPutRequestBodyCreator.createUsuarioPutRequestBody());

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes usuario when successful")
    void delete_RemovesUsuario_WhenSuccessful(){

        Assertions.assertThatCode(() -> usuarioController.delete(1))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = usuarioController.delete(1);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
