package academy.rafael.springboot.mapper;

import academy.rafael.springboot.domain.Usuario;
import academy.rafael.springboot.requests.UsuarioPostRequestBody;
import academy.rafael.springboot.requests.UsuarioPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class UsuarioMapper {
    public static final UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    public abstract Usuario toUsuario(UsuarioPostRequestBody usuarioPostRequestBody);

    public abstract Usuario toUsuario(UsuarioPutRequestBody usuarioPutRequestBody);

}
