package academy.rafael.springboot.requests;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "This is the Usuario's name", example = "Tensei Shittara Slime Datta Ken", required = true)
    private String name;
}
