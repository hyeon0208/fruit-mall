let lastHeartbeat = Date.now();

$(() => {
    axios({
        method: "get",
        url: "/api/v1/isLogin"
    }).then(res => {
        if (res.data) {
            startSSE();
        }
    })

    $("#showNotifications").on("click", () => {
        $('#notificationList').slideToggle();
        $(".notification-dot").remove();
    });

    $("#notificationList .list-group-item").on("click", (e) => {
        const target = $(e.currentTarget);
        const id = target.find(".fw-bold").data("id")
        axios({
            url: "api/v1/notifications/read",
            method: "post",
            data: { notificationsId: id },
            dataType: "json",
            headers: {'Content-Type': 'application/json'}
        }).then(res => {
            window.location.href = `/user/review/${res.data}`;
        })
    });
});


function startSSE() {
    let sse = new EventSource("/api/sse-connection");

    sse.onmessage = (event) => {
        if (event.data != "connected!") {
            $("#showNotifications").append('<span class="notification-dot"></span>');
        }
    };

    sse.addEventListener("heartbeat", (event) => {
        lastHeartbeat = Date.now();
    });

    sse.onerror = (event) => {
        if (event.target.readyState === EventSource.CLOSED || Date.now() - lastHeartbeat > 60000) {
            sse.close();
        }
    };
}