const navbar = document.querySelectorAll('.nav-link');
const hrefPage = window.location.href;
if(hrefPage.includes('home')){
    const navItem = navbar.item(0);
    navItem.classList.add('navbarItemUpdated');
    deleteNeedlessClass(navItem);
}
else if(hrefPage.includes('billing')){
    const navItem = navbar.item(1);
    navItem.classList.add('navbarItemUpdated');
    deleteNeedlessClass(navItem);
}
else if(hrefPage.includes('editDate')){
    const navItem = navbar.item(2);
    navItem.classList.add('navbarItemUpdated');
    deleteNeedlessClass(navItem);
}
else if(hrefPage.includes('showCards')){
    const navItem = navbar.item(3);
    navItem.classList.add('navbarItemUpdated');
    deleteNeedlessClass(navItem);
}
else if(hrefPage.includes('cartAction')){
    const navItem = navbar.item(4);
    navItem.classList.add('navbarItemUpdated');
    deleteNeedlessClass(navItem);
}
function deleteNeedlessClass(navItem) {
    navbar.forEach(nav => {
        if(nav !== navItem){
            nav.classList.remove('navbarItemUpdated');
        }
    });
}