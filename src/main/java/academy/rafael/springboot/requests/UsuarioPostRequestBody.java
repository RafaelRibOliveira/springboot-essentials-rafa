package academy.rafael.springboot.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPostRequestBody {
    @NotEmpty(message = "The usuario name cannot be empty")
    private String name;
}
