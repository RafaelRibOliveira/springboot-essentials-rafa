package academy.rafael.springboot.requests;

import lombok.Data;

@Data
public class UsuarioPutRequestBody {
    private Long id;
    private String name;
}
