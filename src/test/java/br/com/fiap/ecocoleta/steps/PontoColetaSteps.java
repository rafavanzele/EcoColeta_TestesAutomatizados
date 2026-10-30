
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
    private String usuario = System.getenv("ECOCOLETA_USER");
    private String senha = System.getenv("ECOCOLETA_PASSWORD");
    private int idPontoColetaCriado;


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

    @Quando("eu cadastrar um novo ponto de coleta")
    public void cadastrarNovoPontoDeColeta() {

        String corpoRequisicao = """
        {
            "idPontoColeta": 0,
            "nomePonto": "Ponto Teste Automatizado",
            "localizacao": "Santo Andre - SP",
            "capacidadeMaxima": 100,
            "statusPonto": "Ativo"
        }
        """;

        response = RestAssured.given()
                .auth().preemptive().basic(usuario, senha)
                .contentType("application/json")
                .body(corpoRequisicao)
                .when()
                .post("/api/PontoColeta")
                .then()
                .log().all()
                .extract().response();

        if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
            idPontoColetaCriado = response.jsonPath().getInt("idPontoColeta");
        }
    }

    @Dado("que existe um ponto de coleta cadastrado para consulta")
    public void prepararPontoDeColetaParaConsulta() {

        cadastrarNovoPontoDeColeta();

        assertEquals(
                true,
                idPontoColetaCriado > 0
        );
    }

    @Dado("que existe um ponto de coleta cadastrado para atualização")
    public void prepararPontoDeColetaParaAtualizacao() {

        cadastrarNovoPontoDeColeta();

        assertEquals(
                true,
                idPontoColetaCriado > 0
        );
    }

    @Quando("eu atualizar os dados do ponto de coleta")
    public void atualizarDadosPontoDeColeta() {

        String corpoRequisicao = """
        {
            "idPontoColeta": %d,
            "nomePonto": "Ponto Teste Atualizado",
            "localizacao": "Santo Andre - SP",
            "capacidadeMaxima": 200,
            "statusPonto": "Ativo"
        }
        """.formatted(idPontoColetaCriado);

        response = RestAssured.given()
                .auth().preemptive().basic(usuario, senha)
                .contentType("application/json")
                .body(corpoRequisicao)
                .when()
                .put("/api/PontoColeta/" + idPontoColetaCriado)
                .then()
                .log().all()
                .extract().response();
    }

    @Entao("a atualização do ponto de coleta deve ser realizada com sucesso")
    public void validarSucessoAtualizacaoPontoDeColeta() {

        int statusCode = response.getStatusCode();
        System.out.println("STATUS HTTP DO PUT: " + statusCode);
        System.out.println("RESPOSTA DO PUT: " + response.asString());

        assertEquals(
                true,
                statusCode == 200 || statusCode == 204
        );
    }

    @Entao("os dados atualizados do ponto de coleta devem ser persistidos corretamente")
    public void validarPersistenciaAtualizacaoPontoDeColeta() {

        Response respostaConsulta = RestAssured.given()
                .when()
                .get("/api/PontoColeta/" + idPontoColetaCriado)
                .then()
                .log().all()
                .extract().response();

        assertEquals(200, respostaConsulta.getStatusCode());

        assertEquals(
                idPontoColetaCriado,
                respostaConsulta.jsonPath().getInt("idPontoColeta")
        );

        assertEquals(
                "Ponto Teste Atualizado",
                respostaConsulta.jsonPath().getString("nomePonto")
        );

        assertEquals(
                200,
                respostaConsulta.jsonPath().getInt("capacidadeMaxima")
        );

        respostaConsulta.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/ponto_coleta_schema.json"
                ));
    }

    @Quando("eu consultar o ponto de coleta pelo ID")
    public void consultarPontoDeColetaPorId() {

        response = RestAssured.given()
                .when()
                .get("/api/PontoColeta/" + idPontoColetaCriado)
                .then()
                .log().all()
                .extract().response();
    }

    @Entao("o código de resposta HTTP da consulta por ID deve ser 200")
    public void validarStatusHttp200ConsultaPorId() {

        assertEquals(200, response.getStatusCode());
    }

    @Entao("a resposta deve conter os dados do ponto de coleta consultado")
    public void validarDadosPontoDeColetaConsultado() {

        assertEquals(
                idPontoColetaCriado,
                response.jsonPath().getInt("idPontoColeta")
        );

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/ponto_coleta_schema.json"
                ));
    }

    @Entao("o cadastro do ponto de coleta deve ser realizado com sucesso")
    public void validarSucessoCadastroPontoColeta() {

        int statusCode = response.getStatusCode();

        assertEquals(
                true,
                statusCode == 200 || statusCode == 201
        );
    }

    @Entao("a resposta deve conter os dados do ponto de coleta cadastrado")
    public void validarDadosPontoColetaCadastrado() {

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/ponto_coleta_schema.json"
                ));
    }

    @Dado("que existe um ponto de coleta cadastrado para exclusão")
    public void prepararPontoDeColetaParaExclusao() {

        cadastrarNovoPontoDeColeta();

        assertEquals(
                true,
                idPontoColetaCriado > 0
        );
    }

    @Quando("eu excluir o ponto de coleta pelo ID")
    public void excluirPontoDeColetaPeloId() {

        response = RestAssured.given()
                .auth().preemptive().basic(usuario, senha)
                .when()
                .delete("/api/PontoColeta/" + idPontoColetaCriado)
                .then()
                .log().all()
                .extract().response();
    }

    @Entao("a exclusão do ponto de coleta deve ser realizada com sucesso")
    public void validarSucessoExclusaoPontoDeColeta() {

        int statusCode = response.getStatusCode();

        assertEquals(204, statusCode);
    }

    @Entao("o ponto de coleta excluído não deve mais ser encontrado")
    public void validarPontoDeColetaExcluido() {

        Response respostaConsulta = RestAssured.given()
                .auth().preemptive().basic(usuario, senha)
                .when()
                .get("/api/PontoColeta/" + idPontoColetaCriado)
                .then()
                .log().all()
                .extract().response();

        assertEquals(404, respostaConsulta.getStatusCode());
    }


}
