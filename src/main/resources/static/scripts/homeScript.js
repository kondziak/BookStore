const bookItems = document.querySelectorAll('.book_row');
checkBookItems();
window.addEventListener('scroll',checkBookItems);

function checkBookItems(){
    const triggerBottom = window.innerHeight * 4 /5;
    bookItems.forEach(document => {
        const itemTop = document.getBoundingClientRect().top;
        if(itemTop < triggerBottom){
            document.classList.add('show');
        }
        else{
            document.classList.remove('show');
        }
    });
}