$(document).on('click', '#joinBtn', () => {
    $('#hidUserEmail').val($('#user_email').val())
    $('#hidUserName').val($('#user_name').val())
    $('#hidUserPwd').val($('#user_pwd').val())
    $('#hidTermFlag5').val($('#chk5').is(":checked"));
    $('#hidTermFlag6').val($('#chk6').is(":checked"));
})

$(document).ready(function() {
    $("#user_email").on("focusout", function () {
        axios({
            method: "post",
            url: "/user/check-email",
            data: { user_email: $(this).val()},
            dataType: "json", // 응답 데이터 타입
            headers: {'Content-Type': 'application/json'}
        }).then(res => {
            $("#email-error").text(res.data);
        })
    });

    $("#user_name").on("focusout", function () {
        axios({
            method: "post",
            url: "/user/check-name", // "/alert" URL 로 요청
            data: { user_name: $(this).val() } ,
            dataType: "json", // 응답 데이터 타입
            headers: {'Content-Type': 'application/json'} // 요청 헤더에 JSON 형식으로 데이터 전송
        }).then(res => {
            $("#name-error").text(res.data);
        })
    });

    // 체크박스 전체 선택
    $(".checkboxs").on("click", "#all__agreed", function () {
        const checked = $(this).is(":checked");

        if(checked){
            $(this).parents(".checkboxs").find('input').prop("checked", true);
        } else {
            $(this).parents(".checkboxs").find('input').prop("checked", false);
        }
    });
});