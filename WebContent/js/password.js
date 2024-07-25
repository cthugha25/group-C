document.addEventListener('DOMContentLoaded', function(event) {

  const targetElement = document.getElementById('password');
  const triggerElement = document.getElementById('chk_d_ps');

  triggerElement.addEventListener('change', function(event) {
    if ( this.checked ) {
      targetElement.setAttribute('type', 'text');
    } else {
      targetElement.setAttribute('type', 'password');
    }
  }, false);

}, false);