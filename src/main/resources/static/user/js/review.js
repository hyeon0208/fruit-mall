$(() => {
    $("#reviewContent").on("input", () => {
        const inputLength = $("#reviewContent").val().length;
        $('.count').text(inputLength + "/500");
    });

    $("#update_content").on("input", () => {
        const inputLength = $("#update_content").val().length;
        $('.count').text(inputLength + "/500");
    });

    $("#show-reply").on("click", (e) => {
        const reviewId = $(e.currentTarget).data("review-id");
        console.log(reviewId);
        axios({
            method: "get",
            url: `/reply/${reviewId}`
        }).then(res => {
            $(".reply-div #reply_contents").text(res.data.comments);
            $(".reply-div .date").text(res.data.replyCreatedAt.toLocaleString("ko-KR", { year: 'numeric', month: '2-digit', day: '2-digit' }).replaceAll("-", ".").substring(0, 10));
            $(".reply-div").slideToggle();
        }).catch(err => {
            $(".reply-div #reply_contents").text("답글이 없습니다.");
            $(".reply-div").slideToggle();
        });
    });

    $("#writeReview").on("click", () => {
        $(".review__modal.write__review").show();

        // 작성취소
        $(".review__modal .review__cancel a").on("click", (e) => {
            e.preventDefault();
            $(".review__cancel__modal").css("z-index", 10).show();
        });

        $(".review__cancel__modal .review__cancel a").on("click", (e) => {
            e.preventDefault();
            $(".review__cancel__modal, .review__modal").hide();
        });

        $(".review__cancel__modal .review__confirm a").on("click", (e) => {
            e.preventDefault();
            $(".review__cancel__modal").hide();
        });

        // 리뷰등록
        $(".review__modal .review__confirm a").on("click", (e) => {
            e.preventDefault();
            const reviewContents = $("#reviewContent").val();

            if (reviewContents.length > 500 || reviewContents.length < 10) {
                return alert("10자 이상 500자 미만으로 작성해주세요.");
            }

            axios({
                method: "post",
                url: "/review/add",
                data: {
                    productId: $(".right__txt01").data("product-id"),
                    reviewContents: reviewContents
                },
                dataType: "json",
                headers: {'Content-Type': 'application/json'}
            }).then(res => {
                $(".review__confirm__modal").css("z-index", 10).show();
                $(".review__confirm__modal .review__confirm a").on("click", () => {
                    $(".review__confirm__modal, .review__modal").hide();
                });
            });
        });
    });

    $("#update_review").on("click", () => {
        $(".review__modal.update_review").show();
        $('.count').text($("#review_contents").text().length + "/500");
        $("#update_content").val($("#review_contents").text());

        $(".review__modal.update_review .review__cancel a").on("click", (e) => {
            e.preventDefault();
            $(".review__cancel__modal").css("z-index", 10).show();
        });

        $(".review__cancel__modal .review__cancel a").on("click", (e) => {
            e.preventDefault();
            $(".review__cancel__modal, .review__modal").hide();
        });

        $(".review__cancel__modal .review__confirm a").on("click", (e) => {
            e.preventDefault();
            $(".review__cancel__modal").hide();
        });

        $(".review__modal.update_review .review__confirm a").on("click", (e) => {
            e.preventDefault();
            const updateContents = $("#update_content").val();

            if (updateContents.length > 500 || updateContents.length < 10) {
                return alert("10자 이상 500자 미만으로 작성해주세요.");
            }

            axios({
                method: "post",
                url: "/review/update",
                data: updateContents,
                dataType: "json",
                headers: {'Content-Type': 'application/json'}
            }).then(res => {
                $(".review__confirm__modal").css("z-index", "10").show();
                $(".review__confirm__modal .review__confirm a").on("click", () => {
                    $(".review__confirm__modal, .review__modal").hide();
                });
            });
        });
    });
});