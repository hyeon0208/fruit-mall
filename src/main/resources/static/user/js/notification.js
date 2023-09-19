$(document).ready(() => {
    let sse = new EventSource("/api/sse-connection");

    sse.onmessage = function (event) {
        console.log(`[message] Data received from server: ${event.data}`);
    };

    sse.onerror = function (error) {
        console.error(`[error] ${error.message}`);
    };
});