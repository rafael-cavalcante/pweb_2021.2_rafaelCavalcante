function selectAllCheckboxs() {
    const checkboxes = document.querySelectorAll('.checkbox');

    checkboxes.forEach(checkbox => {
        checkbox.setAttribute('checked', '');
    });
}

function verificarCheckBox() {
    const checkboxes = document.querySelectorAll('.child-checkbox:checked');

    if (checkboxes.length < 1) {
        return false;
    }
    return true;
}

function checkAll(checkBoxAll) {
    const checkboxes = document.querySelectorAll(".child-checkbox:not(checked)");

    checkboxes.forEach(c => {
        c.checked = checkBoxAll.checked;
    });
}

function check() {
    const checkboxes = document.querySelectorAll(".child-checkbox");

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
