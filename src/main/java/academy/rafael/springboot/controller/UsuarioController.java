package academy.rafael.springboot.controller;


import academy.rafael.springboot.domain.Usuario;
import academy.rafael.springboot.requests.UsuarioPostRequestBody;
import academy.rafael.springboot.requests.UsuarioPutRequestBody;
import academy.rafael.springboot.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("usuarios")
@Log4j2
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "List all usuarios paginated", description = "The default size is 20, use the parameter size to change the default value",
    tags = {"usuario"})
    public ResponseEntity<Page<Usuario>> list(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(usuarioService.listAll(pageable));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Usuario>> listAll() {
        return ResponseEntity.ok(usuarioService.listAllNonPageable());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable long id){
        return ResponseEntity.ok(usuarioService.findByIdOrThrowBadRequestException(id));

    }
    @GetMapping(path = "by-id/{id}")
    public ResponseEntity<Usuario> findByIdAuthenticationPrincipal(@PathVariable long id,
                                                                   @AuthenticationPrincipal UserDetails userDetails) {
        log.info(userDetails);
        return ResponseEntity.ok(usuarioService.findByIdOrThrowBadRequestException(id));

    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Usuario>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(usuarioService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody @Valid  UsuarioPostRequestBody usuarioPostRequestBody)  {
        return new ResponseEntity<>(usuarioService.save(usuarioPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/admin/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When Usuario Does Not Exist in Rhe DataBase")
    })
    public ResponseEntity<Void> delete(@PathVariable long id) {
        usuarioService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody UsuarioPutRequestBody usuarioPutRequestBody) {
        usuarioService.replace(usuarioPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
