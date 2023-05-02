function buscar(select, pagina) {
    var clienteId = select.options[select.selectedIndex].value;
    window.location.href = "/" + pagina + "/listar/?clienteId=" + clienteId;
}

function produtoClick(produtoId) {
    var baseUrl = window.location.protocol + "//" + window.location.host;

    var url = baseUrl + "/api/produto/" + produtoId;

    $.getJSON(url, function (produto) {
        try {
            $("#produtoNome").text(produto.nome);
            $("#produtoFoto").attr("src", "/image/fotos/" + produto.foto);
            $("#produtoPreco").text("Valor " + produto.precoVenda.toLocaleString('pt-br', { style: 'currency', currency: 'BRL' }));
            $("#produtoEmbalagem").text("Tipo Material " + produto.embalagem);
            $("#formProduto").attr("action", "/carrinho/adicionar/" + produto.id);
        } catch (ex) {
            console.log(ex);
        }
    });
}
