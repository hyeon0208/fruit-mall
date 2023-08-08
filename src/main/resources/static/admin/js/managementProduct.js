// 상태, 분류, 검색 버튼 값 초기 세팅
let selectedStatus, selectedCategory, selectedSearch;

// 수정 버튼
$(document).on("click", ".editBtn", (e) => {
    const btn = $(e.currentTarget);
    const productId = btn.data("product-id-edit");
    const editUrl = `/admin/edit/product/${productId}`;
    window.location.href = editUrl;
});

// 모두 체크
$(document).on("click", "#allCheck", () => {
    // chk1 체크박스의 체크 여부에 따라 모든 체크박스 상태를 변경
    const isChecked = $(this).prop("checked");
    $("input[type='checkbox']").prop("checked", isChecked);
});

// 검색 버튼
$(document).on("click", "#searchBtn", () => {
    const name = $("#searchCond").val();
    $("#searchCondHid").val(name);
    $("#searchForm").submit();
});

// $개 보기 셀렉트 박스 클릭 이벤트
$(document).on("change", "#pageSizeSelect", () => {
    const selectedPageSize = $("#pageSizeSelect").val();
    window.location.search = "pageNum=1" + "&pageSize=" + selectedPageSize;
});

// 엑셀 다운로드 버튼 클릭 이벤트
$(document).on("click", "#excelDownloadBtn", () => {
    const data = $(".product-table").html();
    const blob = new Blob([data], {type: "application/vnd.ms-excel"});
    const link = document.createElement("a");
    link.href = window.URL.createObjectURL(blob);
    link.download = "판매상품내역.xlsx";
    link.click();
});


// 상태 버튼 클릭 시
$(document).on("click", ".statusBtn", (e) => {
    selectedStatus =  $(e.currentTarget).val();
    updateProductList();
});

$(document).on("click", ".categoryBtn", (e) => {
    selectedCategory =  $(e.currentTarget).val();
    updateProductList();
});

$(document).on("click", "#searchBtn", () => {
    selectedSearch = $("#searchBtn").val();
    updateProductList();
});

// 판매 중지 버튼 클릭 이벤트
$(document).on("click", ".stopSaleBtn", (e) => {
    const btn = $(e.currentTarget);
    const productId = btn.data("product-id");

    axios({
        method: "post",
        url: "/admin/salestop",
        data: {
            productId: productId,
            status: "판매중지"
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

// document.ready()
$(() => {

    // 상품 가격 천자리 구분 기호 표시
    $(".productPrice").each((index, element) => {
        const price = $(element).text();
        $(element).text(price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','));
    });

    // 새로 고침 시에도 판매 중지 열의 시간 표시
    $(".stopSaleBtn").each((index, e) => {
        const btn = $(e);
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
});


const getCategoryName = (categoryId) => {
    const categories = ["봄과일", "여름과일", "가을과일", "겨울과일"];
    return categories[categoryId - 1];
};

function updateProductList() {
    axios({
        method: "get",
        url: "/admin/searchfilter",
        params: {
            status: selectedStatus || null,
            category: selectedCategory || null,
            searchCond: selectedSearch || null
        }
    }).then((res) => {
        const products = res.data.products;
        const tableRows = products.map((product) => { //
            return `
                    <tr>
                        <td>
                            <input type="checkbox">
                        </td>
                        <td>${product.productId}</td>
                        <td class="product_Status">${product.productSaleStatus}</td>
                        <td>${getCategoryName(product.categoryId)}</td>
                        <td>${product.productName}</td>
                        <td>${(product.productPrice * (1 - (product.productDiscount / 100))).toLocaleString()}원</td>
                        <td>${product.productDiscount > 0 ? `${product.productDiscount}%` : '-'}</td>
                        <td name="찜수">-</td>
                        <td name="결제횟수">-</td>
                        <td name="리뷰수">-</td>
                        <td>${new Date(product.productCreatedAt).toLocaleDateString("ko-KR", { year: 'numeric', month: '2-digit', day: '2-digit' }).replaceAll(". ", ".").substring(0, 10)}</td>
                        <td>
                            <button class="editBtn" data-product-id-edit="${product.productId}">수정</button>
                        </td>
                        <td>
                            <button class="stopSaleBtn" data-product-id="${product.productId}">중지</button>
                            <span class="updateTime" style="display: none;"></span>
                        </td>
                    </tr>
                    `;
        });
        $(".product-table tbody").html(tableRows.join(''));
    })
}
