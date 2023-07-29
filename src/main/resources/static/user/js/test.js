$(document).on('click', '#alert', () => {
    // Axios 사용해 Post 요청
    axios({
        method: "post",
        url: "/user/testSelect", // "/alert" URL 로 요청
        data: {
            "title": "title 테스트", // 전송할 데이터의 제목 필드
            "msg": "msg 테스트입니다." // 전송할 데이터의 메시지 필드
        },
        dataType: "JSON", // 응답 데이터 타입
        headers: {'Content-Type': 'application/json'} // 요청 헤더에 JSON 형식으로 데이터 전송
    }).then(res => {
        // 서버로부터의 응답 데이터는 res 변수에 저장

        let testList = [];
        testList = res.data

        for (const i in testList) {
            $("body").append(`
                <div>
                    <span> 타이틀 : ${testList[i].title} | </span>
                    <span> 메시지 : ${testList[i].msg} | </span>
                    <hr>
                </div>
                `
        )};
        // 응답 데이터(res.data)에 포함된 "alert.html"을 페이지에 추가
        $("body").append(testList[0].msg);
    });
});

// #confirmBtn 요소를 클릭시 발생하는 이벤트
$(document).on('click', '#confirmBtn', () => {
    // 페이지에서 "txt04" 클래스를 가진 요소를 모두 제거.
    $('.txt04').remove();
})