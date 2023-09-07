let myPageClickedDuration, myPageSearchCategory, myPageSearchText;
let startDate, endDate = null;
let myPageCurrentPage= 1;
let myPagePageSize = 10;

$(() => {
    $("#mypageReviewContent").on("input", () => {
        const inputLength = $("#mypageReviewContent").val().length;
        $('.mypage__content__count').text(inputLength + "/500");
    });

    $(document).on("click", ".writeReviewAtMyPage", (e) => {
        $(".txt05").show();

        // 작성취소
        $(".txt05 .buttons button:eq(0)").on("click", () => {
            $(".txt06").css("z-index", 10).show();
            $(".txt06 .buttons button:eq(0)").on("click", () => {
                $(".txt06, .txt05").hide();
            });

            $(".txt06 .buttons button:eq(1)").on("click", () => {
                $(".txt06").hide();
            });
        });

        // 리뷰등록
        $(".txt05 .buttons button:eq(1)").on("click", () => {
            const mypageReviewContent = $("#mypageReviewContent").val();
            const productId = $(e.currentTarget).siblings(".reAddToCartBtn").data("product-id");
            const orderNumber = $(e.currentTarget).siblings(".reAddToCartBtn").data("order-number");

            if (mypageReviewContent.length > 500 || mypageReviewContent.length < 10) {
                return alert("10자 이상 500자 미만으로 작성해주세요.");
            }

            // 주문번호도 넘겨서 리뷰 저장하도록 수정 ()
            axios({
                method: "post",
                url: "/mypage/review/add",
                data: {
                    productId: productId,
                    orderNumber: orderNumber,
                    reviewContents: mypageReviewContent
                },
                dataType: "json",
                headers: {'Content-Type': 'application/json'}
            }).then(res => {
                $(".txt07").css("z-index", 10).show();
                $(".txt07 .buttons button").on("click", () => {
                    $(".txt07, .txt05").hide();
                    window.location.href="/user/mypage";
                });
            }).catch(err => {
                alert("리뷰작성에 실패했습니다.")
            });
        });
    });
});

// 이전 페이지 버튼
$(document).on('click', '.mypage-prevBtn', () => {
    myPageCurrentPage -= 1;
    myPageOrderProductList();
});

// 페이지 번호 버튼
$(document).on('click', '.mypage-numberBtn', (e) => {
    myPageCurrentPage = parseInt($(e.currentTarget).attr("value"));
    myPageOrderProductList();
});

// 다음 페이지 버튼
$(document).on('click', '.mypage-nextBtn', () => {
    myPageCurrentPage += 1;
    myPageOrderProductList();
});

// 기간검색 버튼
$(document).on("click", ".durationBtn", (e) => {
    let btn = $(e.currentTarget);

    $(".durationBtn").not(btn).each((i, e) => {
        $(e).data("click-cnt", 0);
    });
    let clickCount = btn.data("click-cnt") || 0;
    clickCount += 1;
    btn.data("click-cnt", clickCount);

    $(".durationBtn").css("background-color", "");
    if (clickCount % 2 === 0) {
        $('#date1').prop('disabled', false);
        $('#date2').prop('disabled', false);
    } else {
        myPageClickedDuration = btn.val();
        myPageCurrentPage = 1;
        btn.css("background-color", "rgba(87, 80, 80, 0.6)");
        myPageOrderProductList();
        $('#date1').prop('disabled', true);
        $('#date2').prop('disabled', true);
    }
});

// 날짜항목 버튼
$(document).on("change", "#date1, #date2", () => {
    startDate = $('#date1').val();
    endDate = $('#date2').val();

    if (startDate && endDate) {
        const start = new Date(startDate);
        const end = new Date(endDate);
        if (end < start) {
            return alert("기간 설정을 다시 해주세요.\n종료일이 시작일보다 이전입니다.");
        }
        myPageClickedDuration = null;
        myPageCurrentPage = 1;
        myPageOrderProductList();
    }
});

// 초기화 버튼
$(document).on("click", "#initBtn", () => {
    myPageClickedDuration = null;
    myPageSearchCategory = null;
    myPageSearchText = null;
    startDate = null;
    endDate = null;
    $("#searchText").val("");
    myPageCurrentPage= 1;
    myPageOrderProductList();
});

// 검색어 입력 버튼
$(document).on("click", "#myPageSearchBtn", () => {
    myPageSearchText = $("#searchText").val();
    myPageSearchCategory = $("#products").val();
    myPageCurrentPage = 1;
    myPageOrderProductList();
});

// 엔터키 입력 이벤트
$(document).on("keyup", "#searchText", (e) => {
    if (e.keyCode == 13) {
        myPageSearchText = $("#searchText").val();
        myPageSearchCategory = $("#products").val();
        myPageCurrentPage = 1;
        myPageOrderProductList();
    }
});

$(document).on("click", ".showReview", (e) => {
    const productId = $(e.currentTarget).siblings(".reAddToCartBtn").data("product-id");
    window.location.href=`/user/review/${productId}`
});

$(document).on("click", ".group2", (e) => {
    $(e.currentTarget).next(".deliveryInfo").slideToggle();
});

$(document).on("click", ".reAddToCartBtn", (e) => {
    const btn = $(e.currentTarget);
    const productId = btn.data("product-id");
    const count = btn.data("count");

    axios({
        method: "post",
        url: "/main/cart/add",
        data: {
            productId: productId,
            productCount: count
        },
        dataType: "json",
        headers: {'Content-Type': 'application/json'}
    }).then((res) =>{
        $(".txt04.right__modal.add__cart").show();
        $('#closeCartModal').click(() => {
            $(".txt04.right__modal.add__cart").hide();
        });
    }).catch((res) => {
        $(".txt04.exist__mypage__cart").show();
        $("#existCartModalClose").click(() => {
            $(".txt04.exist__mypage__cart").hide();
        });
    });
});

