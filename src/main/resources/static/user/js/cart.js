$(() => {
    // 세션 정보가 없으면 로컬 스토리지에 담긴 cart의 수를 표시
    if (!$('#localCartCount').data('login-user')) {
        const cart = localStorage.getItem('cart') ? JSON.parse(localStorage.getItem('cart')) : [];
        $('#localCartCount').text(cart.length);
    }

    // console.log($(".td_wrap").attr("data-session"))
    //
    // if ($(".td_wrap").attr("data-session") == null) { // loginUser is undefined or null
    //     let cartItems = JSON.parse(localStorage.getItem('cart')); // Get items from localStorage
    //
    //     // Check if there are items in the cart
    //     if (cartItems && cartItems.length > 0) {
    //         let $tableBody = $('#cart tbody'); // Select table body with jQuery
    //
    //         // Clear existing rows
    //         $tableBody.empty();
    //
    //         $.each(cartItems, function(index, item) {
    //             let row = `
    //                 <tr>
    //                     <td><input type="checkbox"></td>
    //                     <td>
    //                         <div class="td_wrap">
    //                             <img src="${item.imageUrl}" alt="${item.productName}">
    //                             <div class="txt">
    //                                 <span>${item.productName}</span>
    //                                 <span>
    //                                     <button>+</button>
    //                                     <!-- assuming quantity is stored as item.quantity -->
    //                                     <!-- need to handle this in backend as well-->
    //                                     <button>${item.quantity}</button>
    //                                     <!--<button>-</button>-->
    //                                 </span>
    //
    //                             </div>
    //
    //                             <!-- assuming price is stored as item.price -->
    //                             <!-- we add '원' after the price-->
    //                             <!--<div class="price"> -->
    //                                 <!--<span>${#numbers.formatDecimal(item.price, 0, 'COMMA')}원</span>-->
    //                                 <!--<button>x</button>-->
    //                             <!--</div>-->
    //
    //                         </div>
    //
    //                     </td>
    //
    //                     <!-- total price for each product (price * quantity) goes here. Assuming we have such a field or calculation method-->
    //                     <!--<td></tr>-->
    //
    //                     <!-- discount for each product goes here. Assuming we have such a field or calculation method-->
    //                     <!--<td></tr>-->
    //                 </tr>`;
    //
    //             $tableBody.append(row);
    //         });
    //     }
    // }
})

$(document).on("click", "#goHomeBtn", () => {
    window.location.replace("/");
});

$(document).on("click", "#goPaymentBtn", () => {
    window.location.replace("/user/payment");
});


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
        axios({
            method: "get",
            url: `/local/cart/${productId}`,
            params: {
                productId: productId
            },
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        }).then((res) =>{
            const product = res.data
            console.log(product)

            const cartItem = localStorage.getItem('cart');
            let exist = false;

            if (cartItem !== null) {
                exist = JSON.parse(cartItem).find(p => p.product.productId === product.productId);
            }

            if (exist) {
                $('.txt04.right__modal.exist__cart').show();
                $('#existCartModalClose').click(() => {
                    $('.txt04.right__modal.exist__cart').hide();
                });
            } else {
                addToLocalStorageCart(product, 1);
                $('.txt04.right__modal.add__cart').show();
                console.log(localStorage.getItem('cart'));
            }
        })
    }
})

function addToLocalStorageCart(product, quantity) {
    const cart = localStorage.getItem('cart') ? JSON.parse(localStorage.getItem('cart')) : [];
    const existingProduct = cart.find(p => p.product.productId === product.productId);
    if (!existingProduct) {
        cart.push({ product, quantity });
    }
    localStorage.setItem('cart', JSON.stringify(cart));
}