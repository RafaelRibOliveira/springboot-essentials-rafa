package academy.rafael.springboot.util;

import academy.rafael.springboot.requests.UsuarioPutRequestBody;

public class UsuarioPutRequestBodyCreator {
    public static UsuarioPutRequestBody createUsuarioPutRequestBody(){
        return UsuarioPutRequestBody.builder()
                .id(UsuarioCreator.createValidUsuario().getId())
                .name(UsuarioCreator.createValidUpdateUsuario().getName())
                .build();
    }
}
