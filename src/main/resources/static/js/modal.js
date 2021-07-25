const modalOpenBtn = document.querySelector('.modal_open_btn');
const modal = document.querySelector('.modal');
const blur = document.querySelector('.blur_shadow');


modalOpenBtn.addEventListener('click', ()=> {
    blur.classList.add('shadow_show')
    modal.classList.add('modal_show')
})

blur.addEventListener('click', ()=> {

    modal.classList.remove('modal_show') //모달을 숨긴다

    let input = document.querySelectorAll('.modal input');
    for (let i = 0; i < input.length; i++) {//인풋안에 값을 삭제한다
        const o = input[i];
        o.value=''
    }
    setTimeout(() => {
        blur.classList.remove('shadow_show') //그림자를 숨긴다
    }, 400);
    
})