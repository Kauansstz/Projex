document.addEventListener('DOMContentLoaded', () => {
    const button = document.getElementById('profileButton');
    const menu = document.getElementById('profileMenu');

    button.addEventListener('click', (e) => {
        e.stopPropagation();
        menu.classList.toggle('hidden');
    });

    document.addEventListener('click', () => {
        menu.classList.add('hidden');
    });

    menu.addEventListener('click', (e) => {
        e.stopPropagation();
    });
});
