package br.com.fiap.ecocoleta.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TipoResiduoSteps {

    @Dado("que a API EcoColeta está disponível")
    public void queAApiEcoColetaEstaDisponivel() {
        RestAssured.baseURI = "https://localhost:7068";
        RestAssured.useRelaxedHTTPSValidation();
    }

    private Response response;

    @Quando("eu realizar uma requisição GET para consultar os tipos de resíduos")
    public void euRealizarUmaRequisicaoGetParaConsultarOsTiposDeResiduos() {
        response = RestAssured.given()
                .queryParam("pageNumber", 1)
                .queryParam("pageSize", 10)
                .when()
                .get("/api/TipoResiduo")
                .then()
                .log().all()
                .extract().response();
    }

    @Entao("o código de resposta HTTP deve ser 200")
    public void oCodigoDeRespostaHttpDeveSer200() {
        assertEquals(200, response.getStatusCode());
    }

    @Entao("a resposta deve conter uma lista de tipos de resíduos")
    public void aRespostaDeveConterUmaListaDeTiposDeResiduos() {

        assertEquals(
                true,
                response.jsonPath().getList("$") != null
                        && !response.jsonPath().getList("$").isEmpty()
        );

    }

    @Entao("o primeiro tipo de resíduo deve conter os campos obrigatórios")
    public void oPrimeiroTipoDeResiduoDeveConterOsCamposObrigatorios() {

        var primeiroResiduo = response.jsonPath()
                .getMap("[0]");

        assertEquals(true, primeiroResiduo.containsKey("idTipoResiduo"));
        assertEquals(true, primeiroResiduo.containsKey("nomeTipo"));
        assertEquals(true, primeiroResiduo.containsKey("descricao"));
        assertEquals(true, primeiroResiduo.containsKey("reciclavel"));
    }

    @Quando("eu realizar uma requisição GET para consultar o tipo de resíduo com ID 9999")
    public void consultarTipoDeResiduoInexistente() {
        response = RestAssured.given()
                .when()
                .get("/api/TipoResiduo/9999")
                .then()
                .log().all()
                .extract().response();
    }

    @Entao("o código de resposta HTTP deve ser 404")
    public void oCodigoDeRespostaHttpDeveSer404() {
        assertEquals(404, response.getStatusCode());
    }

    @Entao("a resposta deve informar que o tipo de resíduo não foi encontrado")
    public void aRespostaDeveInformarQueOTipoDeResiduoNaoFoiEncontrado() {
        String mensagem = response.jsonPath().getString("mensagem");

        assertEquals(
                "Tipo de resíduo com ID 9999 não encontrado.",
                mensagem
        );
    }


}