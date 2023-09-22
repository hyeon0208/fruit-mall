$(() => {
    let totalPrice = 0;
    $(".orderMultipliedProductPrice").each((i, e) => {
        const price = parseInt($(e).text().replace(/[^0-9]/g, ''));
        totalPrice += price;
    });
    $("#orderTotalPrice").text(totalPrice.toLocaleString() + "원");

    let totalDiscount = 0;
    $(".orderDiscountAppliedPrice").each((i, e) => {
        let discount = parseInt($(e).text().replace(/[^0-9]/g, ''));
        totalDiscount += discount;
    });
    $("#orderTotalDiscount").text("-" + totalDiscount.toLocaleString() + "원");

    if (totalPrice - totalDiscount >= 50000) {
        $("#orderDeliveryFee").text("무료");
    } else {
        $("#orderDeliveryFee").text("3,000원");
    }

    let orderDeliveryFee = $("#orderDeliveryFee").text();
    if (orderDeliveryFee === "무료") {
        orderDeliveryFee = 0;
    } else {
        orderDeliveryFee = 3000;
    }
    $("#paymentExpectedPrice").text((totalPrice - totalDiscount + orderDeliveryFee).toLocaleString() + "원");
    $("#paymentPrice").val(totalPrice - totalDiscount + orderDeliveryFee);
    $("#paymentPrice").text((totalPrice - totalDiscount + orderDeliveryFee).toLocaleString() + "원");

    $('#where').change((e) => {
        const selectedDeliveryName = $(e.currentTarget).val();

        axios({
            method: "get",
            url: `/api/v1/delivery/${encodeURIComponent(selectedDeliveryName)}`
        }).then(res => {
            const delivery = res.data;
            $('#name').val(delivery.userName);
            $('#phone1').val(delivery.phoneNumber);
            $('#add').val(delivery.zipcode);
            $('#add2').val(delivery.address);
        });
    });

    $('#ask').change((e) => {
        if ($(e.currentTarget).val() == "직접입력") {
            $('#customAsk').show();
        } else {
            $('#customAsk').hide();
        }
    });

    clickDeliveryModal();
});

$(document).on("click", "#전체동의", () => {
    const isChecked = $("#전체동의").prop("checked");
    $("input[type='checkbox']:not(#paymentMethodChk)").prop("checked", isChecked);
});

function clickDeliveryModal() {
    $(document).on("click", "#newDelivery", () => {
        $(".delivery__add").show();
    });

    $(document).on("click", "#delivery-add-cancel-btn", () => {
        $('.delivery__add').hide();
    });

    $(document).on("click", "#delivery_add_btn", () => {
        axios({
            url: "/api/v1/delivery",
            method: "post",
            data: {
                deliveryName: $("#title").val(),
                userName: $("#addname").val(),
                phoneNumber: $("#addphone1").val() + $("#addphone2").val(),
                zipcode: $("#zipcode").val(),
                address: $("#addaddress").val() + " " + $("#detailAddaddress").val()
            },
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        }).then(res => {
            if (res.data == "success") {
                $('.delivery__add').hide();
                alert("배송지 저장이 완료되었습니다.");
            } else {
                alert(res.data)
            }
        }).catch(error => {
            alert(JSON.stringify(error.response.data));
        });
    });

    $(document).on("click", "#daumPostcode", () => {
        new daum.Postcode({
            oncomplete: (res) => {
                // 검색 결과에서 우편번호와 주소 정보를 가져옵니다.
                const zonecode = res.zonecode;
                const address = res.address;

                // 주소 정보를 해당 필드에 넣습니다.
                document.getElementById('zipcode').value = zonecode; // 우편번호 필드
                document.getElementById('addaddress').value = address; // 주소 필드
            }
        }).open();
    });
}