var activePage = window.location.href;
var navLinks = document.querySelectorAll('.nav-link');

navLinks.forEach(item => {
    if (activePage.includes(item.href)) {
        item.classList.add('active');
    }
});
