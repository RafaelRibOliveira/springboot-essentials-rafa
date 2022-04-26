package academy.rafael.springboot.util;

import academy.rafael.springboot.domain.Usuario;

public class UsuarioCreator {

    public static Usuario createUsuarioToBeSaved(){
        return Usuario.builder()
            .name("Aline")
            .build();

        }
    public static Usuario createValidUsuario (){
        return Usuario.builder()
                .name("Aline")
                .id(1L)
                .build();

    }
    public static Usuario createValidUpdateUsuario(){
        return Usuario.builder()
                .name("lilian")
                .id(1L)
                .build();

    }

    }
