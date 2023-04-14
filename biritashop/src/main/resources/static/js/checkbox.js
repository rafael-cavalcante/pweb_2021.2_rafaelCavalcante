function verificarCheckBox() {
    const checkboxes = document.querySelectorAll(".child-checkbox");
    var selecionado = false;

    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
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
            ative();
        });
    });
}

function ative() {
    const checkboxes = document.querySelectorAll(".child-checkbox");
    const masterCheckbox = document.getElementById("master-checkbox");

    var allChecked = true;

    for (let checkbox of checkboxes) {
        if (!checkbox.checked) {
            allChecked = false;
            break;
        }
    }
    masterCheckbox.checked = allChecked;
}

check();
ative();
