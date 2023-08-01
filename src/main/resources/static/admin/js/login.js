$(document).ready(function () {
    $("#adminLoginBtn").on("click", (e) => {
        e.preventDefault(); // 폼이 에러 상황에서도 제출되는 것을 방지

        axios({
            method: "post",
            url: "/admin/login",
            data: { id: $("#id").val(), password: $("#pw").val() },
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        }).then(res => {
            if (res.data === "fail") {
                $('.txt05').show();
                $('.txt05 a').click(function() {
                    $('.txt05').hide();
                });
            }
            if (res.data === "success") {
                window.location.href = "/admin/dashboard";
            }
        })
    });
});