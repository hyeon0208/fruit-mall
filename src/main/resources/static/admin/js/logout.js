$(document).ready(() => {
    $("#logout").on("click", (e) => {
        e.preventDefault();
        // 로그아웃 폼을 선택하고 제출
        $("#logout-form").submit();
    });
});