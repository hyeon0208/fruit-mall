let lastHeartbeat = Date.now();

$(() => {
    startSSE();
});

$("#showNotifications").on("click", () => {
    console.log("버튼 클릭");
    $('#notificationList').slideToggle();
    $(".notification-dot").remove();
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
        console.error(`[error] ${event.message}`);
        if (event.target.readyState === EventSource.CLOSED || Date.now() - lastHeartbeat > 60000) {
            retryCount++;
            setTimeout(startSSE, 5000); // Retry every 5 seconds
        }
    };
}