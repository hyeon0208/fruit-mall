function showLoginErrorModal(errorMessage, inputElement) {
    $('#modalErrorMessage').text(errorMessage);
    $('#errorModal').show();
    $('#modalCloseBtn').off('click');
    $('#modalCloseBtn').on('click', function () {
        $('#errorModal').hide();
        inputElement.focus();
    });
}

$(document).ready(() => {
    $("#id").on("focusout", function() {
        axios({
            method: "post",
            url: "/user/check-login",
            data: "user_email=" + encodeURIComponent($("#id").val()),
            dataType: "String", // 응답 데이터 타입
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        })
    });

    $("#loginBtn").on("click", (e) => {
        e.preventDefault(); // 폼이 에러 상황에서도 제출되는 것을 방지

        // 로그인 요청을 하고 결과에 따라 페이지 이동 또는 에러 모달 표시
        axios({
            method: "post",
            url: "/user/login",
            data: {
                user_email: $("#id").val(),
                user_pwd: $("#pw").val()
            },
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        }).then(res => {
            if (res.data === "success") {
                let localCarts = localStorage.getItem('cart') ? JSON.parse(localStorage.getItem('cart')) : [];
                axios({
                    method: "post",
                    url: "/user/update-cart",
                    data: { localCarts },
                    headers: {'Content-Type': 'application/json'}
                }).then(() => {
                    window.location.replace("/");
                    localStorage.removeItem('cart');
                });
            } else {
                const errorElement = $('#id-error').text().length > 0 ? $('#id') : $('#pw');
                showLoginErrorModal('이메일 또는 비밀번호가 일치하지 않습니다.', errorElement);
            }
        });
    });
});