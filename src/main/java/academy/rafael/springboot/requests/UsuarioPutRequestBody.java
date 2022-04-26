package academy.rafael.springboot.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioPutRequestBody {
    private Long id;
    private String name;
}
