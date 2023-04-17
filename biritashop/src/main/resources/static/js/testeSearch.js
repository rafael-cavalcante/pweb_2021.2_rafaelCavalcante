var inputSearch = document.getElementById('#inputSearch');

inputSearch.addEventListener('input', function(){

    var tableDates = document.querySelectorAll('.tableDate');

    for(let tableDate of tableDates){
        if(tableDate.textContent.includes(inputSearch.value)){
            document.getElementById(tableDate.id).scrollIntoView();
        }
        break;
    }
});