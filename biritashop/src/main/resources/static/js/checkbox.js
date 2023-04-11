// Get references to the checkboxes
var masterCheckbox = document.getElementById("master-checkbox");
var childCheckboxes = document.getElementsByClassName("child-checkbox");

// Add an event listener to the master checkbox
masterCheckbox.addEventListener("click", function () {
    // Loop through all child checkboxes and toggle their state
    for (var i = 0; i < childCheckboxes.length; i++) {
        childCheckboxes[i].checked = masterCheckbox.checked;
    }
});

// Add event listeners to the child checkboxes
for (var i = 0; i < childCheckboxes.length; i++) {
    childCheckboxes[i].addEventListener("click", function () {
        // Check if all child checkboxes are checked
        var allChecked = true;
        for (var j = 0; j < childCheckboxes.length; j++) {
            if (!childCheckboxes[j].checked) {
                allChecked = false;
                break;
            }
        }
        // Set the state of the master checkbox
        masterCheckbox.checked = allChecked;
    });
}

function verificarCheckBox() {
    let checkboxes = document.querySelectorAll('input[type=checkbox]');
    let selecionado = false;

    for (let i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            selecionado = true;
            break;
        }
    }

    if (!selecionado) {
        alert("Selecione pelo menos uma produto.");
        return false;
    }
    return true;
}