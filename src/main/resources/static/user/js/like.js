$(document).on('click', '.red__heart', (e) => {
    let heart = $(e.currentTarget);
    const productId = heart.closest('li').find('#recentProductBtn').data('user-product-id');
    const userIdNo = heart.data("user-id");

    if(heart.val() == '0') {
        heart.val(1);
        heart.addClass('filled'); // filled 클래스 추가
    } else {
        heart.val(0);
        heart.removeClass('filled'); // filled 클래스 제거
    }
    console.log(heart.val());
    console.log(productId)
    console.log(userIdNo)

    // 좋아요 취소
    if (heart.val() == 0) {
        axios({
            method: "post",
            url: "/like/disabled",
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
            url: "/like/activate",
            data: {
                productId: productId,
                userIdNo: userIdNo
            },
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        })
    }
});