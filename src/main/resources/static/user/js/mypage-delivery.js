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
});