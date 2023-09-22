$(document).on('click', '#changePwBtn', (e) => {
    e.preventDefault(); // 폼이 에러 상황에서도 제출되는 것을 방지

    if ($('#regex-error').text().length > 0) {
        $('.txt05').show();
        $('.txt05 a').click(() => {
            $('.txt05').hide();
            $('#newpw').focus();
        });
        return;
    }

    // 에러 메시지를 검사하고 모달을 표시
    if ($('#check-error').text().length > 0) {
        $('.txt06').show();
        $('.txt06 a').click(() => {
            $('.txt06').hide();
            $('#newpw-check').focus();
        });
        return;
    }

    axios({
        method: "patch",
        url: "/api/v1/password",
        data: { user_pwd: $("#newpw").val(), user_email: $("#user_email").val() },
        dataType: "json",
        headers: {'Content-Type': 'application/json'}
    }).then(res => {
        if (res.data === "success") {
            $('.txt04').show();
            $('.txt04 a').click(function () {
                $('.txt04').hide();
            });
        }
    })
});

$(document).ready(function() {
    $("#newpw").on("focusout", () => {
        const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,20}$/;
        if (!($('#newpw').val().match(passwordRegex))) {
            $("#regex-error").text("영문자 / 숫자 / 특수문자 포함 최소 8자리 이상 20자리 이내로 입력해주세요.");
        }  else {
            $("#regex-error").text("");
        }
    });

    $("#newpw-check").on("focusout", () => {
        const userPwd = $('#newpw').val();
        const pwdCheck = $('#newpw-check').val();

        // 비밀번호 일치 검사
        if (userPwd === pwdCheck) {
            $("#check-error").text("");
        }  else {
            $("#check-error").text("비밀번호가 일치하지 않습니다.");
        }
    });
});