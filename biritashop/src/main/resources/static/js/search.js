function buscar(select, pagina) {
    var clienteId = select.options[select.selectedIndex].value;
    window.location.href = "/" + pagina + "/listar/?clienteId=" + clienteId;
}
