const container = document.getElementById('edit_container');
const edit_form = document.getElementById('edit_form');

const nav_height = 40;
edit_form.style.top = container.clientHeight / 2 - edit_form.offsetTop - nav_height  + 'px';
edit_form.style.transform = 'translateY(-50%)';