$("input").on('keypress', function (e) {
    var inputValue = e.key;
    var regex = (this.value.length === 0) ? /[1-9]/ : /[0-9]/;
    if (!regex.test(inputValue)) {
        e.preventDefault();
    }
});