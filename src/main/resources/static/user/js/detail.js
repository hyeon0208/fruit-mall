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