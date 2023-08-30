$(() => {
    const isChecked = $("#cartAllChk").prop("checked");
    $("input[type='checkbox']").prop("checked", isChecked);

    // 세션 정보가 없으면 로컬 스토리지에 담긴 cart의 수를 표시
    if (!$('#localSideBarCartCount').data('login-user')) {
        const cart = localStorage.getItem('cart') ? JSON.parse(localStorage.getItem('cart')) : [];
        $('#localCartCount').text(cart.length);
        $('#localSideBarCartCount').text(cart.length);
    }

    // 세션 정보가 없으면 로컬 스토리지에 담긴 cart를 cart.html에 표시
    if (!$('#cart').data('session')) {
        const cartItems = localStorage.getItem('cart') ? JSON.parse(localStorage.getItem('cart')) : [];
        const cartList = cartItems.map((cart) => {
            const product = cart.product;
            const productCount = product.productCount
            const discountStyle = product.productDiscount > 0 ? product.productDiscount + "%" : "";
            return `<tr>
                    <td>
                        <input type="checkbox" class="cartChk">
                    </td>
                    
                    <td>
                        <div class="td_wrap">
                            <img src="${product.imageUrl}" alt="${product.productName}">
                            
                            <div class="txt">
                                <span>${product.productName}</span>
                                <span>
                                    <button class="increaseProductCnt">+</button>
                                    <button class="productCnt" data-product-id="${product.productId}" value="${productCount}">${productCount}</button>
                                    <button class="decreaseProductCnt" ${productCount}>-</button>
                                </span>
                            </div>

                            <div class="price" data-status="${product.productSaleStatus}">
                                <span>${product.productPrice.toLocaleString()}원</span>
                                <button class="delCartProduct">x</button>
                            </div>
                        </div>
                    </td> 
                    
                    <td class="sumPrice" data-cart-price="${product.productPrice}">${(product.productPrice * productCount).toLocaleString()}원</td>
                    <td class="discountRate">${discountStyle}</td>
                </tr>`;
        });
        $('#showCart').empty().append(cartList);

        const isChecked = $("#cartAllChk").prop("checked");
        $("input[type='checkbox']").prop("checked", isChecked);
    }


    $(".productCnt").each((i, e) => {
        const target = $(e);
        const productCount = parseInt(target.val());

        if (productCount > 1) {
            target.siblings(".decreaseProductCnt").show();
        } else {
            target.siblings(".decreaseProductCnt").hide();
        }
    });

    updateTotalOrderPriceArea();
});

// 모두 체크
$(document).on("click", "#cartAllChk", (e) => {
    const isChecked = $(e.target).prop("checked");
    $("input[type='checkbox']").prop("checked", isChecked);

    let count = 0;
    $("input[type='checkbox']:not(#cartAllChk)").each((i, e) => {
        if ($(e).prop("checked")) {
            count++;
        }
    });
    $("#localCartCount").text(count);
    $("#cartCount").text(count)
});

// 단일 체크
$(document).on("click", ".cartChk", (e)=> {
    const isChecked = $(e.target).prop("checked");

    let count = 0;
    $("input[type='checkbox']:not(#cartAllChk)").each((i, e) => {
        if ($(e).prop("checked")) {
            count++;
        }
    });
    $('#localCartCount').text(count);
    $("#cartCount").text(count)
});


$(document).on("click", "#goHomeBtn", () => {
    window.location.replace("/");
});

$(document).on("click", "#goPaymentBtn", () => {
    // 체크박스로 선택된 상품들 중 "품절"인 상품이 있는지 확인
    const soldOutProducts = [];
    $("input[type='checkbox']:checked:not(#cartAllChk)").each((i, checkbox) => {
        const productRow = $(checkbox).closest("tr");
        const productStatus = productRow.find(".price").data("status");

        console.log(productStatus);
        if (productStatus === "품절") {
            soldOutProducts.push(i);
        }
    });

    if (soldOutProducts.length > 0) {
        alert("품절상품은 구매 불가능합니다.");
        return;
    }
    window.location.replace("/user/order");
});

