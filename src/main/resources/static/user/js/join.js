$(document).on('click', '#joinBtn', () => {
    $('#hidUserEmail').val($('#user_email').val())
    $('#hidUserName').val($('#user_name').val())
    $('#hidUserPwd').val($('#user_pwd').val())
    $('#hidTermFlag5').val($('#chk5').is(":checked"));
    $('#hidTermFlag6').val($('#chk6').is(":checked"));
})