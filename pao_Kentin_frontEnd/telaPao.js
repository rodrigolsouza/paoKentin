/*telaPao aqui equival ao cadastro de fornada.
*/

const botoesPaes = document.querySelectorAll(".botao-pao");

botoesPaes.forEach((botao) => {
  botao.addEventListener("click", function (e) {
    e.preventDefault();

    var nomePao = botao.textContent.trim();

    fetch("http://localhost:8092/pao/", {
      method: "GET",
      headers: { "Content-type": "application/json;charset=UTF-8" },
    })
      .then((data) => data.json())
      .then((json) => {
        json.forEach((element) => {
            if(nomePao.toLowerCase===element.nome.toLowerCase){
                var codigo= element.id;
                var pao = {};
                pao.id=codigo;
                pao.nome=element.nome;
                pao.descricao=element.descricao;
                pao.tempoPreparo=element.tempoPreparo;
            }

        var envio = {};
        envio.tempoInicial=Date.now();
        envio.pao=pao;
            
        });

      })
      .catch((err) => {
        document.getElementById("result").innerHTML = "Erro ao recuperar a lista";
      });


    fetch("http://localhost:8092/fornada/"+ codigo, {
        method: "POST",
        body: JSON.stringify(envio),
        headers: { "Content-type": "application/json; charset=UTF-8" }
        })
        .then((data) => {
            document.getElementById("result").innerHTML =
            "Cadastro Realizado com Sucesso!";
        })
        .catch((erro) => {
            document.getElementById("result").innerHTML = "Erro ao realizar cadastro!";
        });
    });
});
