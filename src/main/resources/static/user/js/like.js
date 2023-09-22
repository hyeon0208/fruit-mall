$(document).on('click', '.red__heart', (e) => {
    let heart = $(e.currentTarget);
    const productId = heart.data("product-id");
    const userIdNo = heart.data("user-id");

    if(heart.val() == '0') {
        heart.val(1);
        heart.addClass('filled'); // filled 클래스 추가
    } else {
        heart.val(0);
        heart.removeClass('filled'); // filled 클래스 제거
    }

    // 좋아요 취소
    if (heart.val() == 0) {
        axios({
            method: "delete",
            url: "/api/v1/like",
            data: {
                productId: productId,
                userIdNo: userIdNo
            },
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        })
    }

    // 좋아요 활성화
    if (heart.val() == 1) {
        axios({
            method: "post",
            url: "/api/v1/like",
            data: {
                productId: productId,
                userIdNo: userIdNo
            },
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        })
    }
});