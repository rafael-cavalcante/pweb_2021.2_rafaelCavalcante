$(".input-unidades").on('keypress', function (event) {
    var inputValue = event.key;
    var regex = (this.value.length === 0) ? /[1-9]/ : /[0-9]/;
    if (!regex.test(inputValue)) {
        event.preventDefault();
    }
});