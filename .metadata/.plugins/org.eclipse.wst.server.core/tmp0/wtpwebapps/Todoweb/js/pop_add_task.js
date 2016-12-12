$(document).ready(function () {
  $('#add_self_task').hide()
  $('#add_team_task').hide()
  $('#covered').hide()
  $('#task_calender').hide()
  $('#setting_group').hide()
  $('#triangle-up').hide()

  $('#add_task').click(function () {
    $('#add_self_task').show()
    $('#covered').show()
  })
  $('#task_edit_button_save').click(function () {
    $('#add_self_task').hide()
    $('#covered').hide()
  })
  $('#task_edit_button_cancle').click(function () {
    $('#add_self_task').hide()
    $('#covered').hide()
  })
  $('#top_left').click(function () {
    $('#setting_group').slideToggle('0.8s')
    $('#triangle-up').slideToggle('0.8s')
  })
  flatpickr('.flatpickr', {
    enableTime: true
  })
  $('#self_task_occupy').click(function(){
    $('#add_self_task').show()
    $('#covered').show()
  })
  $('#team_task_occupy').click(function(){
    $('#add_self_task').show()
    $('#covered').show()
  })
})
