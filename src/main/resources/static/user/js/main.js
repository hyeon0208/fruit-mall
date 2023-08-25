let userSelectedCategory, userSelectedSearch;
let userCurrentPage= 1;
let userPageSize = 9;

$(document).on('click', '#recentProductBtn', (e) => {
    const productId = $(e.currentTarget).data('product-id');

    axios({
        method: "post",
        url: `/recent-products/${productId}`
    })
});

$(document).on('click', '#TopBtn', (e) => {
    e.preventDefault();
    window.scrollTo(0, 0);
});

// 이전 페이지 버튼
$(document).on('click', '.user-prevBtn', () => {
    userCurrentPage -= 1;
    showMainProductList();
});

// 페이지 번호 버튼
$(document).on('click', '.user-numberBtn', (e) => {
    userCurrentPage = parseInt($(e.currentTarget).attr("value"));
    showMainProductList();
});

// 다음 페이지 버튼
$(document).on('click', '.user-nextBtn', () => {
    userCurrentPage += 1;
    showMainProductList();
});

// 게시상태 버튼
$(document).on("click", ".categoryBtn-user", (e) => {
    userSelectedCategory =  $(e.currentTarget).val();
    userCurrentPage = 1;
    showMainProductList();
});

// 검색어 입력 버튼
$(document).on("click", "#searchCond-userBtn", () => {
    userSelectedSearch = $("#searchCond-user").val();
    userCurrentPage = 1;
    showMainProductList();
});


function showMainProductList() {
    axios({
        method: "get",
        url: "/user/searchfilter",
        params: {
            category: userSelectedCategory,
            searchCond: userSelectedSearch,
            pageNum: userCurrentPage,
            pageSize: userPageSize
        }
    }).then((res) => {
        const products = res.data.productAndImageInfoPageInfo.list;
        const pageInfo = res.data.productAndImageInfoPageInfo;
        const category = res.data.category;
        const loginUser = res.data.loginUser;

        $('.categoryBtn-user').each((i, e) => {
            const btn = $(e);
            btn.removeClass('active');

            if (category === "전체카테고리" && btn.val() === "전체카테고리") {
                btn.addClass('active');
            } else if (category === "봄과일" && btn.val() === "봄과일") {
                btn.addClass('active');
            } else if (category === "여름과일" && btn.val() === "여름과일") {
                btn.addClass('active');
            } else if (category === "가을과일" && btn.val() === "가을과일") {
                btn.addClass('active');
            } else if (category === "겨울과일" && btn.val() === "겨울과일") {
                btn.addClass('active');
            }
        });

        // #products 내부의 .inner ul 내부 내용 교체
        const productList = products.map((product) => {
            let favoriteButton;

            if (loginUser != null) {
                favoriteButton = $("<button>")
                    .addClass("material-icons red__heart")
                    .attr('value', product.liked ? '1' : '0')
                    .attr('data-user-id', loginUser.userIdNo) // Assuming 'loginUser' has a 'userIdNo' property
                    .text("favorite");

                // If 'liked' property is true, add 'filled' class
                if (product.liked) {
                    favoriteButton.addClass("filled");
                }
            } else {
                favoriteButton = $("<button>")
                    .addClass("material-icons red__heart")
                    .attr('disabled', true)
                    .text("favorite");
            }

            return $("<li>")
                .append(
                    $("<a>")
                        .attr("id", "recentProductBtn")
                        .attr("data-userproductId", product.productId)
                        .attr("href", `/user/detail/${product.productId}`)
                        .append($("<img>").attr("src", product.imageUrl))
                        .append(
                            $("<div>")
                                .addClass("txt")
                                .append($("<div>").addClass("title").text(product.productName))
                                .append(
                                    $("<div>")
                                        .addClass("price")
                                        .text(
                                            `${(
                                                product.productPrice *
                                                (1 - product.productDiscount / 100.0)
                                            )
                                                .toLocaleString("ko-KR", {
                                                    maximumFractionDigits: 0,
                                                })}원`
                                        )
                                )
                        ),
                    $("<div>")
                        .addClass('icons')
                        .append(favoriteButton)
                        .append('<button class="material-symbols-outlined" value="0">shopping_cart</button>')
                );
        });
        $("#products .inner ul").empty().append(productList);

        // .user-pagination 클래스 태그 내부 내용 교체
        const paginationDiv = $('.user-pagination').empty();
        const numbersDiv = $('<p>').addClass('user-numbers');

        const totalData = pageInfo.pages; // 총 데이터 수
        const pageNumberList = pageInfo.navigatepageNums; // 페이지 번호들의 순서를 담은 배열
        const userCurrentPage = pageInfo.pageNum;

        // 이전 페이지 버튼
        if (pageInfo.hasPreviousPage) {
            const userPrevBtn = $("<a>")
                .attr('href', '#')
                .addClass("user-prevBtn")
                .attr('value', pageInfo.prePage)
                .html('<span class="material-symbols-outlined">chevron_left</span>');
            paginationDiv.append(userPrevBtn);
        }

        // 페이지 번호 버튼
        pageNumberList.forEach((pageNumber) => {
            if (pageNumber <= totalData) {
                const userNumberBtn = $("<a>")
                    .text(pageNumber)
                    .attr('href', '#')
                    .addClass('user-numberBtn')
                    .attr('value', pageNumber);

                if (pageNumber === userCurrentPage) {
                    userNumberBtn.addClass("bold").addClass("large-text").css("font-size", "16px");
                }
                numbersDiv.append(userNumberBtn);
            }
        });
        paginationDiv.append(numbersDiv);

        // 다음 페이지 버튼
        if (pageInfo.hasNextPage) {
            const userNextBtn = $("<a>")
                .attr('href', '#')
                .addClass("user-nextBtn")
                .attr('value', pageInfo.nextPage)
                .html('<span class="material-symbols-outlined">chevron_right</span>');
            paginationDiv.append(userNextBtn);
        }
    });
}


new Swiper('.promotion .swiper', {
    slidesPerView: 1, // 한 번에 보여 줄 슬라이드 개수
    spaceBetween: 10, // 슬라이드 사이 여백 (10px)
    centeredSlides: true, // 1번 슬라이드가 가운데 보이기
    loop: true,
    autoplay: {
        delay: 5000 // 5초
    },
    pagination: {
        el: '.promotion .swiper-pagination', // 페이지 번호 요소 선택자
        clickable: true // 사용자의 페이지 번호 요소 제어 기능 여부
    },
    navigation: {
        prevEl: '.promotion .swiper-button-prev',
        nextEl: '.promotion .swiper-button-next'
    }
});

new Swiper("#popular .mySwiper", {
    slidesPerView: 3,
    spaceBetween: 0,
    loop: true,
    navigation: {
        prevEl: '#popular .swiper-button-prev',
        nextEl: '#popular .swiper-button-next'
    },
});

new Swiper("#new .mySwiper", {
    slidesPerView: 3,
    spaceBetween: 0,
    loop: true,
    navigation: {
        prevEl: '#new .swiper-button-prev',
        nextEl: '#new .swiper-button-next'
    },
});

new Swiper("#recomm .mySwiper", {
    slidesPerView: 3,
    spaceBetween: 0,
    loop: true,
    navigation: {
        prevEl: '#recomm .swiper-button-prev',
        nextEl: '#recomm .swiper-button-next'
    },
});