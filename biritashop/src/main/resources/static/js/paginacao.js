function listarProdutos(pagina) {
    var baseUrl = window.location.protocol + "//" + window.location.host;

    $.ajax({
        url: baseUrl + "/api/produto/?page=" + pagina + "&sort=nome,asc&size=1", // URL do endpoint que retorna os dados
        method: 'GET',
        success: function (data) {
            // Função executada quando a solicitação for bem-sucedida
            var html = '';

            data.content.forEach(function (produto) {
                html += '<div class="col" id="cards">';
                html += '<a href="#" onclick="produtoClick(' + produto.id + ')" data-bs-toggle="modal"';
                html += 'data-bs-target="#exampleModal" class="poppup link-underline link-underline-opacity-0">';
                html += '<div class="card mb-3 h-100">';
                html += '<div class="card-header">' + produto.nome + '</div>';
                html += '<img src="' + (produto.foto ? '/image/fotos/' + produto.foto : '/image/imagem.png') + '"';
                html += 'class="card-img-top" alt="..." width="30" height="240">';
                html += '<div class="card-body">';
                html += '<h5 class="card-title">' + produto.precoVenda.toLocaleString('pt-BR', {style: 'currency', currency: 'BRL'}) +'</h5>';
                html += '</div></div></a></div>';
            });


            $('#produtos-container').html(html); // Inserir HTML gerado na página

            if (data.first) {
                $("#paginaFirst").addClass("disabled");
            } else {
                $("#paginaFirst").removeClass("disabled");
            }

            if (data.last) {
                $("#paginaLast").addClass("disabled");
            } else {
                $("#paginaLast").removeClass("disabled");
            }

            $("#paginaFirst").text(data.number - 1)
            $("#paginaNumber").text(data.number + 1)
            $("#paginaNumber").text(data.number + 1)
        },
        error: function () {
            // Função executada quando ocorre um erro na solicitação
            alert('Erro ao carregar produtos.');
        }
    });
}

function filtrarProdutos(produtoInfo) {
    var baseUrl = window.location.protocol + "//" + window.location.host;

    $.ajax({
        url: baseUrl + "/api/produto/?page=" + pagina + "&sort=nome,asc&value=12", // URL do endpoint que retorna os dados
        method: 'GET',
        success: function (data) {
            // Função executada quando a solicitação for bem-sucedida
            var html = '';

            data.content.forEach(function (produto) {
                html += '<div class="col" id="cards">';
                html += '<a href="#" onclick="produtoClick(' + produto.id + ')" data-bs-toggle="modal"';
                html += 'data-bs-target="#exampleModal" class="poppup link-underline link-underline-opacity-0">';
                html += '<div class="card mb-3 h-100">';
                html += '<div class="card-header">' + produto.nome + '</div>';
                html += '<img src="' + (produto.foto ? '/image/fotos/' + produto.foto : '/image/imagem.png') + '"';
                html += 'class="card-img-top" alt="..." width="30" height="240">';
                html += '<div class="card-body">';
                html += '<h5 class="card-title">' + produto.precoVenda.toLocaleString('pt-br', { style: 'currency', currency: 'BRL' }) + '</h5>';
                html += '</div></div></a></div>';
            });


            $('#produtos-container').html(html); // Inserir HTML gerado na página

            if (data.first) {
                $("#paginaFirst").addClass("disabled");
            } else {
                $("#paginaFirst").removeClass("disabled");
            }

            if (data.last) {
                $("#paginaLast").addClass("disabled");
            } else {
                $("#paginaLast").removeClass("disabled");
            }
        },
        error: function () {
            // Função executada quando ocorre um erro na solicitação
            alert('Erro ao carregar produtos.');
        }
    });
}