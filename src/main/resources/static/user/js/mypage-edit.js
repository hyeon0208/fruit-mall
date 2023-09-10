$(() => {
    $("#mypageEdit .button button:eq(0)").on("click", (e) => {
        e.preventDefault();
        axios({
            url: "/user/mypage/password/confirm",
            method: "post",
            data: { inputPwd: $("#mypage-edit-password").val() },
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        }).then(res => {
            if (res.data == "success") {
                window.location.href = "/user/mypage/userinfo/edit";
            } else {
                alert(res.data);
            }
        }).catch(error => {
            alert(error.data);
        });
    });

    // 비밀번호 변경하기 버튼
    $(".change__PW .button__box button").on("click", () => {
        $(".change__PW").hide();
        $(".change__PW__button").show();
    });

    // 비밀번호 변경 취소 버튼
    $(".change__PW__button .button__box button").on("click", () => {
        $(".change__PW").show();
        $(".change__PW__button").hide();

        $('#pwd1').val("");
        $('#pwd2').val("");
    });

    // 취소하기 버튼
    $(".mypageEdit__button button:eq(0)").on("click", (e) => {
        e.preventDefault();
        $(".mypageEdit__cancel").show();
        $(".mypageEdit__cancel .mypageEdit__cancel__buttons button:eq(0)").on("click", (e) => {
            e.preventDefault();
            window.location.href = "/";
        });
        $(".mypageEdit__cancel .mypageEdit__cancel__buttons button:eq(1)").on("click", (e) => {
            e.preventDefault();
            $(".mypageEdit__cancel").hide();
        });
    });

    // 수정완료 버튼
    $(".mypageEdit__button button:eq(1)").on("click", (e) => {
        e.preventDefault();

        if (!$("#check-name-name-confirm").is(":visible")) {
            return alert("변경 버튼을 클릭 후, 닉네임 중복 검사를 해주세요.")
        }

        if ($(".error-message").is(":visible")) {
            return alert("입력 항목을 다시 확인해주세요.");
        }

        axios({
            url: "/user/mypage/userinfo-update",
            method: "post",
            data: {
                userEmail: $("#email").val(),
                userName: $("#name").val(),
                userPwd: $('#pwd2').val()
            },
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        }).then(res => {
            $(".mypageEdit__confirm").show();
            $(".mypageEdit__confirm .mypageEdit__confirm__buttons button").on("click", (e) => {
                e.preventDefault();
                window.location.href = "/";
            });
        });
    });

    // 변경 버튼 클릭
    $("#check-name").on("click", () => {
        axios({
            url: "/user/mypage/name-check",
            method: "post",
            data: { userName: $("#name").val() },
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        }).then(res => {
            if (res.data == "success") {
                $("#check-name-name-confirm").show();
                $("#check-name-error").hide();
            } else {
                $("#check-name-name-confirm").hide();
                $("#check-name-error").show();
            }
        });
    });

    // 비밀번호 입력 조건 체크
    $("#pwd1").on("focusout", () => {
        const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,20}$/;
        if (!($('#pwd1').val().match(passwordRegex))) {
            $("#pwd-regex").show();
        }  else {
            $("#pwd-regex").hide();
        }
    });

    // 비밀번호 확인 체크
    $("#pwd2").on("focusout", () => {
        const userPwd = $('#pwd1').val();
        const pwdCheck = $('#pwd2').val();

        if (userPwd === pwdCheck) {
            $("#pwd-error").hide();
        } else {
            $("#pwd-error").show();
        }
    });
});