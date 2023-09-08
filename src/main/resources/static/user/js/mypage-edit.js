$(() => {
    $("#mypageEdit .button button:eq(0)").on("click", (e) => {
        e.preventDefault();
        console.log($("#mypage-edit-password").val());
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
});