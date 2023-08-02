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
                let price = $(id).val().replace(/[^0-9]/g, '');
                let formattedPrice = price.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                $(id).val(formattedPrice);

                if ($(id).val() === "") {
                    $(`${id}-error`).text("숫자만 입력 가능합니다.");
                }
            }
        }
    });
}

function calculateTotal() {
    let price = parseInt($("#price").val().replace(/,/g, ''));
    let discount = parseInt($("#discount").val());
    let totalPrice = price * (100 - discount) / 100;
    if (isNaN(totalPrice)) {
       $("#totalPrice").val(0);
    } else {
        let formattedTotalPrice = totalPrice.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        $("#totalPrice").val(formattedTotalPrice);
    }
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
    showErrorMessage("#sort");
    showErrorMessage("#price");

    $("#discount").on("focusout", () => {
        let regexDiscount = $("#discount").val().replace(/[^0-9]/g, '');
        console.log(regexDiscount);
        if (regexDiscount === "") {
            $("#discount-error").text("숫자만 입력가능 합니다.");
        } else if (parseInt(regexDiscount) < 1 || parseInt(regexDiscount) > 100) {
            $("#discount-error").text("1 ~ 100 까지의 숫자만 입력가능합니다.");
        } else {
            $("#discount-error").text("");
        }
    });

    // showErrorMessage("#discount");
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
        if (checkErrorAndShowModal("#sort")) return;
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