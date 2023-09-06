$(document).on("click", ".showReview", (e) => {
    const productId = $(e.currentTarget).siblings(".reAddToCartBtn").data("product-id");
    window.location.href=`/user/review/${productId}`
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