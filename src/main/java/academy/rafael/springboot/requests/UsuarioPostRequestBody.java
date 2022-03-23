package academy.rafael.springboot.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class UsuarioPostRequestBody {
    @NotEmpty(message = "The usuario name cannot be empty")
    private String name;
}
