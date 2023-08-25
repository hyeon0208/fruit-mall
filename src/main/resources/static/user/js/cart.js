$(document).on("click", ".addCartBtn", (e) => {
    let cart = $(e.currentTarget);
    const productId = cart.data("product-id");
    const userIdNo = cart.data("user-id");

    if(cart.attr("data-btn-status") == 0) {
        cart.attr("data-btn-status", 1);
    } else {
        cart.attr("data-btn-status", 0);
    }

    console.log(productId);
    console.log(userIdNo);
    console.log(cart.attr("data-btn-status"))

    // 로그인 시
    if (userIdNo != 0) {
        // 장바구니 취소
        if (cart.attr("data-btn-status") == 0) {
            axios({
                method: "post",
                url: "/main/cart/remove",
                data: {
                    productId: productId,
                    userIdNo: userIdNo
                },
                dataType: "json",
                headers: {'Content-Type': 'application/json'}
            });
        }

        // 장바구니 담기
        if (cart.attr("data-btn-status") == 1) {
            let localCart = localStorage.getItem('cart') ? JSON.parse(localStorage.getItem('cart')) : [];
            axios({
                method: "post",
                url: "/main/cart/add",
                data: {
                    productId: productId,
                    userIdNo: userIdNo,
                    localCart: localCart
                },
                dataType: "json",
                headers: {'Content-Type': 'application/json'}
            }).then((res) =>{
                showCartModal();
            })
        }
    }

    // 비로그인 시
    if (userIdNo == 0) {
        addToLocalStorageCart(productId, 1);
        console.log(localStorage.getItem('cart'));
        showCartModal();
    }
})

function showCartModal() {
    $('.txt04.right__modal').show();
    $('#closeCartModal').click(() => {
        $('.txt04.right__modal').hide();
    });
}

function addToLocalStorageCart(productId, quantity) {
    const cart = localStorage.getItem('cart') ? JSON.parse(localStorage.getItem('cart')) : [];
    const existingProduct = cart.find(product => product.productId === productId);
    if (existingProduct) {
        existingProduct.quantity += quantity;
    } else {
        cart.push({ productId, quantity });
    }
    localStorage.setItem('cart', JSON.stringify(cart));
}