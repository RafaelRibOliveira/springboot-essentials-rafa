package academy.rafael.springboot.client;


import academy.rafael.springboot.domain.Usuario;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Usuario> entity = new RestTemplate().getForEntity("http://LocalHost:8080/usuarios/{id}",
                Usuario.class);
        log.info(entity);

        Usuario object = new RestTemplate().getForObject("http://LocalHost:8080/usuarios/{id}", Usuario.class,2);

        log.info(object);

        Usuario[] usuarios = new RestTemplate().getForObject("http://LocalHost:8080/usuarios/all", Usuario[].class);

        log.info(Arrays.toString(usuarios));
        //formatter:off
        ResponseEntity<List<Usuario>> exchange = new RestTemplate().exchange("http://LocalHost:8080/usuarios/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        //formatter:on
        log.info(exchange.getBody());

//        Usuario kingdom = Usuario.builder().name("kingdom").build();
//       Usuario kingdomSaved = new RestTemplate().postForObject("http://LocalHost:8080/usuarios/", kingdom, Usuario.class);
//       log.info("saved usuario{}", kingdom);

        Usuario rafa = Usuario.builder().name("rafa").build();
        ResponseEntity<Usuario> rafaSaved = new RestTemplate().exchange("http://LocalHost:8080/usuarios/",
                HttpMethod.POST,
                new HttpEntity<>(rafa, createJsonHeader()),
                Usuario.class);

        log.info("saved usuario{}", rafaSaved);

        Usuario usuarioToBeUpdated = rafaSaved.getBody();
        usuarioToBeUpdated.setName("rafa 2");

        ResponseEntity<Void> rafaUpdated = new RestTemplate().exchange("http://LocalHost:8080/usuarios/",
                HttpMethod.PUT,
                new HttpEntity<>(usuarioToBeUpdated, createJsonHeader()),
                Void.class);

        log.info(rafaUpdated);


        ResponseEntity<Void> rafaDeleted = new RestTemplate().exchange("http://LocalHost:8080/usuarios/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                usuarioToBeUpdated.getId());

        log.info(rafaDeleted);

    }
    private static HttpHeaders createJsonHeader() {
       HttpHeaders httpHeaders = new HttpHeaders();
       httpHeaders.setContentType(MediaType.APPLICATION_JSON);
       return httpHeaders;


    }
}
