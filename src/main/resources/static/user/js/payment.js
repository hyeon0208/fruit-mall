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
            order.orderStatus = "결제완료";
            order.paymentMethod = payMethod;
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
                    url: `/user/order/verify_iamport/${rsp.imp_uid}`
                }).then(res => {
                    if ($("#paymentPrice").val() == res.data.response.amount) {
                        axios({
                            url: "/user/order/add",
                            method: "post",
                            data: orders,
                            dataType: "json",
                            headers: {'Content-Type': 'application/json'}
                        }).then(res => {
                            let productIds = orders.map(order => order.productId);
                            axios({
                                url: `/cart/delete/pay/success`,
                                method: "post",
                                data: {
                                    userIdNo: res.data,
                                    productIds: productIds
                                }
                            })
                            let msg = '결제가 완료되었습니다.';
                            msg += '고유ID : ' + rsp.imp_uid;
                            msg += '상점 거래ID : ' + rsp.merchant_uid;
                            msg += '결제 금액 : ' + rsp.paid_amount;
                            msg += '카드 승인번호 : ' + rsp.apply_num;
                            alert(msg)
                        }).catch(error => {
                            alert("주문정보 저장을 실패 했습니다.")
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