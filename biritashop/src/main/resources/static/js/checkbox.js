// Obtemos referências aos elementos HTML
const selectAllCheckbox = document.getElementById('checkbox-all');
const checkboxItems = document.querySelectorAll('.checkbox-item');

// Função para lidar com o evento "change" da caixa de seleção "Selecionar todos"
selectAllCheckbox.addEventListener('change', function () {
    checkboxItems.forEach(checkbox => {
        checkbox.checked = selectAllCheckbox.checked;
    });
});

checkboxItems.forEach(checkbox => {
    checkbox.addEventListener('change', function (event) {
        if (verificarCheckBox()) {
            checkboxItems.forEach(checkbox => {
                checkbox.removeAttribute('required');
                //checkbox.classList.remove("is-invalid");
                //checkbox.classList.add("is-valid");
            });
        } else {
            checkboxItems.forEach(checkbox => {
                checkbox.setAttribute('required', '');
                //checkbox.classList.remove("is-valid");
                //checkbox.classList.add("is-invalid");
            });
        }
        const allChecked = [...checkboxItems].every(item => item.checked);
        selectAllCheckbox.checked = allChecked;
    });
});

function verificarCheckBox() {
    const checkboxesSelecionados = document.querySelectorAll('.checkbox:checked');
    return checkboxesSelecionados.length > 0;
}