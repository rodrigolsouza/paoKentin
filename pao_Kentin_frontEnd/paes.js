var myModal = new bootstrap.Modal(document.getElementById("modalAltera"));

fetch("http://localhost:8092/pao", {
  method: "GET",
  headers: { "Content-type": "application/json;charset=UTF-8" },
})
  .then((data) => data.json())
  .then((json) => {
    const divMain = document.getElementById("main");

    let s =
      "<table class='table'>" +
      "<tr><th>Código</th><th>Nome</th><th>Descricao</th><th>Tempo De preparo</th><th>Ações</th></tr>";

    json.forEach((element) => {
      s +=
        `<tr><td>${element.codigo}</td><td>${element.nome}</td><td>${element.descricao}</td><td>${element.tempoPreparo}</td>` +
        `<td><button class="btn btn-primary" onclick="abrirModalAltera(${element.codigo})" >alterar</button>` +
        `<button class="btn btn-secondary" onclick="deletar(${element.codigo})">deletar</button></td></tr>`;
    });
    s += "</table>";

    divMain.innerHTML = s;
  })
  .catch((err) => {
    document.getElementById("main").innerHTML = "Erro ao recuperar a lista";
  });



async function abrirModalAltera(codigo) {
  myModal.show();

  const retorno = await fetch("http://localhost:8092/pao/" + codigo, {
    method: "GET",
    headers: { "Content-type": "application/json;charset=UTF-8" },
  });

  retorno.json().then((json) => {
    document.getElementById("txtCodigo").value = json.codigo;
    document.getElementById("txtNome").value = json.nome;
    document.getElementById("txtDescricao").value = json.descricao;
    document.getElementById("txtTempoPreparo").value = json.tempoPreparo;
  });
}

async function deletar(codigo){

    if(confirm("Deseja realmente deletar o registro?")){

        const retorno = await fetch("http://localhost:8092/pao/"+codigo,
        {
            method:"DELETE",
            headers: {"Content-type": "application/json;charset=UTF-8"}
        })

        location.reload();

    }

}
