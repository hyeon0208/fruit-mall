$(() => {
    $("#mypageReviewContent").on("input", () => {
        const inputLength = $("#mypageReviewContent").val().length;
        $('.mypage__content__count').text(inputLength + "/500");
    });
});

$(document).on("click", ".showReview", (e) => {
    const productId = $(e.currentTarget).siblings(".reAddToCartBtn").data("product-id");
    window.location.href=`/user/review/${productId}`
});

$(document).on("click", ".writeReviewAtMyPage", (e) => {
    $(".txt05").show();

    // 작성취소
    $(".txt05 .buttons button:eq(0)").on("click", () => {
        $(".txt06").css("z-index", 10).show();
        $(".txt06 .buttons button:eq(0)").on("click", () => {
            $(".txt06, .txt05").hide();
        });

        $(".txt06 .buttons button:eq(1)").on("click", () => {
            $(".txt06").hide();
        });
    });

    // 리뷰등록
    $(".txt05 .buttons button:eq(1)").on("click", () => {
        const mypageReviewContent = $("#mypageReviewContent").val();
        const productId = $(e.currentTarget).siblings(".reAddToCartBtn").data("product-id");

        if (mypageReviewContent.length > 500 || mypageReviewContent.length < 10) {
            return alert("10자 이상 500자 미만으로 작성해주세요.");
        }

        axios({
            method: "post",
            url: "/review/add",
            data: {
                productId: productId,
                reviewContents: mypageReviewContent
            },
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        }).then(res => {
            $(".txt07").css("z-index", 10).show();
            $(".txt07 .buttons button").on("click", () => {
                $(".txt07, .txt05").hide();
                window.location.reload();
            });
        }).catch(err => {
            alert("리뷰작성에 실패했습니다.")
        });
    });
});

$(document).on("click", ".group2", (e) => {
    $(e.currentTarget).next(".deliveryInfo").slideToggle();
});

$(document).on("click", ".reAddToCartBtn", (e) => {
    const btn = $(e.currentTarget);
    const productId = btn.data("product-id");
    const count = btn.data("count");

    axios({
        method: "post",
        url: "/main/cart/add",
        data: {
            productId: productId,
            productCount: count
        },
        dataType: "json",
        headers: {'Content-Type': 'application/json'}
    }).then((res) =>{
        $(".txt04.right__modal.add__cart").show();
        $('#closeCartModal').click(() => {
            $(".txt04.right__modal.add__cart").hide();
        });
    }).catch((res) => {
        $(".txt04.exist__mypage__cart").show();
        $("#existCartModalClose").click(() => {
            $(".txt04.exist__mypage__cart").hide();
        });
    });
});