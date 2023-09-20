let lastHeartbeat = Date.now();

$(() => {
    startSSE();

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
        })
    });
});


function startSSE() {
    let sse = new EventSource("/api/sse-connection");
    let retryCount = 0;

    if (retryCount >= 3) {
        return;
    }

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
            retryCount++;
            setTimeout(startSSE, 5000); // Retry every 5 seconds
        }
    };
}