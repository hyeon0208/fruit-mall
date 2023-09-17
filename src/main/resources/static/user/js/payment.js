$(() =>{
    let pg = "";
    let payMethod = "";

    $('#cartPay').on("click", () => {
        pg = "html5_inicis";
        payMethod = "card"
    });

    $('#phonePay').on("click", () => {
        pg = "danal";
        payMethod = "MOBILE";
    });

    $('#kakaoPay').on("click", () => {
        pg = "kakaopay";
        payMethod = "card"
    });

    $(document).on("click", "#pay__do", () => {
        const isChecked1 = $("#1번").prop("checked");
        const isChecked2 = $("#2번").prop("checked");

        if (!isChecked1 || !isChecked2) {
            alert("약관에 동의하셔야 구매가 가능합니다.")
            return;
        }

        if (!payMethod) {
            alert("결제 수단을 선택해주세요.")
            return;
        }

        let name = $("#orderProductNames").text();
        if ($("#orderProductName").text()) {
            name = $("#orderProductName").text();
        }

        let orders = [];
        const orderNumber = createOrderNum();

        $('.orderProductPrice').each((i, e) => {
            let order = {};
            const price = parseInt($(e).text().replace(/[^0-9]/g, ''));
            let discountRate = parseInt($('.orderProductDiscount').eq(i).text().replace(/[^0-9]/g, ''));

            if (!discountRate) {
                discountRate = 0;
            }

            order.productId = $(".td_wrap").eq(i).data("order-product-id")
            order.orderPrice = price - (price * (discountRate / 100));
            order.orderCount = parseInt($('.orderProductCount').eq(i).text().replace(/[^0-9]/g, ''));
            order.receiverName = $("#name").val();
            order.phoneNumber = $("#phone1").val();
            order.orderNumber = orderNumber;
            order.zipcode = parseInt($("#add").val());
            order.address = $("#add2").val();
            order.orderRequired = $("#ask").val();
            order.paymentMethod = payMethod;
            order.orderDiscount = discountRate;
            order.productImage =  $('.td_wrap img').eq(i).attr('src');
            orders.push(order);
        });

        IMP.init('imp60132334');
        IMP.request_pay({
            pg: pg,
            pay_method : payMethod,
            merchant_uid : orderNumber,
            name : name,
            amount: $("#paymentPrice").val(), // 결제 가격
            buyer_name : $('#name').val(),
            buyer_tel : $('#phone1').val(),
            buyer_postcode : $('#add').val(),
            buyer_addr : $('#add2').val()
        }, function(rsp) {
            if (rsp.success) {
                axios({
                    method: "post",
                    url: `/payment/validation/${rsp.imp_uid}`
                }).then(res => {
                    if ($("#paymentPrice").val() == res.data.response.amount) {
                        axios({
                            url: "/order/payment",
                            method: "post",
                            data: orders,
                            dataType: "json",
                            headers: {'Content-Type': 'application/json'}
                        }).then(res => {
                            let productIds = orders.map(order => order.productId);
                            return axios({
                                url: "/cart/delete/pay/success",
                                method: "post",
                                data: productIds
                            }).then(res => {
                                const quota = rsp.card_quota;
                                let quotaInfo;
                                const merchantUid = rsp.merchant_uid
                                const payMethod = getPayMethod(rsp.pay_method);
                                const payPrice = rsp.paid_amount;

                                if (quota === 0) {
                                    quotaInfo = "일시불"
                                } else {
                                    quotaInfo = quota + "개월"
                                }

                                let msg = '결제가 완료되었습니다.';
                                msg += '고유ID : ' + rsp.imp_uid;
                                msg += '상점 거래ID : ' + merchantUid;
                                msg += '결제 금액 : ' + payPrice;
                                msg += '카드 승인번호 : ' + rsp.apply_num;
                                alert(msg)
                                window.location.href=`/confirm/payment?quotaInfo=${quotaInfo}&merchantUid=${merchantUid}&payMethod=${payMethod}&payPrice=${payPrice}`;
                            });
                        }).catch(error => {
                            alert(JSON.stringify(error.response.data))
                        });
                    }
                }).catch(error => {
                    alert('결제에 실패하였습니다. ' + rsp.error_msg);
                });
            } else {
                alert(rsp.error_msg);
            }
        });
    })
});


$(document).on("click", ".paymentButtons button", (e) => {
    $('.paymentButtons button').css({
        'background-color': 'white',
        'color': 'black'
    });

    $(e.currentTarget).css({
        'background-color': 'black',
        'color': 'white'
    });
});

$(document).on("click", "#pay__cancel", () => {
    $(".payment__cancel").show();

    $('.payment__cancel #paypage-cancel').click(() => {
        $('.payment__cancel').hide();
        $(".payment__cancel__confirm").show()
        $('.payment__cancel__confirm button').click(() => {
            $('.payment__cancel__confirm').hide();
            window.location.href = "/";
        });
    });

    $('.payment__cancel #paypage-continue').click(() => {
        $('.payment__cancel').hide();
    });
});

function getPayMethod(payMethod) {
    if (payMethod == "point") {
        return "포인트결제"
    }
    if (payMethod == "phone") {
        return "휴대폰결제"
    }
    return "신용카드";
}

function createOrderNum() {
    const date = new Date();
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");

    let orderNum = year + month + day;
    for (let i = 0; i < 5; i++) {
        orderNum += Math.floor(Math.random() * 8);
    }
    return parseInt(orderNum);
}