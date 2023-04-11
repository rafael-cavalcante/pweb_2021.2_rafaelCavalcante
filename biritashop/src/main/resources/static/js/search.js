function buscarPedidos() {
    var select = document.getElementById("search");
    var clienteId = select.options[select.selectedIndex].value;
    window.location.href = "/pedido/listar/?clienteId=" + clienteId;
}

function buscarDependentes() {
    var select = document.getElementById("search");
    var clienteId = select.options[select.selectedIndex].value;
    window.location.href = "/dependente/listar/?clienteId=" + clienteId;
}
