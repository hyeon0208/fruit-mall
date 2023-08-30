$(() => {
    const price =  parseInt($("#detailTotalPrice").text().replace(/[^0-9]/g, ''));
    if (price >= 50000) {
        $("#detailDeliveryFee").text("무료");
    } else {
        $("#detailDeliveryFee").text("3,000원");
    }
});

$(document).on("click", "#increaseDetailProductCnt", () => {
    let cnt = $("#detailProductCnt");
    let decreaseBtn = $("#decreaseDetailProductCnt");
    let value = parseInt(cnt.val());
    cnt.text(value + 1);
    cnt.val(value + 1);

    if (parseInt(cnt.val()) > 1) {
        decreaseBtn.show();
    }
    updateDetailTotalPrice();
    updateDetailDeliveryFee();
});

$(document).on("click", "#decreaseDetailProductCnt", () => {
    let cnt = $("#detailProductCnt");
    let decreaseBtn = $("#decreaseDetailProductCnt");
    let value = parseInt(cnt.val());

    if (parseInt(cnt.val()) > 1) {
        cnt.text(value - 1);
        cnt.val(value - 1);

        if (parseInt(cnt.val()) <= 1) {
            decreaseBtn.hide();
        }
    }
    updateDetailTotalPrice();
    updateDetailDeliveryFee();
});

$(document).on("click", "#addDetailToCartBtn", () => {
    let product = $("#addDetailToCartBtn");
    const productId = product.data("cart-product-id");
    const userIdNo = product.data("user-id");

    if(product.attr("data-btn-status") == 0) {
        product.attr("data-btn-status", 1);
    }

    // 로그인 시
    if (userIdNo != 0) {
        // 장바구니 담기
        if (product.attr("data-btn-status") == 1) {
            let localCart = localStorage.getItem('cart') ? JSON.parse(localStorage.getItem('cart')) : [];
            axios({
                method: "post",
                url: "/main/cart/add",
                data: {
                    productId: productId,
                    userIdNo: userIdNo,
                    productCount : parseInt($("#detailProductCnt").val()),
                    localCart: localCart
                },
                dataType: "json",
                headers: {'Content-Type': 'application/json'}
            }).then((res) =>{
                $('.txt04.right__modal.add__detail_cart').show();
                $('#detailCartModalClose').click(() => {
                    $('.txt04.right__modal.add__detail_cart').hide();
                });
            }).catch((res) => {
                $('.txt04.right__modal.exist__detail__cart').show();
                $('#existDetailCartModalClose').click(() => {
                    $('.txt04.right__modal.exist__detail__cart').hide();
                });
            });
        }
    }

    // 비로그인 시
    if (userIdNo == 0) {
        axios({
            method: "get",
            url: `/local/cart/${productId}`,
            params: {
                productId: productId
            },
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        }).then((res) =>{
            const product = res.data
            product.productCount = parseInt($("#detailProductCnt").val());
            const cartItem = localStorage.getItem('cart');
            let exist = false;

            if (cartItem !== null) {
                exist = JSON.parse(cartItem).find(p => p.product.productId === product.productId);
            }

            if (exist) {
                $('.txt04.right__modal.exist__detail__cart').show();
                $('#existDetailCartModalClose').click(() => {
                    $('.txt04.right__modal.exist__detail__cart').hide();
                });
            } else {
                addToLocalStorageCart(product);
                $('.txt04.right__modal.add__detail_cart').show();
                $('#detailCartModalClose').click(() => {
                    $('.txt04.right__modal.add__detail_cart').hide();
                });
            }
        })
    }
});

$(document).on("click", "#goPaymentOneBtn", () => {
    const productId = $("#addDetailToCartBtn").data("cart-product-id");
    const productCount = parseInt($("#detailProductCnt").val());
    window.location.replace(`/user/order/one/${productId}/${productCount}`);
});

function updateDetailTotalPrice() {
    const discount = $("#detailDiscount").attr("data-detail-discount");
    let price = 0;
    const cnt = parseInt($("#detailProductCnt").text());

    if (!discount) {
        price = parseInt($("#NotDiscountAppliedPrice").text().replace(/[^0-9]/g, ''));
    } else {
        price = parseInt($("#DiscountAppliedPrice").text().replace(/[^0-9]/g, ''));
    }
    $("#detailTotalPrice").text((price * cnt).toLocaleString() + "원");
}

function updateDetailDeliveryFee() {
    const totalPrice = parseInt($("#detailTotalPrice").text().replace(/[^0-9]/g, ''));
    if (totalPrice >= 50000) {
        $("#detailDeliveryFee").text("무료");
    } else {
        $("#detailDeliveryFee").text("3,000원");
    }
}