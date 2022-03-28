package academy.rafael.springboot.service;

import academy.rafael.springboot.domain.Usuario;
import academy.rafael.springboot.exception.BadRequestException;
import academy.rafael.springboot.mapper.UsuarioMapper;
import academy.rafael.springboot.repository.UsuarioRepository;
import academy.rafael.springboot.requests.UsuarioPostRequestBody;
import academy.rafael.springboot.requests.UsuarioPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Page<Usuario> listAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable);

    }
        public List<Usuario> listAllNonPageable() {
            return usuarioRepository.findAll();

    }


    public List<Usuario> findByName(String name) {
        return usuarioRepository.findByName(name);
    }

    public Usuario findByidOrThrowBadRequestException(long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Usuario not Found"));
    }
    @Transactional
    public Usuario save(UsuarioPostRequestBody usuarioPostRequestBody) {
        return usuarioRepository.save(UsuarioMapper.INSTANCE.toUsuario(usuarioPostRequestBody));
    }

    public void delete(long id) {
        usuarioRepository.delete(findByidOrThrowBadRequestException(id));
    }

    public void replace(UsuarioPutRequestBody usuarioPutRequestBody) {
       Usuario saveUsuario = findByidOrThrowBadRequestException(usuarioPutRequestBody.getId());
       Usuario usuario = UsuarioMapper.INSTANCE.toUsuario(usuarioPutRequestBody);
       usuario.setId(saveUsuario.getId());
       usuarioRepository.save(usuario);
    }


}
