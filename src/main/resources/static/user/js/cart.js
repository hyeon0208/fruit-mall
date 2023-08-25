$(() => {
    // 세션 정보가 없으면 로컬 스토리지에 담긴 cart의 수를 표시
    if (!$('#localCartCount').data('login-user')) {
        const cart = localStorage.getItem('cart') ? JSON.parse(localStorage.getItem('cart')) : [];
        $('#localCartCount').text(cart.length);
    }
})

$(document).on("click", ".addCartBtn", (e) => {
    let cart = $(e.currentTarget);
    const productId = cart.data("product-id");
    const userIdNo = cart.data("user-id");

    if(cart.attr("data-btn-status") == 0) {
        cart.attr("data-btn-status", 1);
    }

    console.log(productId);
    console.log(userIdNo);
    console.log(cart.attr("data-btn-status"))
    console.log(localStorage.getItem('cart'));


    // 로그인 시
    if (userIdNo != 0) {
        // // 장바구니 취소
        // if (cart.attr("data-btn-status") == 0) {
        //     axios({
        //         method: "post",
        //         url: "/main/cart/remove",
        //         data: {
        //             productId: productId,
        //             userIdNo: userIdNo
        //         },
        //         dataType: "json",
        //         headers: {'Content-Type': 'application/json'}
        //     });
        // }

        // 장바구니 담기
        if (cart.attr("data-btn-status") == 1) {
            let localCart = localStorage.getItem('cart') ? JSON.parse(localStorage.getItem('cart')) : [];
            console.log(localCart);
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
                $('.txt04.right__modal.add__cart').show();
            })
        }
    }

    // 비로그인 시
    if (userIdNo == 0) {
        const cartItem = localStorage.getItem('cart');
        let cond = null;

        if (cartItem !== null) {
            cond = JSON.parse(cartItem).find(product => product.productId === productId);
        }

        if (cond) {
            $('.txt04.right__modal.exist__cart').show();
            $('#existCartModalClose').click(() => {
                $('.txt04.right__modal.exist__cart').hide();
            });
        }

        if (cart.attr("data-btn-status") == 1 && !cond) {
            addToLocalStorageCart(productId, 0);
            $('.txt04.right__modal.add__cart').show();
        }
    }
})

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