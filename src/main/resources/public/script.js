document.addEventListener("DOMContentLoaded", function () { //ativar funções apenas após o carregamento da pagina
    //requisição deletar
    const btn = document.querySelector("#send");
    btn.addEventListener("click", function (e) {
        e.preventDefault();
        var numero = document.querySelector("#deletar");
        var valor = numero.value;
        console.log(valor);
        fetch("/produto/delete/" + valor, {
            method: "GET"
        })
            .then(response => {
                if (response.ok) {
                    alert("Excluido com sucesso");

                } else if (!response.ok) {
                    alert("Produto não encontrado");
                    throw new Error("Erro ao encontrar produto");
                }
                return;
            })
            .catch(error => {
                console.error("Erro ao excluir produto", error);
            });

    });

    //Fazer a edição
    const btn4 = document.querySelector("#edit");

    btn4.addEventListener("click", function (e) {
        e.preventDefault();
        var numero = document.querySelector("#editar").value;

        fetch("/produto/update/" + numero, {
            method: "GET"
        })
            .then(response => {
                if (response.ok) {
                    alert("Encontrado");
                    const editDiv = document.querySelector(".formEdit");//criar um novo formulario no html para pegar as informações
                    editDiv.innerHTML = ` 
                    <form id="formularioEdit">
                        <label for="Editdescricao">Placa:</label>
                        <input type="text" id="Editdescricao" name="Editdescricao"><br>
                        <label for="Editmarca">Marca:</label>
                        <input type="text" id="Editmarca" name="Editmarca"><br>
                        <label for="Editmodelo">Modelo:</label>
                        <input type="text" id="Editmodelo" name="Editmodelo"><br>
                        <label for="Editano">Ano:</label>
                        <input type="text" id="Editano" name="Editano"><br>
                        <label for="Editpreco">Preço:</label>
                        <input type="text" id="Editpreco" name="Editpreco"><br>
                        <input type="submit" value="Editar" id="botao2">
                    </form>
                    `;

                    const editForm = document.querySelector("#formularioEdit");
                    editForm.addEventListener("submit", function (e) {
                        e.preventDefault();
                        const Editplaca = document.querySelector("#Editdescricao").value;
                        const Editmarca = document.querySelector("#Editmarca").value;
                        const Editmodelo = document.querySelector("#Editmodelo").value;
                        const Editano = document.querySelector("#Editano").value;
                        const Editpreco = document.querySelector("#Editpreco").value;

                        if (Editplaca === '' || Editmarca === '' || Editmodelo === '' || Editano === '' || Editpreco === '') {
                            alert("Por favor, preencha todos os campos.");
                            return;
                        }

                        const dadosEdit = {
                            placa: Editplaca,
                            marca: Editmarca,
                            modelo: Editmodelo,
                            ano: Editano,
                            preco: Editpreco
                        };

                        fetch("/produto/update/" + numero, {//fazer a requisição e subir para o Back End
                            method: "POST",
                            headers: {
                                "Content-Type": "application/json"
                            },
                            body: JSON.stringify(dadosEdit)
                        })
                            .then(response => {
                                if (response.ok) {
                                    alert("Editado com sucesso!");
                                    editForm.reset();
                                    editDiv.innerHTML = ` `
                                } else {
                                    throw new Error("Erro no registro");
                                }
                            })
                            .catch(error => {
                                alert("Erro no registro: " + error.message);
                            });
                    });
                } else if (!response.ok) {
                    alert("Produto não encontrado");
                    throw new Error("Erro ao encontrar produto");
                }
            })
            .catch(error => {
                console.error("Erro ao encontrar o produto", error);
                console.error("Erro no registro:", error);
            });
    });

    //Requisição inserir

    const form = document.querySelector("#formulario");
    form.addEventListener("submit", function (e) {
        e.preventDefault(); // Evita o comportamento padrão de enviar o formulário

        // Obtém os valores dos campos do formulário
        const placa = document.querySelector("#descricao").value;
        const marca = document.querySelector("#marca").value;
        const modelo = document.querySelector("#modelo").value;
        const ano = document.querySelector("#ano").value;
        const preco = document.querySelector("#preco").value;


        if (descricao === '' || marca === '' || modelo === '' || ano === '' || preco === '') {
            alert("Por favor, preencha todos os campos.");
            return; // Para a execução do código
        }


        // Cria um objeto com os valores
        const dados = {
            placa,
            marca,
            modelo,
            ano,
            preco
        };

        console.log(dados)

        // Realiza a requisição POST para o backend
        fetch("/produto/insert", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(dados)
        })
            .then(response => {
                if (response.ok) {
                    alert("Registrado com sucesso!");
                    form.reset(); // Limpa o formulário após o registro bem-sucedido
                } else {
                    throw new Error("Erro no registro");
                }
            })
            .catch(error => {
                alert("Erro no registro: " + error.message);
                console.error("Erro no registro:", error);
            });
    });


    // Selecione o botão e o modal
    const btnListar = document.getElementById("listar-btn");
    const modal = document.getElementById("myModal");
    const spanClose = document.getElementsByClassName("close")[0];

    // Adicione um ouvinte de evento ao botão
    btnListar.addEventListener("click", function (e) {
        e.preventDefault();
        fetch("/produto/list/1")
            .then(response => response.json())
            .then(data => {
                const listaCarros = document.getElementById("listar-carros");
                listaCarros.innerHTML = "";

                data.forEach(carro => {
                    const itemCarro = document.createElement("li");
                    itemCarro.textContent = `${carro.codigo} - ${carro.placa} - ${carro.marca} - ${carro.modelo} - ${carro.ano} - ${carro.preco}`;
                    listaCarros.appendChild(itemCarro);
                });

                modal.style.display = "block"; // Exibir o modal
            })
            .catch(error => {
                console.error("Erro ao Listar", error);
            });
    });

    // Adicione um ouvinte de evento para fechar o modal ao clicar no botão fechar
    spanClose.onclick = function () {
        modal.style.display = "none";
    }

    // Feche o modal se o usuário clicar fora dele
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

})