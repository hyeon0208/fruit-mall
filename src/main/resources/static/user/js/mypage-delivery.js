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
                const zonecode = res.zonecode;
                const address = res.address;

                $("#add_1").val(zonecode);
                $("#add_2").val(address);
            }
        }).open();
    });

    $(document).on("click", ".editDeliveryBtn", (e) => {
        $(".delivery__edit").show();
        const delivery = $(e.currentTarget).closest(".delivery__wrap");
        const deliveryName = delivery.find('li span:contains("배송지명")').next().text();
        const userName = delivery.find('li span:contains("수령인")').next().text();
        const phoneNumber = delivery.find('li span:contains("연락처")').next().text();
        const address = delivery.find('li span:contains("배송주소")').next().text();

        $('#edit_title').val(deliveryName);
        $('#edit_name').val(userName);

        let phoneSplit = phoneNumber.split("-");
        $('#edit_phone1').val(phoneSplit[0]);
        $('#edit_phone2').val(phoneSplit[1] + phoneSplit[2]);

        let addressSplit = address.split(" ");
        $('#edit_1').val(addressSplit.shift());
        $('#edit_2').val(addressSplit.join(' '));

        $(".delivery__edit .delivery__edit__buttons button:eq(0)").on("click", (e) => {
            e.preventDefault();
            $(".delivery__edit").hide();
        });

        $(".delivery__edit .delivery__edit__buttons button:eq(1)").on("click", (e) => {
            e.preventDefault();

            axios({
                url: "/delivery/update",
                method: "post",
                data: {
                    curDeliveryName: deliveryName,
                    updateDeliveryName: $("#edit_title").val(),
                    userName: $("#edit_name").val(),
                    phoneNumber: $("#edit_phone1").val() + $("#edit_phone2").val(),
                    zipcode: $("#edit_1").val(),
                    address: $("#edit_2").val()
                },
                dataType: "json",
                headers: {'Content-Type': 'application/json'}
            }).then(res => {
                if (res.data == "success") {
                    $(".delivery__add__confirm").css("z-index", 10).show();
                    $(".delivery__add__confirm button").on("click", () => {
                        $(".delivery__add__confirm").on("click", () => {
                            $(".delivery__add__confirm").hide();
                            $(".delivery__edit").hide();
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
    });


    $(document).on("click", "#mypage-DaumPostcode-edit", () => {
        new daum.Postcode({
            oncomplete: (res) => {
                const zonecode = res.zonecode;
                const address = res.address;

                $("#edit_1").val(zonecode);
                $("#edit_2").val(address);
            }
        }).open();
    });
});