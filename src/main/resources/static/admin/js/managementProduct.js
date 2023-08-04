$(() => {
    $(".productPrice").each(function() {
        const price = $(this).data('price');
        if (typeof price !== 'undefined') {
            $(this).text(price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','));
        }
    });

    $("#pageSizeSelect").on("change", () => {
        const selectedPageSize = $("#pageSizeSelect").val();
        window.location.search = "pageNum=" + 1 + "&pageSize=" + selectedPageSize;
    });

    $("#excelDownloadBtn").on("click", function() {
        const data = $(".product-table").html();
        const blob = new Blob([data], {type: "application/vnd.ms-excel"});
        const link = document.createElement("a");
        link.href = window.URL.createObjectURL(blob);
        link.download = "판매상품내역.xlsx";
        link.click();
    });

    $("#stop-sale-btn").on("click", function () {
        const btn = $(this);
        const productId = btn.data("product-id");

        $.ajax({
            url: "/updateProductStatus",
            type: "post",
            data: {
                productId: productId,
                status: "판매중지",
            },
            success: function (response) {
                const row = btn.closest("tr"); // 버튼의 상위 테이블 행 찾기
                row.find("td:eq(2)").text("판매중지"); // 상태 열의 텍스트를 "판매중지"로 변경
                btn.replaceWith("<span>판매중지</span>"); // 버튼을 텍스트로 교체
            },
            error: function (error) {
                // 에러 처리
                alert("상품 상태 업데이트에 실패했습니다. 다시 시도해주세요.");
            },
        });
    });
})
