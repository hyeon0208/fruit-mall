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

    $(".stopSaleBtn").each((index, element) => {
        const btn = $(element);
        const productId = btn.data("product-id");
        const status = localStorage.getItem(productId);
        if (status === "Status_SaleOut") {
            btn.hide();
            const timeSpan = btn.next(".updateTime");
            const storedDate = localStorage.getItem(`updatedTime-${productId}`);
            if (storedDate) {
                timeSpan.text(storedDate);
            }
            timeSpan.show();
        }
    });

    $(".stopSaleBtn").on("click", (e) => {
        const btn = $(e.currentTarget);
        const productId = btn.data("product-id");

        axios({
            method: "post",
            url: "/admin/salestop",
            data: {
                productId: productId,
                status: "판매중지",
            },
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        }).then(res => {
            const updatedTime = res.data
            btn.hide();
            const timeSpan = btn.next(".updateTime");
            const formattedTime = updatedTime.substring(0, 10).replaceAll('-', '.');
            timeSpan.text(formattedTime);
            timeSpan.show();

            const productStatus = btn.closest('tr').find('.product_Status');
            productStatus.text("판매중지");

            localStorage.setItem(productId, "Status_SaleOut");
            localStorage.setItem(`updatedTime-${productId}`, formattedTime);
        })
    });
});
