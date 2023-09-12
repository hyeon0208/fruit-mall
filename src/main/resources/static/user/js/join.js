function showErrorModal(errorMessage, inputElement) {
    $('#modalErrorMessage').text(errorMessage);
    $('#errorModal').show();
    $('#modalCloseBtn').off('click');
    $('#modalCloseBtn').on('click', function () {
        $('#errorModal').hide();
        inputElement.focus();
    });
}

$(document).on('click', '#joinBtn', (e) => {
    e.preventDefault(); // 폼이 에러 상황에서도 제출되는 것을 방지

    // 에러 메시지를 검사하고 모달을 표시
    if ($('#email-error').text().length > 0) {
        showErrorModal('이메일을 확인 해주세요.', $('#user_email'));
        return;
    }

    if ($('#name-error').text().length > 0) {
        showErrorModal('닉네임을 확인해 주세요.', $('#user_name'));
        return;
    }

    if ($('#pwd-error').is(':visible')) {
        showErrorModal('비밀번호가 일치하지 않습니다.', $('#pwd_check'));
        return;
    }

    // 체크박스 chk5 와 chk6이 선택되지 않았을 경우 모달창을 띄우기
    if (!$('#chk2').is(":checked") || !$('#chk3').is(":checked") || !$('#chk4').is(":checked")) {
        showErrorModal('필수 동의 사항에 체크해야 합니다.', null);
        return;
    }

    if ($('#pwd-regex').text().length > 0) {
        showErrorModal('비밀번호 입력 조건을 다시 확인해주세요.', $('#user_pwd'));
        return;
    }

    // 폼 전송 코드
    $('#hidUserEmail').val($('#user_email').val())
    $('#hidUserName').val($('#user_name').val())
    $('#hidUserPwd').val($('#user_pwd').val())
    $('#hidTermFlag5').val($('#chk5').is(":checked"));
    $('#hidTermFlag6').val($('#chk6').is(":checked"));

    $('#joinForm').submit(); // 폼 제출
})

$(document).ready(function() {
    // 비밀번호 확인 입력 칸 초기 설정
    $('#pwd_check').removeClass('wrong__pw__input');
    $('#pwd-error').hide();

    $("#user_email").on("focusout", () => {
        axios({
            method: "post",
            url: "/join/check-email",
            data: "user_email=" + encodeURIComponent($("#user_email").val()),
            dataType: "String",
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(res => {
            $("#email-error").text(res.data);
        })
    });

    $("#user_name").on("focusout", () => {
        axios({
            method: "post",
            url: "/join/check-name",
            data: { user_name: $("#user_name").val() } ,
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        }).then(res => {
            $("#name-error").text(res.data);
        })
    });

    $("#user_name").on("focusout", () => {
        if ($('#user_name').val().length < 2) {
            $("#name-error").text("닉네임은 2글자 이상을 입력해주세요.");
        }
    });

    $("#user_pwd").on("focusout", () => {
        const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,20}$/;

        if (!($('#user_pwd').val().match(passwordRegex))) {
            $("#pwd-regex").text("영문자 / 숫자 / 특수문자 포함 최소 8자리 이상 20자리 이내로 입력해주세요.");
        }  else {
            $("#pwd-regex").text("");
        }
    });

    $("#pwd_check").on("focusout", () => {
        const userPwd = $('#user_pwd').val();
        const pwdCheck = $('#pwd_check').val();

        // 비밀번호 일치 검사
        if (userPwd === pwdCheck) {
            $('#pwd-error').hide();
            $('#pwd_check').removeClass('wrong__pw__input');
        } else {
            $('#pwd-error').show();
            $('#pwd_check').addClass('wrong__pw__input');
        }
    });

    // 체크박스 전체 선택
    $(document).ready(function() {
        // chk1 체크박스를 클릭했을 때 이벤트 핸들러 추가
        $("#chk1").click(function() {
            // chk1 체크박스의 체크 여부에 따라 모든 체크박스 상태를 변경
            const isChecked = $(this).prop("checked");
            $("input[type='checkbox'][name='status']").prop("checked", isChecked);
            $("input[type='checkbox'][name='term_flag5']").prop("checked", isChecked);
            $("input[type='checkbox'][name='term_flag6']").prop("checked", isChecked);
        });
    });
});