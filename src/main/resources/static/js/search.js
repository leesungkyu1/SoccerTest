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
  
  // 검색이벤트
  let search = document.querySelector('.search');
  let searchInput = document.querySelector('.search_input');
  let searchText = document.querySelector('.search_input input');
  
  search.addEventListener(mousedownEvent, ()=>{
    searchInput.classList.toggle('search_show');
  })
  