package academy.rafael.springboot.util;

import academy.rafael.springboot.requests.UsuarioPostRequestBody;

public class UsuarioPostRequestBodyCreator {
    public static UsuarioPostRequestBody createUsuarioPostRequestBody() {
        return UsuarioPostRequestBody.builder()
                .name(UsuarioCreator.createUsuarioToBeSaved().getName())
                .build();

    }
}
