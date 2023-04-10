function buscarPedidos() {
    var select = document.getElementById("buscar");
    var idSelecionado = select.options[select.selectedIndex].value;
    var novaUrl = "/pedido/listar/" + idSelecionado;
    window.location.href = novaUrl;
}