function verificarCheckBox() {
    const checkboxes = document.querySelectorAll("input[type=checkbox]");
    var selecionado = false;

    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked){
            selecionado = true;
            break;
        }
    }
    return selecionado;
}

function checkAll(checkBoxAll) {
    const checkboxes = document.querySelectorAll(".child-checkbox");

    checkboxes.forEach(c => {
        c.checked = checkBoxAll.checked;
    });
}

function check() {
    const checkboxes = document.querySelectorAll(".child-checkbox");
    const masterCheckbox = document.getElementById("master-checkbox");

    checkboxes.forEach(c => {
        c.addEventListener("click", function () {
            var allChecked = true;

            for (var j = 0; j < checkboxes.length; j++) {
                if (!checkboxes[j].checked) {
                    allChecked = false;
                    break;
                }
            }
            masterCheckbox.checked = allChecked;
        });
    });
}

check();