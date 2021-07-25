let shadow = document.querySelector('.shadow');

// 다운버튼 누르면 팝업 다운
let downBtn = document.querySelector('.down-btn');
downBtn.addEventListener('click', ()=> {
    document.querySelector('.popup').classList.remove('popup_up')
    shadow.classList.toggle('shadow_show')
})
// 맴버 누르면 팝업 업
let player = Array.prototype.slice.call(document.querySelectorAll('.pop_up_btn'));
for (let i = 0; i < player.length; i++) {
    const p = player[i];
    p.addEventListener('click', ()=> {
        document.querySelector('.popup').classList.add('popup_up')
        shadow.classList.toggle('shadow_show')
    })
}