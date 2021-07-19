var mousedownEvent = (function() {
  if ('ontouchstart' in document.documentElement === true) {
    return 'touchstart';
  } else {
    return 'mousedown';
  }
})();

var mousemoveEvent = (function() {
  if ('ontouchstart' in document.documentElement === true) {
    return 'touchmove';
  } else {
    return 'mousemove';
  }
})();

var mouseupEvent = (function() {
  if ('ontouchstart' in document.documentElement === true) {
    return 'touchend';
  } else {
    return 'mouseup';
  }
})();

//슬라이드 이벤트

const slider = document.querySelectorAll('.slide');
let isMouseDown = false;
let startX, scrollLeft;

for (let i = 0; i < slider.length; i++) {
  const o = slider[i];
  
  o.addEventListener(mousedownEvent, (e) => {
    isMouseDown = true;
    o.classList.add('active');
  
    startX = e.pageX - o.offsetLeft;
    scrollLeft = o.scrollLeft;
  });
  
  o.addEventListener('mouseleave', () => {
    isMouseDown = false;
  });
  
  o.addEventListener(mouseupEvent, () => {
    isMouseDown = false;
  });
  
  o.addEventListener(mousemoveEvent, (e) => {
    if (!isMouseDown) return;
  
    e.preventDefault();
    const x = e.pageX - o.offsetLeft;
    const walk = (x - startX) * 1;
    o.scrollLeft = scrollLeft - walk;
  });
}






