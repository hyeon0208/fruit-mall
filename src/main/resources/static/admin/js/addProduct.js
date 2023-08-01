function showModal() {
    $(".txt05").css("display", "block");
}

function showErrorMessage(id) {
    $(id).on("focusout", () => {
        if ($(id).val().trim() === "") {
            $(`${id}-error`).text("필수 입력 값을 입력해주세요.");
        } else {
            $(`${id}-error`).text("");

            if (id === "#price") {
                // 가격 필드에 특정 조건을 추가하여 에러 메시지 표시
                let price = $(id).val().replace(/[^0-9]/g, '');
                let formattedPrice = price.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                $(id).val(formattedPrice);

                if ($(id).val() === "") {
                    // 숫자가 아닌 값이 입력되었을 때 에러 메시지 표시
                    $(`${id}-error`).text("숫자만 입력 가능합니다.");
                }
            }
            if (id === "#discount") {
                // 할인 필드에 특정 조건을 추가하여 에러 메시지 표시
                let regexDiscount = $(id).val().replace(/[^0-9]/g, '');
                $(id).val(regexDiscount);
                if ($(id).val() === "") {
                    $(`${id}-error`).text("숫자만 입력 가능합니다.");
                } else {
                    let discount = parseInt($(id).val());
                    if (discount < 1 || discount > 100) {
                        $(`${id}-error`).text("1 ~ 100 까지의 숫자만 입력가능합니다.");
                    }
                }
            }
        }
    });
}

function calculateTotal() {
    let price = $("#price").val();
    let discount = $("#discount").val();
    let totalPrice = price - (price * discount / 100);
    console.log(totalPrice)
    if (isNaN(totalPrice)) {
       $("#totalPrice").val(0);
    }
    $("#totalPrice").val(totalPrice.toFixed(0)); // 소수점 둘째 자리까지 표시
}

function checkErrorAndShowModal(id) {
    if ($(id + '-error').text().length > 0) {
        $(".txt05 h5").html('입력 항목을 다시 확인해주세요.' + '<br><a>확인</a>')
        showModal();
        $('.txt05 a').click(function() {
            $('.txt05').hide();
        });
        $(id).focus();
        return true; // 필수 입력 값이 없는 경우 true 반환
    }
    return false;
}

$(() => {
    showErrorMessage("#productName");
    showErrorMessage("#price");
    showErrorMessage("#discount");
    showErrorMessage("#stock");
    showErrorMessage("#description");


    // #discount 필드의 값이 변경될 때 calculateTotal() 함수 호출
    $("#discount").on("focusout", () => {
        calculateTotal();
    });


    $("#cancelBtn").on("click", () => {
        $(".txt05 h5").html('작성중이던 항목이 모두 삭제됩니다. 취소하겠습니까?' + '<br><a href="/admin/product">확인</a>');
        showModal();
    });

    $("#addBtn").on("click", (e) => {
        e.preventDefault();

        if (checkErrorAndShowModal("#productName")) return;
        if (checkErrorAndShowModal("#price")) return;
        if (checkErrorAndShowModal("#discount")) return;
        if (checkErrorAndShowModal("#stock")) return;
        if (checkErrorAndShowModal("#description")) return;

        axios({
            method: "post",
            url: "/admin/add/product",
            data: { id: $("#id").val(), password: $("#pw").val() },
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        }).then(res => {
            if(res.data.success)
                $(".txt05 h5").html('상품등록이 완료되었습니다.' + '<br><a href="/admin/product">확인</a>');
                showModal();
        });
    });
});