$(() => {
    $(".deleteDeliveryBtn").on("click", (e) => {
        $(".delivery__delete").show();
        $(".delivery__delete__buttons button:eq(0)").on("click", () => {
            $(".delivery__delete").hide();
        });

        $(".delivery__delete__buttons button:eq(1)").on("click", () => {
            axios({
                url: "/delete/delivery",
                method: "post",
                data: { deliveryName: $(e.currentTarget).data("delivery-name") },
                dataType: "json",
                headers: {'Content-Type': 'application/json'}
            }).then(res => {
                alert("배송지가 삭제되었습니다.")
                $(".delivery__delete").hide();
                window.location.href = "/user/mypage/delivery";
            });
        });
    });

    $("#addDelivery").on("click", () => {
        const deliverySize = $(".delivery__wrap").data("delivery-size");

        if (deliverySize >= 3) {
            return alert("배송지는 3개까지만 저장됩니다.")
        } else {
            $("#mypage_delivery .delivery__add").show();
            $("#mypage_delivery .delivery__add__cancel").on("click", () => {
                $(".delivery__add__calcel").show();
                $(".delivery__add__calcel__buttons button:eq(0)").on("click", () => {
                    $('.delivery__add').hide();
                    $('.delivery__add__calcel').hide();
                });

                $(".delivery__add__calcel__buttons button:eq(1)").on("click", () => {
                    $('.delivery__add__calcel').hide();
                });
            });

            $("#mypage_delivery .delivery__add__confirm__btn").on("click", () => {
                axios({
                    url: "/delivery/add/",
                    method: "post",
                    data: {
                        deliveryName: $("#add_title").val(),
                        userName: $("#add_name").val(),
                        phoneNumber: $("#add_phone1").val() + $("#add_phone2").val(),
                        zipcode: $("#add_1").val(),
                        address: $("#add_2").val() + " " + $("#add_3").val()
                    },
                    dataType: "json",
                    headers: {'Content-Type': 'application/json'}
                }).then(res => {
                    if (res.data == "success") {
                        $(".delivery__add__confirm").show();
                        $(".delivery__add__confirm button").on("click", () => {
                            $(".delivery__add__confirm").on("click", () => {
                                $(".delivery__add__confirm").hide();
                                $('.delivery__add').hide();
                                window.location.href = "/user/mypage/delivery";
                            });
                        });
                    } else {
                        alert(res.data);
                    }
                }).catch(error => {
                    alert(JSON.stringify(error.response.data));
                });
            });
        }
    });

    $(document).on("click", "#mypage-DaumPostcode", () => {
        new daum.Postcode({
            oncomplete: (res) => {
                // 검색 결과에서 우편번호와 주소 정보를 가져옵니다.
                const zonecode = res.zonecode;
                const address = res.address;

                // 주소 정보를 해당 필드에 넣습니다.
                document.getElementById('add_1').value = zonecode; // 우편번호 필드
                document.getElementById('add_2').value = address; // 주소 필드
            }
        }).open();
    });
});