// Example starter JavaScript for disabling form submissions if there are invalid fields
(function () {
    'use strict'

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation')

    var checkboxes = document.querySelectorAll(".checkbox");

    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function (event) {
            if (verificarCheckBox()) {
                checkboxes.forEach(checkbox => {
                    checkbox.removeAttribute('required');
                });
            } else {
                checkboxes.forEach(checkbox => {
                    checkbox.setAttribute('required', '');
                });
            }
        });
    });

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {                
                if (!form.checkValidity() || !verificarCheckBox()) {
                    event.preventDefault()
                    event.stopPropagation()
                } else {
                    //Removendo Mascaras dos inputs
                    $(".mask-cep").val($(".mask-cep").cleanVal());
                    $(".mask-telefone").val($(".mask-telefone").cleanVal());
                    $(".mask-cartao").val($(".mask-cartao").cleanVal());
                }

                form.classList.add('was-validated');
            }, false)
        })
})()