function myPageOrderProductList() {
    axios({
        method: "get",
        url: "/user/mypage/searchfilter",
        params: {
            clickedDuration: myPageClickedDuration,
            startDate: startDate,
            endDate: endDate,
            category: myPageSearchCategory,
            searchText: myPageSearchText,
            pageNum: myPageCurrentPage,
            pageSize: myPagePageSize
        }
    }).then((res) => {
        const orderDetails = res.data.list;
        const pageInfo = res.data;

        if (orderDetails.length == 0) {
            const messageDiv = $("<div>")
                .addClass("txt")
                .css("text-align", "center")
                .append($("<span>").css("font-size", "20px").text("최근 주문/배송 조회 내역이 없습니다."));
            $(".historyContents ul").empty().append(messageDiv);
            $('.pagination').empty();
        } else {
            // .historyContents 내부의 ul 내부 내용 교체
            const orderDetailList = orderDetails.map((orderDetail) => {

                // 배송지 정보 생성
                const deliveryInfoDiv = $("<div>")
                    .addClass("deliveryInfo")
                    .css("display", "none")
                    .append(
                        $("<br>"),
                        $("<div>")
                            .css({border:"1px #000000 solid", background:"#f7f5eb", padding:"8px"})
                            .append($("<p>").text(`주문자 : ${orderDetail.receiverName}`))
                            .append($("<p>").text(`연락처 : ${orderDetail.phoneNumber}`))
                            .append($("<p>").text(`주소 : ${orderDetail.address}`))
                            .append($("<p>").text(`배송요청사항 : ${orderDetail.orderRequired}`))
                    );

                // 재구매 버튼 생성
                const reAddToCartBtn = $("<button>")
                    .addClass("reAddToCartBtn")
                    .attr('data-count', orderDetail.orderCount)
                    .attr('data-product-id', orderDetail.productId)
                    .attr('data-order-number', orderDetail.orderNumber)
                    .text("재구매");

                // 리뷰작성/리뷰보기 버튼 생성
                let reviewButton;
                if (!orderDetail.isWrite) {
                    reviewButton = $("<button>")
                        .addClass("writeReviewAtMyPage")
                        .text("리뷰작성");
                } else {
                    reviewButton = $("<button>")
                        .addClass("showReview")
                        .css({"background-color":"#f1dacb", "color":"#c56206"})
                        .text("리뷰보기");
                }

                return $("<li>")
                    .append(
                        $("<div>")
                            .addClass("txt")
                            .append(
                                $("<span>").text(orderDetail.orderNumber),
                                $("<span>").text(new Date(orderDetail.orderDate).toLocaleDateString("ko-KR", { year: 'numeric', month: '2-digit', day: '2-digit' }).replaceAll(". ", ".").substring(0, 10))
                            ),
                        $("<div>")
                            .addClass("img")
                            .append(
                                $("<img>").attr({src: orderDetail.imageUrl}),
                                $("<div>")
                                    .addClass('img__txt')
                                    .append(
                                        $("<p>").text(orderDetail.productName),
                                        $("<div>")
                                            .addClass('group')
                                            .append(
                                                $("<span>").addClass('orange').text(`${(orderDetail.orderPrice * 1).toLocaleString()}원`),
                                                $("<span>").text(`구매수량 ${orderDetail.orderCount}개`)
                                            ),
                                        // 배송지 확인 영역 추가
                                        $('<div>')
                                            .addClass('group2')
                                            .html('<p>배송지 확인 <span class="material-symbols-outlined">double_arrow</span></p>'), deliveryInfoDiv
                                    )
                            ),
                        $("<div>", {class: 'buttons'}).append(reAddToCartBtn, reviewButton)
                    );
            });
            $(".historyContents ul").empty().append(orderDetailList);

            // .pagination 클래스 태그 내부 내용 교체
            const paginationDiv = $('.pagination').empty();
            const totalData = pageInfo.pages; // 총 데이터 수
            const pageNumberList = pageInfo.navigatepageNums; // 페이지 번호들의 순서를 담은 배열
            const currentPage = pageInfo.pageNum;

            // 이전 페이지 버튼
            if (pageInfo.hasPreviousPage) {
                const prevBtn = $("<a>")
                    .attr('href', '#')
                    .addClass("mypage-prevBtn")
                    .attr('value', pageInfo.prePage)
                    .html('<span class="material-symbols-outlined">chevron_left</span>');
                paginationDiv.append(prevBtn);
            }

            // 페이지 번호 버튼
            pageNumberList.forEach((pageNumber) => {
                if (pageNumber <= totalData) {
                    const numberBtn = $("<a>")
                        .text(pageNumber)
                        .attr('href', '#')
                        .addClass('mypage-numberBtn')
                        .attr('value', pageNumber);

                    if (pageNumber === currentPage) {
                        numberBtn.addClass("bold").addClass("large-text").css("font-size", "16px");
                    }
                    paginationDiv.append(numberBtn);
                }
            });

            // 다음 페이지 버튼
            if (pageInfo.hasNextPage) {
                const nextBtn = $("<a>")
                    .attr('href', '#')
                    .addClass("mypage-nextBtn")
                    .attr('value', pageInfo.nextPage)
                    .html('<span class="material-symbols-outlined">chevron_right</span>');
                paginationDiv.append(nextBtn);
            }
        }
    });
}
