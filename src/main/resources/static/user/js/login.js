$(() => {
    $("#loginFormBtn").on("click", (e) => {
        e.preventDefault();

        axios({
            method: "post",
            url: "/user/login",
            data:   {
                user_email: $("#id").val(),
                user_pwd: $("#pw").val()
            },
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        }).then((res) => {
            if(res.status == "200"){
                let localCarts = localStorage.getItem('cart') ? JSON.parse(localStorage.getItem('cart')) : [];
                axios({
                    method: "post",
                    url: "/api/v1/cart/merge",
                    data: { localCarts },
                    headers: {'Content-Type': 'application/json'}
                }).then(() => {
                    localStorage.removeItem('cart');
                });
                window.location.href = res.data.redirectUrl;
            } else {
                alert(res.data);
            }
        }).catch((error) => {
            alert(error.response.data.message);
        });
    });
});