$(document).on("click", "#redirectLoginBtn", () => {
    const product = $("#addDetailToCartBtn");
    const productId = product.data("cart-product-id");
    axios({
        method: "get",
        url: `/local/cart/${productId}`,
        params: {
            productId: productId
        },
        dataType: "json",
        headers: {'Content-Type': 'application/json'}
    }).then((res) =>{
        const product = res.data;
        product.productCount = parseInt($("#detailProductCnt").val());
        addToLocalStorageCart(res.data);
    })
    window.location.replace("/login");
});

$(document).on("click", ".increaseProductCnt", (e) => {
    let cnt = $(e.currentTarget).siblings(".productCnt");
    let decreaseBtn = $(e.currentTarget).siblings(".decreaseProductCnt");
    let value = parseInt(cnt.val());
    cnt.text(value + 1);
    cnt.val(value + 1);

    if (parseInt(cnt.val()) > 1) {
        decreaseBtn.show();
    }

    if ($('#cart').data('session')) { // 회원일 경우
        axios({
            method: "post",
            url: "/cart/increaseProductCnt",
            data: {
                productCount: cnt.val(),
                cartId: cnt.attr("data-cart-id")
            },
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        });
    }

    if (!$('#cart').data('session')) {  // 비회원인 경우 로컬 스토리지 데이터 수정
        const cartItems = JSON.parse(localStorage.getItem('cart'));
        const cart = cartItems.find(cart => cart.product.productId == cnt.attr("data-product-id"));
        if (cart) {
            cart.product.productCount++;
            localStorage.setItem('cart', JSON.stringify(cartItems));
        }
    }

    const sumPrice = $(e.currentTarget).closest('tr').find(".sumPrice");
    sumPrice.text((parseInt(sumPrice.data("cart-price")) * cnt.val()).toLocaleString() + "원");

    updateTotalOrderPriceArea();
});

$(document).on("click", ".decreaseProductCnt", (e) => {
    let cnt = $(e.currentTarget).siblings(".productCnt");
    let decreaseBtn = $(e.currentTarget);
    let value = parseInt(cnt.val());

    if (parseInt(cnt.val()) > 1) {
        cnt.text(value - 1);
        cnt.val(value - 1);

        if (parseInt(cnt.val()) <= 1) {
            decreaseBtn.hide();
        }
    }

    if ($('#cart').data('session')) { // 회원일 경우
        axios({
            method: "post",
            url: "/cart/decreaseProductCnt",
            data: {
                productCount: cnt.val(),
                cartId: cnt.attr("data-cart-id")
            },
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        });
    }

    if (!$('#cart').data('session')) {  // 비회원인 경우 로컬 스토리지 데이터 수정
        const cartItems = JSON.parse(localStorage.getItem('cart'));
        const cart = cartItems.find(cart => cart.product.productId == cnt.attr("data-product-id"));
        if (cart) {
            cart.product.productCount--;
            localStorage.setItem('cart', JSON.stringify(cartItems));
        }
    }

    const sumPrice = $(e.currentTarget).closest('tr').find(".sumPrice");
    sumPrice.text((parseInt(sumPrice.data("cart-price")) * cnt.val()).toLocaleString() + "원");

    updateTotalOrderPriceArea();
});

$(document).on("click", ".addCartBtn", (e) => {
    let cart = $(e.currentTarget);
    const productId = cart.data("product-id");
    const userIdNo = cart.data("user-id");

    if(cart.attr("data-btn-status") == 0) {
        cart.attr("data-btn-status", 1);
    }

    // 로그인 시
    if (userIdNo != 0) {
        // 장바구니 담기
        if (cart.attr("data-btn-status") == 1) {
            let localCart = localStorage.getItem('cart') ? JSON.parse(localStorage.getItem('cart')) : [];
            axios({
                method: "post",
                url: "/main/cart/add",
                data: {
                    productId: productId,
                    userIdNo: userIdNo,
                    productCount: 1,
                    localCart: localCart
                },
                dataType: "json",
                headers: {'Content-Type': 'application/json'}
            }).then((res) =>{
                $('.txt04.right__modal.add__cart').show();
                $('#closeCartModal').click(() => {
                    $('.txt04.right__modal.add__cart').hide();
                });
            }).catch((res) => {
                $('.txt04.right__modal.exist__cart').show();
                $('#existCartModalClose').click(() => {
                    $('.txt04.right__modal.exist__cart').hide();
                });
            });
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
                addToLocalStorageCart(product);
                $('.txt04.right__modal.add__cart').show();
                $('#closeCartModal').click(() => {
                    $('.txt04.right__modal.add__cart').hide();
                });
            }
        })
    }
});

