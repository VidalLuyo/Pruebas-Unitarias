package pe.edu.vg.PruebaUnitarias;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonPlaceholderTest {

    RestTemplate cliente = new RestTemplate();
    String url = "https://jsonplaceholder.typicode.com";

    @Test
    void prueba1_consultarUsuario() {
        ResponseEntity<Map> respuesta = cliente.getForEntity(url + "/users/1", Map.class);

        assertEquals(200, respuesta.getStatusCode().value());
        assertEquals(1, respuesta.getBody().get("id"));
        assertEquals("Leanne Graham", respuesta.getBody().get("name"));
    }

    @Test
    void prueba2_crearPost() {
        Map<String, Object> nuevoPost = Map.of(
            "title", "mi post",
            "body", "contenido del post",
            "userId", 1
        );

        ResponseEntity<Map> respuesta = cliente.postForEntity(
            url + "/posts",
            new HttpEntity<>(nuevoPost, new HttpHeaders()),
            Map.class
        );

        assertEquals(201, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody().get("id"));
    }

    @Test
    void prueba3_usuarioQueNoExiste() {
        HttpClientErrorException error = assertThrows(
            HttpClientErrorException.class,
            () -> cliente.getForEntity(url + "/users/99999", Map.class)
        );

        assertEquals(404, error.getStatusCode().value());
    }
}
