$(".input-unidades").on('keypress', function (event) {
    var inputValue = event.key;

    var regex = (this.value.length === 0) ? /[1-9]/ : /[0-9]/;

    if (!regex.test(inputValue)) {
        event.preventDefault();
    }
});

$(".input-unidades").on('blur', function () {
    if ($(this).val() === '') {
        $(this).val('1');
    }
});

function calcularTotal() {
    var subTotal = 0;

    // Loop através dos elementos da lista
    $('.list-group-item').each(function () {
        var $checkbox = $(this).find('.child-checkbox');
        var $input = $(this).find('.input-unidades');
        var $span = $(this).find('.text-preco');

        if ($checkbox.is(':checked')) {
            var quantidade = parseFloat($input.val());
            var preco = parseFloat($span.text().replace('R$', '').trim());
            subTotal += quantidade * preco;
        }
    });

    var imposto = subTotal * 0.1375;

    var total = subTotal + imposto;

    // Atualiza o valor da tag strong
    $('#subTotal').text('R$ ' + subTotal.toFixed(2));
    $('#imposto').text('R$ ' + imposto.toFixed(2));
    $('#total').text('R$ ' + total.toFixed(2));
}

// Associe o evento de mudança da checkbox e input number à função de cálculo
$('.child-checkbox, .input-unidades, #master-checkbox').on('change', calcularTotal);

// Inicialize o cálculo quando a página é carregada
calcularTotal();

const decrementButtons = document.querySelectorAll(".decrement");
const incrementButtons = document.querySelectorAll(".increment");
const valueInputs = document.querySelectorAll(".value");

decrementButtons.forEach((button, index) => {
    button.addEventListener("click", () => {
        const currentValue = parseInt(valueInputs[index].value);
        if (currentValue > 1) {
            valueInputs[index].value = currentValue - 1;
            calcularTotal();
        }
    });
});

incrementButtons.forEach((button, index) => {
    button.addEventListener("click", () => {
        const currentValue = parseInt(valueInputs[index].value);
        valueInputs[index].value = currentValue + 1;
        calcularTotal();
    });
});

$(function() {
    $("#formaPagamento").change(function() {
      var formaPagamento = $(this).val();
  
      switch (formaPagamento) {
        case "dinheiro":
          $("#div-valor").show();
          $("#div-numeroCartao").hide();
          break;
        case "pix":
          $("#div-valor").show();
          $("#div-numeroCartao").hide();
          break;
        case "cartao":
          $("#div-valor").show();
          $("#div-numeroCartao").show();
          break;
      }
    });
  });
  