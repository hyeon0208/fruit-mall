// 상태, 분류, 검색 버튼, 현재 페이지 값 초기 세팅
let selectedStatus, selectedCategory, selectedSearch;
let currentPage= 1;

// 이전 페이지 버튼
$(document).on('click', '.prevBtn', () => {
    currentPage -= 1;
    updateProductList();
});

// 페이지 번호 버튼
$(document).on('click', '.numberBtn', (e) => {
    currentPage = parseInt($(e.currentTarget).attr("value"));
    updateProductList();
});

// 다음 페이지 버튼
$(document).on('click', '.nextBtn', () => {
    currentPage += 1;
    updateProductList();
});

// 수정 버튼
$(document).on("click", ".editBtn", (e) => {
    const btn = $(e.currentTarget);
    const productId = btn.val();
    const editUrl = `/admin/edit/product/${productId}`;
    window.location.href = editUrl;
});

// 모두 체크
$(document).on("click", "#allCheck", () => {
    // chk1 체크박스의 체크 여부에 따라 모든 체크박스 상태를 변경
    const isChecked = $(this).prop("checked");
    $("input[type='checkbox']").prop("checked", isChecked);
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

// 게시상태 버튼
$(document).on("click", ".statusBtn", (e) => {
    selectedStatus =  $(e.currentTarget).val();
    updateProductList();
});

// 카테고리 분류 버튼
$(document).on("click", ".categoryBtn", (e) => {
    selectedCategory =  $(e.currentTarget).val();
    updateProductList();
});

// 검색어 입력 버튼
$(document).on("click", "#searchBtn", () => {
    selectedSearch = $("#searchCond").val();
    updateProductList();
});

// 판매 중지 버튼 클릭 이벤트
$(document).on("click", ".stopSaleBtn", (e) => {
    const btn = $(e.currentTarget);
    const productId = btn.val();

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

$(() => {
    // 상품 가격 천자리 구분 기호 표시
    $(".productPrice").each((index, element) => {
        const price = $(element).text();
        $(element).text(price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','));
    });

    // 새로 고침 시에도 판매 중지 열의 시간 표시
    $(".stopSaleBtn").each((index, e) => {
        const btn = $(e);
        const productId = btn.val();
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
            searchCond: selectedSearch || null,
            pageNum: currentPage || 1
        }
    }).then((res) => {

        // .product-table 클래스 내부의 tbody 내부 내용 교체
        const products = res.data.list;
        const tableRows = products.map((product) => {
            $(".margin .bold").text(res.data.total);

            let stopBtn = $(`<button class="stopSaleBtn" data-product-id="${product.productId}">중지</button>`);
            let updateTime = "";

            if (product.productSaleStatus === "판매중지") {
                stopBtn = $("");
                updateTime = $(`<span class="updateTime">${new Date(product.productUpdatedAt).toLocaleDateString("ko-KR", { year: 'numeric', month: '2-digit', day: '2-digit' }).replaceAll(". ", ".").substring(0, 10)}</span>`);
            }

            return $("<tr>")
                .append($("<td>").html(`<input type="checkbox">`))
                .append($("<td>").text(product.productId))
                .append($("<td>").addClass("product_Status").text(product.productSaleStatus))
                .append($("<td>").text(getCategoryName(product.categoryId)))
                .append($("<td>").text(product.productName))
                .append($("<td>").text((product.productPrice * (1 - (product.productDiscount / 100))).toLocaleString() + "원"))
                .append($("<td>").text(product.productDiscount > 0 ? `${product.productDiscount}%` : "-"))
                .append($("<td>").attr("name", "찜수").text("-"))
                .append($("<td>").attr("name", "결제횟수").text("-"))
                .append($("<td>").attr("name", "리뷰수").text("-"))
                .append($("<td>").text(new Date(product.productCreatedAt).toLocaleDateString("ko-KR", { year: 'numeric', month: '2-digit', day: '2-digit' }).replaceAll(". ", ".").substring(0, 10)))
                .append($("<td>").html(`<button class="editBtn" value="${product.productId}">수정</button>`))
                .append($("<td>").append(stopBtn).append(updateTime));
        });
        $(".product-table tbody").empty().append(tableRows);

        // .pagination 클래스 태그 내부 내용 교체
        const paginationDiv = $('.pagination');
        const numbersDiv = $('<p>').addClass('numbers');
        paginationDiv.empty();

        const totalData = res.data.pages; // 총 데이터 수
        const pageNumberList = res.data.navigatepageNums; // 페이지 번호들의 순서를 담은 배열
        const currentPage = res.data.pageNum;

        // 이전 페이지 버튼
        if (res.data.hasPreviousPage) {
            const prevBtn = $('<a>')
                .attr('href', '#')
                .addClass('prevBtn')
                .attr('value', res.data.prePage)
                .html('<span class="material-symbols-outlined">chevron_left</span>');
            paginationDiv.append(prevBtn);
        }

        pageNumberList.forEach((pageNumber) => {
            if (pageNumber <= totalData) {
                const numberBtn = $('<a>')
                    .text(pageNumber)
                    .attr('href', '#')
                    .addClass('numberBtn')
                    .attr('value', pageNumber);

                if (pageNumber === currentPage) {
                    numberBtn.addClass('bold').addClass("large-text").css("font-size", "16px");
                }
                numbersDiv.append(numberBtn);
            }
        });
        paginationDiv.append(numbersDiv);

        // 다음 페이지 버튼
        if (res.data.hasNextPage) {
            const nextBtn = $('<a>')
                .attr('href', '#')
                .addClass('nextBtn')
                .attr('value', res.data.nextPage)
                .html('<span class="material-symbols-outlined">chevron_right</span>');
            paginationDiv.append(nextBtn);
        }
    })
}

