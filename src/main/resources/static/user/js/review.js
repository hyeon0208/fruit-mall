$(() => {
    $("#reviewContent").on("input", () => {
        const inputLength = $("#reviewContent").val().length;
        $('.count').text(inputLength + "/500");
    });

    $("#writeReview").on("click", () => {
        $(".review__modal").show();

        // 작성취소
        $(".review__modal .review__cancel a").on("click", (e) => {
            e.preventDefault();
            $(".review__cancel__modal").show();
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

            if (reviewContents.length > 500) {
                return alert("10자 이상 500자 미만으로 작성해주세요.");
            }

            axios({
                method: "post",
                url: "/add/review",
                data: {
                    productId: $(".right__txt01").data("product-id"),
                    reviewContents: reviewContents
                },
                dataType: "json",
                headers: {'Content-Type': 'application/json'}
            }).then(res => {
                $(".review__confirm__modal").show();
                $(".review__confirm__modal .review__confirm a").on("click", () => {
                    $(".review__confirm__modal, .review__modal").hide();
                });
            });
        });
    });
});