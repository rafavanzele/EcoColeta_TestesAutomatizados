package br.com.fiap.ecocoleta.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class TipoResiduoSteps {

    @Dado("que a API EcoColeta está disponível")
    public void queAApiEcoColetaEstaDisponivel() {
        RestAssured.baseURI = "https://localhost:7068";
        RestAssured.useRelaxedHTTPSValidation();
    }

    private Response response;

    private String usuario = System.getenv("ECOCOLETA_USER");
    private String senha = System.getenv("ECOCOLETA_PASSWORD");

    private int idTipoResiduoCriado;

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

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/tipo_residuo_lista_schema.json"));

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

    @Quando("eu realizar uma requisição POST autenticada para cadastrar um tipo de resíduo")
    public void cadastrarNovoTipoResiduo() {

        String corpoRequisicao = """
        {
            "idTipoResiduo": 0,
            "nomeTipo": "Papel Teste Automatizado",
            "descricao": "Residuo criado pelo teste automatizado",
            "reciclavel": true
        }
        """;

        response = RestAssured.given()
                .auth().preemptive().basic(usuario, senha)
                .contentType("application/json")
                .body(corpoRequisicao)
                .when()
                .post("/api/TipoResiduo")
                .then()
                .log().all()
                .extract().response();

        if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
            idTipoResiduoCriado = response.jsonPath().getInt("idTipoResiduo");
        }
    }

    @Entao("o cadastro do tipo de resíduo deve ser realizado com sucesso")
    public void validarSucessoCadastroTipoResiduo() {

        int statusCode = response.getStatusCode();

        assertEquals(
                true,
                statusCode == 200 || statusCode == 201
        );
    }

    @Entao("a resposta deve conter os dados do tipo de resíduo cadastrado")
    public void validarDadosTipoResiduoCadastrado() {

        String nomeTipo = response.jsonPath().getString("nomeTipo");
        String descricao = response.jsonPath().getString("descricao");
        Boolean reciclavel = response.jsonPath().getBoolean("reciclavel");

        assertEquals("Papel Teste Automatizado", nomeTipo);
        assertEquals("Residuo criado pelo teste automatizado", descricao);
        assertEquals(true, reciclavel);
        assertEquals(true, idTipoResiduoCriado > 0);

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/tipo_residuo_schema.json"));

    }

    @Dado("que existe um tipo de resíduo criado para atualização")
    public void criarTipoResiduoParaAtualizacao() {

        String corpoRequisicao = """
    {
        "idTipoResiduo": 0,
        "nomeTipo": "Vidro Teste Atualizacao",
        "descricao": "Residuo criado para testar PUT",
        "reciclavel": true
    }
    """;

        Response respostaCadastro = RestAssured.given()
                .auth().preemptive().basic(usuario, senha)
                .contentType("application/json")
                .body(corpoRequisicao)
                .when()
                .post("/api/TipoResiduo")
                .then()
                .log().all()
                .extract().response();

        int statusCode = respostaCadastro.getStatusCode();

        assertEquals(
                true,
                statusCode == 200 || statusCode == 201
        );

        idTipoResiduoCriado = respostaCadastro.jsonPath()
                .getInt("idTipoResiduo");

        assertEquals(true, idTipoResiduoCriado > 0);
    }

    @Quando("eu realizar uma requisição PUT autenticada para atualizar o tipo de resíduo")
    public void atualizarTipoResiduo() {

        String corpoRequisicao = """
    {
        "idTipoResiduo": %d,
        "nomeTipo": "Vidro Teste Atualizado",
        "descricao": "Residuo atualizado pelo teste automatizado",
        "reciclavel": true
    }
    """.formatted(idTipoResiduoCriado);

        response = RestAssured.given()
                .auth().preemptive().basic(usuario, senha)
                .contentType("application/json")
                .body(corpoRequisicao)
                .when()
                .put("/api/TipoResiduo/" + idTipoResiduoCriado)
                .then()
                .log().all()
                .extract().response();
    }

    @Entao("a atualização do tipo de resíduo deve ser realizada com sucesso")
    public void validarSucessoAtualizacaoTipoResiduo() {

        int statusCode = response.getStatusCode();

        assertEquals(
                true,
                statusCode == 200 || statusCode == 204
        );
    }

    @Entao("os dados atualizados devem ser persistidos corretamente")
    public void validarPersistenciaAtualizacaoTipoResiduo() {

        Response respostaConsulta = RestAssured.given()
                .when()
                .get("/api/TipoResiduo/" + idTipoResiduoCriado)
                .then()
                .log().all()
                .extract().response();

        assertEquals(200, respostaConsulta.getStatusCode());

        String nomeTipo = respostaConsulta.jsonPath()
                .getString("nomeTipo");

        String descricao = respostaConsulta.jsonPath()
                .getString("descricao");

        Boolean reciclavel = respostaConsulta.jsonPath()
                .getBoolean("reciclavel");

        assertEquals("Vidro Teste Atualizado", nomeTipo);
        assertEquals(
                "Residuo atualizado pelo teste automatizado",
                descricao
        );
        assertEquals(true, reciclavel);
    }

    @Dado("que existe um tipo de resíduo criado para exclusão")
    public void criarTipoResiduoParaExclusao() {

        String corpoRequisicao = """
    {
        "idTipoResiduo": 0,
        "nomeTipo": "Residuo Teste Exclusao",
        "descricao": "Residuo criado para testar DELETE",
        "reciclavel": true
    }
    """;

        Response respostaCadastro = RestAssured.given()
                .auth().preemptive().basic(usuario, senha)
                .contentType("application/json")
                .body(corpoRequisicao)
                .when()
                .post("/api/TipoResiduo")
                .then()
                .log().all()
                .extract().response();

        int statusCode = respostaCadastro.getStatusCode();

        assertEquals(
                true,
                statusCode == 200 || statusCode == 201
        );

        idTipoResiduoCriado = respostaCadastro.jsonPath()
                .getInt("idTipoResiduo");

        assertEquals(true, idTipoResiduoCriado > 0);
    }

    @Quando("eu realizar uma requisição DELETE autenticada para excluir o tipo de resíduo")
    public void excluirTipoResiduo() {

        response = RestAssured.given()
                .auth().preemptive().basic(usuario, senha)
                .when()
                .delete("/api/TipoResiduo/" + idTipoResiduoCriado)
                .then()
                .log().all()
                .extract().response();
    }

    @Entao("a exclusão do tipo de resíduo deve ser realizada com sucesso")
    public void validarSucessoExclusaoTipoResiduo() {

        int statusCode = response.getStatusCode();

        assertEquals(
                true,
                statusCode == 200 || statusCode == 204
        );
    }

    @Entao("o tipo de resíduo excluído não deve mais existir")
    public void validarTipoResiduoExcluido() {

        Response respostaConsulta = RestAssured.given()
                .when()
                .get("/api/TipoResiduo/" + idTipoResiduoCriado)
                .then()
                .log().all()
                .extract().response();

        assertEquals(404, respostaConsulta.getStatusCode());
    }

}