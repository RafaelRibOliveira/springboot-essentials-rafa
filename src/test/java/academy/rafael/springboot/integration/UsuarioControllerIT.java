package academy.rafael.springboot.integration;


import academy.rafael.springboot.domain.Usuario;
import academy.rafael.springboot.repository.UsuarioRepository;
import academy.rafael.springboot.requests.UsuarioPostRequestBody;
import academy.rafael.springboot.util.UsuarioCreator;
import academy.rafael.springboot.util.UsuarioPostRequestBodyCreator;
import academy.rafael.springboot.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UsuarioControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("list returns list of usuario inside page object when successful")
    void list_ReturnsListOfUsuariosInsidePageObject_WhenSuccessful() {
        Usuario savedUsuario = usuarioRepository.save(UsuarioCreator.createUsuarioToBeSaved());

        String expectedName = savedUsuario.getName();

        PageableResponse<Usuario> usuarioPage = testRestTemplate.exchange("/usuarios", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Usuario>>() {
                }).getBody();

        Assertions.assertThat(usuarioPage).isNotNull();

        Assertions.assertThat(usuarioPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(usuarioPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAll returns list of usuario when successful")
    void listAll_ReturnsListOfUsuario_WhenSuccessful() {
        Usuario savedUsuario = usuarioRepository.save(UsuarioCreator.createUsuarioToBeSaved());

        String expectedName = savedUsuario.getName();

        List<Usuario> usuarios = testRestTemplate.exchange("/usuarios/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Usuario>>() {
                }).getBody();

        Assertions.assertThat(usuarios)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(usuarios.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns usuario when successful")
    void findById_ReturnsUsuario_WhenSuccessful() {
        Usuario savedUsuario = usuarioRepository.save(UsuarioCreator.createUsuarioToBeSaved());

        Long expectedId = savedUsuario.getId();

        Usuario usuario = testRestTemplate.getForObject("/usuarios/{id}", Usuario.class, expectedId);

        Assertions.assertThat(usuario).isNotNull();

        Assertions.assertThat(usuario.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns a list of usuario when successful")
    void findByName_ReturnsListOfUsuario_WhenSuccessful(){
        Usuario savedUsuario = usuarioRepository.save(UsuarioCreator.createUsuarioToBeSaved());

        String expectedName = savedUsuario.getName();

        String url = String.format("/usuarios/find?name=%s", expectedName);

        List<Usuario> usuarios = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Usuario>>() {
                }).getBody();

        Assertions.assertThat(usuarios)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(usuarios.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of usuario when usuario is not found")
    void findByName_ReturnsEmptyListOfUsuario_WhenUsuarioIsNotFound(){
        List<Usuario> usuarios = testRestTemplate.exchange("/usuarios/find?name=dbz", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Usuario>>() {
                }).getBody();

        Assertions.assertThat(usuarios)
                .isNotNull()
                .isEmpty();

    }

    @Test
    @DisplayName("save returns usuario when successful")
    void save_ReturnsUsuario_WhenSuccessful(){
        UsuarioPostRequestBody usuarioPostRequestBody = UsuarioPostRequestBodyCreator.createUsuarioPostRequestBody();

        ResponseEntity<Usuario> usuarioResponseEntity = testRestTemplate.postForEntity("/usuarios", usuarioPostRequestBody, Usuario.class);

        Assertions.assertThat(usuarioResponseEntity).isNotNull();
        Assertions.assertThat(usuarioResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(usuarioResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(usuarioResponseEntity.getBody().getId()).isNotNull();

    }

    @Test
    @DisplayName("replace updates usuario when successful")
    void replace_UpdatesUsuario_WhenSuccessful(){
        Usuario savedUsuario = usuarioRepository.save(UsuarioCreator.createUsuarioToBeSaved());

        savedUsuario.setName("new name");

        ResponseEntity<Void> usuarioResponseEntity = testRestTemplate.exchange("/usuarios",
                HttpMethod.PUT,new HttpEntity<>(savedUsuario), Void.class);

        Assertions.assertThat(usuarioResponseEntity).isNotNull();

        Assertions.assertThat(usuarioResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes usuario when successful")
    void delete_RemovesUsuario_WhenSuccessful(){
        Usuario savedUsuario = usuarioRepository.save(UsuarioCreator.createUsuarioToBeSaved());

        ResponseEntity<Void> usuarioResponseEntity = testRestTemplate.exchange("/usuarios/{id}",
                HttpMethod.DELETE,null, Void.class, savedUsuario.getId());

        Assertions.assertThat(usuarioResponseEntity).isNotNull();

        Assertions.assertThat(usuarioResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}