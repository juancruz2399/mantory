//Time Picker Iniatialization
$('#input_starttime').pickatime({});
// Example starter JavaScript for disabling form submissions if there are invalid fields
(function () {
  'use strict'

  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  var forms = document.querySelectorAll('.needs-validation')

  // Loop over them and prevent submission
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }

        form.classList.add('was-validated')
      }, false)
    })
})()
$.extend($.fn.datepicker.defaults,{
  monthsFull: ['Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre'],
  weekdaysShort:['Lunes','Martes','Miercoles','Jueves','Viernes','Sábado','Domingo']
  today: 'día',
  clear: 'borrar',
  formatSubmit: 'yyyy/mm/dd'
  
})
$('.datepicker').datepicker({
  monthsFull:['Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre'],
  weekdaysShort:['Lunes','Martes','Miercoles','Jueves','Viernes','Sábado','Domingo']
  clear:'borrar',
  formatSubmit:'yyyy/mm/dd'
})