$(document).on("click", ".delCartProduct", (e) => {
    if ($('#cart').data('session')) {  // 회원일 경우
        const cartId = $(e.currentTarget).closest('tr').find(".productCnt").attr("data-cart-id")

        axios({
            method: "delete",
            url: `/cart/delete/${cartId}`
        }).then(res => {
            window.location.href = res.data;
        });
    }

    if (!$('#cart').data('session')) { // 비회원일 경우
        const cartItems = JSON.parse(localStorage.getItem('cart'));
        const productId = $(e.currentTarget).closest('tr').find(".productCnt").attr("data-product-id");
        const newCartItems = cartItems.filter(cart => cart.product.productId != productId);
        localStorage.setItem('cart', JSON.stringify(newCartItems));
        $(e.currentTarget).closest('tr').remove();
    }
});

function addToLocalStorageCart(product) {
    const cart = localStorage.getItem('cart') ? JSON.parse(localStorage.getItem('cart')) : [];
    const existingProduct = cart.find(p => p.product.productId === product.productId);
    if (!existingProduct) {
        cart.push({ product });
    }
    localStorage.setItem('cart', JSON.stringify(cart));
}

function updateTotalOrderPriceArea() {
    updateCartTotalPrice();
    updateCartTotalDiscount();
    updateDeliveryFee();
    updatePayPrice();
}

function updateCartTotalPrice() {
    let totalPrice = 0;
    $(".sumPrice").each((i, e) => {
        const price = parseInt($(e).text().replace(/[^0-9]/g, ''));
        totalPrice += price;
    });
    $("#cartTotalPrice").text(totalPrice.toLocaleString() + "원");
}

function updateCartTotalDiscount() {
    let totalDiscount = 0;
    $(".discountRate").each((i, e) => {
        const price = parseInt( $(e).siblings(".sumPrice").text().replace(/[^0-9]/g, ''));
        let discountRate = parseInt($(e).text().replace(/[^0-9]/g, ''));
        if (!discountRate) {
            discountRate = 0
        }
        totalDiscount += (price * (discountRate / 100));
    });
    $("#cartTotalDiscount").text("-" + totalDiscount.toLocaleString() + "원");
}

function updateDeliveryFee() {
    const deliveryFee = 3000;
    const totalPrice = parseInt($("#cartTotalPrice").text().replace(/[^0-9]/g, ''));
    const totalDiscount = parseInt($("#cartTotalDiscount").text().replace(/[^0-9]/g, ''));
    if (totalPrice - totalDiscount >= 50000) {
        $("#deliveryFee").text("무료");
    } else {
        $("#deliveryFee").text(deliveryFee.toLocaleString() + "원");
    }
}

function updatePayPrice() {
    const totalPrice = parseInt($("#cartTotalPrice").text().replace(/[^0-9]/g, ''));
    const totalDiscount = parseInt($("#cartTotalDiscount").text().replace(/[^0-9]/g, ''));
    let deliveryFee = $("#deliveryFee").text();
    if (deliveryFee === "무료") {
        deliveryFee = 0;
    } else {
        deliveryFee = parseInt(deliveryFee.replace(/[^0-9]/g, ''));
    }
    const payPrice = totalPrice - totalDiscount + deliveryFee;
    $("#payPrice").text(payPrice.toLocaleString() + "원");
}