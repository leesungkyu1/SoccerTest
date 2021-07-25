let select = document.querySelectorAll('.select');

for (let i = 0; i < select.length; i++) {
    const s = select[i];
    s.addEventListener('input',()=> {
        s.style.color = '#fff';
    })
}
