
package br.com.fiap.ecocoleta.steps;

import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import io.cucumber.java.After;

import io.cucumber.java.pt.Dado;

public class ColetaResiduoSteps {

    private Response response;
    private String usuario = System.getenv("ECOCOLETA_USER");
    private String senha = System.getenv("ECOCOLETA_PASSWORD");

    private int idTipoResiduoCriado;
    private int idPontoColetaCriado;
    private int idColetaCriada;

    @Quando("eu realizar uma requisição GET para consultar as coletas de resíduos")
    public void consultarTodasAsColetasDeResiduos() {

        response = RestAssured.given()
                .when()
                .get("/api/ColetaResiduo")
                .then()
                .log().all()
                .extract().response();
    }

    @Entao("o código de resposta HTTP da consulta de coletas deve ser 200")
    public void validarStatusHttp200ColetaResiduo() {

        assertEquals(200, response.getStatusCode());
    }


    @Entao("a resposta deve conter uma lista de coletas de resíduos")
    public void validarListaColetasDeResiduos() {

        assertNotNull(response.jsonPath().getList("$"));

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/coleta_residuo_lista_schema.json"
                ));
    }

    @Dado("que existem um tipo de resíduo e um ponto de coleta válidos")
    public void prepararRelacionamentosColetaResiduo() {

        String corpoTipoResiduo = """
                {
                    "idTipoResiduo": 0,
                    "nomeTipo": "Papel Coleta Automatizada",
                    "descricao": "Tipo criado para teste de coleta",
                    "reciclavel": true
                }
                """;

        Response respostaTipo = RestAssured.given()
                .auth().preemptive().basic(usuario, senha)
                .contentType("application/json")
                .body(corpoTipoResiduo)
                .when()
                .post("/api/TipoResiduo")
                .then()
                .log().all()
                .extract().response();

        assertEquals(201, respostaTipo.getStatusCode());

        idTipoResiduoCriado =
                respostaTipo.jsonPath().getInt("idTipoResiduo");

        assertEquals(true, idTipoResiduoCriado > 0);

        String corpoPontoColeta = """
                {
                    "idPontoColeta": 0,
                    "nomePonto": "Ponto Coleta Automatizada",
                    "localizacao": "Santo Andre - SP",
                    "capacidadeMaxima": 100,
                    "statusPonto": "Ativo"
                }
                """;

        Response respostaPonto = RestAssured.given()
                .auth().preemptive().basic(usuario, senha)
                .contentType("application/json")
                .body(corpoPontoColeta)
                .when()
                .post("/api/PontoColeta")
                .then()
                .log().all()
                .extract().response();

        assertEquals(201, respostaPonto.getStatusCode());

        idPontoColetaCriado =
                respostaPonto.jsonPath().getInt("idPontoColeta");

        assertEquals(true, idPontoColetaCriado > 0);

    }

    @Quando("eu cadastrar uma nova coleta de resíduos")
    public void cadastrarNovaColetaDeResiduos() {

        String corpoRequisicao = """
                {
                    "idColeta": 0,
                    "dataColeta": "2026-10-30T10:00:00",
                    "quantidade": 15.5,
                    "idPontoColeta": %d,
                    "idTipoResiduo": %d
                }
                """.formatted(
                idPontoColetaCriado,
                idTipoResiduoCriado
        );

        response = RestAssured.given()
                .auth().preemptive().basic(usuario, senha)
                .contentType("application/json")
                .body(corpoRequisicao)
                .when()
                .post("/api/ColetaResiduo")
                .then()
                .log().all()
                .extract().response();

        if (response.getStatusCode() == 201) {
            idColetaCriada =
                    response.jsonPath().getInt("idColeta");
        }
    }

    @Entao("o cadastro da coleta de resíduos deve ser realizado com sucesso")
    public void validarSucessoCadastroColetaResiduo() {

        assertEquals(201, response.getStatusCode());

        assertEquals(true, idColetaCriada > 0);
    }

    @Entao("a resposta deve conter os dados da coleta cadastrada")
    public void validarDadosColetaCadastrada() {

        assertEquals(
                idColetaCriada,
                response.jsonPath().getInt("idColeta")
        );

        assertEquals(
                idPontoColetaCriado,
                response.jsonPath().getInt("idPontoColeta")
        );

        assertEquals(
                idTipoResiduoCriado,
                response.jsonPath().getInt("idTipoResiduo")
        );

        assertEquals(
                15.5,
                response.jsonPath().getDouble("quantidade"),
                0.001
        );

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/coleta_residuo_schema.json"
                ));
    }

    @Dado("que existe uma coleta de resíduos cadastrada")
    public void prepararColetaResiduoCadastrada() {
        cadastrarNovaColetaDeResiduos();

        assertEquals(true, idColetaCriada > 0);
    }

    @Quando("eu consultar a coleta de resíduos pelo ID")
    public void consultarColetaResiduoPeloId() {

        response = RestAssured.given()
                .auth().preemptive().basic(usuario, senha)
                .when()
                .get("/api/ColetaResiduo/" + idColetaCriada)
                .then()
                .log().all()
                .extract().response();
    }

    @Entao("o código de resposta HTTP da consulta de coleta por ID deve ser 200")
    public void validarStatusHttp200ConsultaColetaPorId() {

        assertEquals(200, response.getStatusCode());
    }

    @Entao("a resposta deve conter os dados da coleta consultada")
    public void validarDadosColetaConsultada() {

        assertEquals(
                idColetaCriada,
                response.jsonPath().getInt("idColeta")
        );

        assertEquals(
                idPontoColetaCriado,
                response.jsonPath().getInt("idPontoColeta")
        );

        assertEquals(
                idTipoResiduoCriado,
                response.jsonPath().getInt("idTipoResiduo")
        );

        assertEquals(
                15.5,
                response.jsonPath().getDouble("quantidade"),
                0.001
        );

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/coleta_residuo_schema.json"
                ));
    }

    @Quando("eu atualizar a coleta de resíduos cadastrada")
    public void atualizarColetaResiduoCadastrada() {

        String corpoRequisicao = """
                {
                    "idColeta": %d,
                    "dataColeta": "2026-10-30T10:00:00",
                    "quantidade": 25.5,
                    "idPontoColeta": %d,
                    "idTipoResiduo": %d
                }
                """.formatted(
                idColetaCriada,
                idPontoColetaCriado,
                idTipoResiduoCriado
        );

        response = RestAssured.given()
                .auth().preemptive().basic(usuario, senha)
                .contentType("application/json")
                .body(corpoRequisicao)
                .when()
                .put("/api/ColetaResiduo/" + idColetaCriada)
                .then()
                .log().all()
                .extract().response();
    }

    @Entao("a atualização da coleta de resíduos deve ser realizada com sucesso")
    public void validarSucessoAtualizacaoColetaResiduo() {

        assertEquals(204, response.getStatusCode());
    }

    @Entao("os dados atualizados da coleta devem estar persistidos")
    public void validarPersistenciaAtualizacaoColetaResiduo() {

        Response respostaConsulta = RestAssured.given()
                .auth().preemptive().basic(usuario, senha)
                .when()
                .get("/api/ColetaResiduo/" + idColetaCriada)
                .then()
                .log().all()
                .extract().response();

        assertEquals(200, respostaConsulta.getStatusCode());

        assertEquals(
                25.5,
                respostaConsulta.jsonPath().getDouble("quantidade"),
                0.001
        );

        assertEquals(
                idColetaCriada,
                respostaConsulta.jsonPath().getInt("idColeta")
        );
    }

    @Quando("eu excluir a coleta de resíduos cadastrada")
    public void excluirColetaResiduoCadastrada() {
        response = RestAssured.given()
                .auth().preemptive().basic(usuario, senha)
                .when()
                .delete("/api/ColetaResiduo/" + idColetaCriada)
                .then()
                .log().all()
                .extract().response();
    }

    @Entao("a exclusão da coleta de resíduos deve ser realizada com sucesso")
    public void validarSucessoExclusaoColetaResiduo() {

        assertEquals(204, response.getStatusCode());

    }

    @Entao("a coleta de resíduos excluída não deve mais existir")
    public void validarColetaResiduoExcluida() {

        Response respostaConsulta = RestAssured.given()
                .auth().preemptive().basic(usuario, senha)
                .when()
                .get("/api/ColetaResiduo/" + idColetaCriada)
                .then()
                .log().all()
                .extract().response();

        assertEquals(404, respostaConsulta.getStatusCode());
    }

    @After
    public void limparDadosTesteColetaResiduo() {

        StringBuilder errosLimpeza = new StringBuilder();

        if (idColetaCriada > 0) {
            try {
                Response resposta = RestAssured.given()
                        .auth().preemptive().basic(usuario, senha)
                        .when()
                        .delete("/api/ColetaResiduo/" + idColetaCriada);

                if (resposta.getStatusCode() != 204 &&
                        resposta.getStatusCode() != 404) {
                    errosLimpeza.append(
                            "Erro ao excluir ColetaResiduo: HTTP "
                                    + resposta.getStatusCode() + "\n"
                    );
                }
            } catch (Exception e) {
                errosLimpeza.append(
                        "Erro ao excluir ColetaResiduo: "
                                + e.getMessage() + "\n"
                );
            }
        }

        if (idPontoColetaCriado > 0) {
            try {
                Response resposta = RestAssured.given()
                        .auth().preemptive().basic(usuario, senha)
                        .when()
                        .delete("/api/PontoColeta/" + idPontoColetaCriado);

                if (resposta.getStatusCode() != 204 &&
                        resposta.getStatusCode() != 404) {
                    errosLimpeza.append(
                            "Erro ao excluir PontoColeta: HTTP "
                                    + resposta.getStatusCode() + "\n"
                    );
                }
            } catch (Exception e) {
                errosLimpeza.append(
                        "Erro ao excluir PontoColeta: "
                                + e.getMessage() + "\n"
                );
            }
        }

        if (idTipoResiduoCriado > 0) {
            try {
                Response resposta = RestAssured.given()
                        .auth().preemptive().basic(usuario, senha)
                        .when()
                        .delete("/api/TipoResiduo/" + idTipoResiduoCriado);

                if (resposta.getStatusCode() != 204 &&
                        resposta.getStatusCode() != 404) {
                    errosLimpeza.append(
                            "Erro ao excluir TipoResiduo: HTTP "
                                    + resposta.getStatusCode() + "\n"
                    );
                }
            } catch (Exception e) {
                errosLimpeza.append(
                        "Erro ao excluir TipoResiduo: "
                                + e.getMessage() + "\n"
                );
            }
        }

        assertEquals(
                "",
                errosLimpeza.toString(),
                "Falha na limpeza dos dados de teste"
        );
    }


}
