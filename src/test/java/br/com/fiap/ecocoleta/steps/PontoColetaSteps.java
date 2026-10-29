
package br.com.fiap.ecocoleta.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class PontoColetaSteps {

    private Response response;


    @Quando("eu realizar uma requisição GET para consultar os pontos de coleta")
    public void consultarTodosOsPontosDeColeta() {

        response = RestAssured.given()
                .when()
                .get("/api/PontoColeta")
                .then()
                .log().all()
                .extract().response();
    }

    @Entao("o código de resposta HTTP da consulta de pontos de coleta deve ser 200")
    public void validarStatusHttp200PontoColeta() {

        assertEquals(200, response.getStatusCode());
    }

    @Entao("a resposta deve conter uma lista de pontos de coleta")
    public void validarListaPontosDeColeta() {

        assertEquals(
                200,
                response.getStatusCode()
        );

        assertEquals(
                true,
                response.jsonPath().getList("$") != null
        );

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/ponto_coleta_lista_schema.json"
                ));

    }




}
