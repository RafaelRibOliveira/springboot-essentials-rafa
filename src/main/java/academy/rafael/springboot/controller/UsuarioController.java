package academy.rafael.springboot.controller;


import academy.rafael.springboot.domain.Usuario;
import academy.rafael.springboot.requests.UsuarioPostRequestBody;
import academy.rafael.springboot.requests.UsuarioPutRequestBody;
import academy.rafael.springboot.service.UsuarioService;
import academy.rafael.springboot.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("usuarios")
@Log4j2
@RequiredArgsConstructor
public class UsuarioController {
    private final DateUtil dateUtil;
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<Usuario>> list(Pageable pageable) {
        log.info(dateUtil.formatLocalDateTimeToDatabasesStyle(LocalDateTime.now()));
        return ResponseEntity.ok(usuarioService.listAll(pageable));
    }
    @GetMapping(path = "/all")
    public ResponseEntity<List<Usuario>> listAll() {
        log.info(dateUtil.formatLocalDateTimeToDatabasesStyle(LocalDateTime.now()));
        return ResponseEntity.ok(usuarioService.listAllNonPageable());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable long id){
        return ResponseEntity.ok(usuarioService.findByidOrThrowBadRequestException(id));

    }
    @GetMapping(path = "/find")
    public ResponseEntity<List<Usuario>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(usuarioService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody @Valid  UsuarioPostRequestBody usuarioPostRequestBody)  {
        return new ResponseEntity<>(usuarioService.save(usuarioPostRequestBody),HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
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
