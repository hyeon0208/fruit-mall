$(document).on('click', '#recentProductBtn', (e) => {
    const productId = $(e.currentTarget).attr("data-userproductId");

    axios({
        method: "post",
        url: `/recent-products/${productId}`
    })
});

$(document).on('click', '#TopBtn', (e) => {
    e.preventDefault();
    window.scrollTo(0, 0);
});


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