<!DOCTYPE html>
<html lang="pt"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="~{layout/layout}">
<head>
    <meta th:name="${_csrf.parameterName}" th:content="${_csrf.token}"/>
</head>
<body>
<section layout:fragment="content">
    <div id="wrap" class="container">
        <div id="main">
            <div class="jumbotron">
                <div class="row justify-content-center">
                    <h3>Formulário de Avaliação de Proposta de Projeto do TCC</h3>
                </div>
                <hr/>
                <!-- MESSAGES -->
                <form th:object="${proposta}" th:action="@{/gtcc/proposta/gerar}" method="POST" autocomplete="off"
                      class="form-default form-horizontal" name="form">
                    <div class="form-group row justify-content-center">
                        <div class="input-group col-xl-5">
                            <input type="text" name="nome"
                                   id="nome" class="form-control"
                                   th:field="*{nome}" autofocus="autofocus" placeholder="Nome do Aluno *" aria-label="Nome do Aluno"
                                   aria-describedby="basic-addon1">
                        </div>
                    </div>

                    <div class="form-group row justify-content-center">
                        <div class="input-group col-xl-5">
                        <div style="color: red" th:if="${#fields.hasErrors('nome')}" th:errors="*{nome}"></div>
                        </div>
                    </div>

                    <div class="form-group row justify-content-center">
                    <div class="input-group col-xl-5">
                        <label>Avaliação *</label>
                    </div>
                </div>


                <div class="form-group offset-md-4 offset-sm-1">
                    <div class="form-check row">
                        <input class="form-check-input" th:field="*{avaliacao}" type="radio" name="avaliacao1" id="avaliacao" value="1">
                        <label class="form-check-label" for="avaliacao1">
                            1° Bimestre
                        </label>
                    </div>
                    <div class="form-check row">
                    <input class="form-check-input" th:field="*{avaliacao}" type="radio" name="avaliacao2" id="avaliacao" value="2">
                    <label class="form-check-label" for="avaliacao2">
                        2° Bimestre
                    </label>
                </div>
                <div class="form-check row">
                <input class="form-check-input" th:field="*{avaliacao}" type="radio" name="avaliacao3" id="avaliacao" value="3">
                <label class="form-check-label" for="avaliacao3">
                    3° Bimestre
                </label>
            </div>
                    <div>

                    <br>
                    <div class="form-group row justify-content-center">
                        <div class="input-group col-xl-5">
                            <label>* Esses campos são obrigatórios</label>
                        </div>
                    </div>

                    <hr/>
                    <div class="form-group row justify-content-center ">
                        <div class=" col-sm-2">
                            <a id="alert" class="btn btn-lg btn-danger" th:href="@{/gtcc/home}">
                                Voltar </a>
                        </div>
                        <div class="col-sm-2 text-right">
                            <button id="btn_salvar" type="button" class="btn btn-lg btn-success">
                                Gerar
                            </button>
                        </div>

                    </div>

                </form>
            </div>
        </div>
        <div id="push"></div>
    </div>
</section>

</body>
</html>