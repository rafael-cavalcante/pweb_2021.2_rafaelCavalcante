$(document).ready(function () {
    //$("#cep").mask("00.000-000", {placeholder: '__.___-___'});
    $("#telefone").mask("+00 (00) 00000-0000", { placeholder: '+__ (__) _____-____' });
    $("#precoCompra").mask("#0.00", { reverse: true });
    $("#precoVenda").mask("#0.00", { reverse: true });
    $("#volume").mask("#0.00", { reverse: true });
    $(".mask-valor").mask("#0.00", { reverse: true });

    $("#quantidade").keyup(function () {
        if (this.value.length > 1) {
            $("#quantidade").val(this.value.match(/[0-9]*/));
        } else {
            $("#quantidade").val(this.value.match(/[1-9]*/));
        }
    });

    //Método de selecionar e desselecionar checkboxs da classe checkbox-item 
    $("#selectall").click(function (event) {  //on click 
        if (this.checked) { // check select status
            $(".checkbox-item").each(function () { //loop through each checkbox
                this.checked = true;  //select all checkboxes with class "checkbox1"               
            });
        } else {
            $(".checkbox-item").each(function () { //loop through each checkbox
                this.checked = false; //deselect all checkboxes with class "checkbox1"                       
            });
        }
    });
});


/*

Creditos:

Obrigado à Anderson Mamede pelo codigo abaixo.

https://blog.andersonmamede.com.br/autocomplete-de-endereco-pelo-CEP/

*/

// Registra o evento blur do campo "cep", ou seja, a pesquisa será feita
// quando o usuário sair do campo "cep"
$("#cep").blur(function () {
    // Remove tudo o que não é número para fazer a pesquisa
    var cep = this.value.replace(/[^0-9]/, "");

    // Validação do CEP; caso o CEP não possua 8 números, então cancela
    // a consulta
    if (cep.length != 8) {
        return false;
    }

    // A url de pesquisa consiste no endereço do webservice + o cep que
    // o usuário informou + o tipo de retorno desejado (entre "json",
    // "jsonp", "xml", "piped" ou "querty")
    var url = "https://viacep.com.br/ws/" + cep + "/json/";

    // Faz a pesquisa do CEP, tratando o retorno com try/catch para que
    // caso ocorra algum erro (o cep pode não existir, por exemplo) a
    // usabilidade não seja afetada, assim o usuário pode continuar//
    // preenchendo os campos normalmente
    $.getJSON(url, function (dadosRetorno) {
        try {
            // Preenche os campos de acordo com o retorno da pesquisa
            //$("#endereco").val(dadosRetorno.logradouro);
            //$("#bairro").val(dadosRetorno.bairro);
            $("#cidade").val(dadosRetorno.localidade);
            //$("#uf").val(dadosRetorno.uf);
        } catch (ex) { }
    });
});

