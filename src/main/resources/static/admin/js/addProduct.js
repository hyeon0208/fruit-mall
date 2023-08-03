function showModal() {
    $(".txt05").css("display", "block");
}

const formData = new FormData();

function uploadImage() {
    const fileInput  = $("#productPicture")[0]; // 입력 요소를 가져온 시점
    const file = (fileInput && fileInput.files.length > 0) ? fileInput.files[0] : null; // 사용자가 파일을 선택한 시점

    if (file) {
        const path = "images"; // 이미지 저장 경로 (원하는 경로로 변경 가능)
        const fileName = file.name; // 업로드할 이미지 파일의 원래 이름 사용

        formData.append("file", file);
        formData.append("path", path);
        formData.append("fileName", fileName);

        // FireBaseService의 uploadFiles 메서드를 호출하여 이미지 업로드
        axios({
            method: "post",
            url: "/upload",
            data: formData,
            dataType: "json",
        }).then(res => {
                // 이미지 업로드가 성공한 경우 처리
                const imageUrl = res.data; // 업로드된 이미지의 다운로드 URL
                const previewImg = document.getElementById("previewImage");
                previewImg.src = imageUrl; // 업로드된 이미지의 미리보기를 이미지 태그에 표시
            })
    }
}

function showErrorMessage(id) {
    $(id).on("focusout", () => {
        if ($(id).val().trim() === "") {
            $(`${id}-error`).text("필수 입력 값을 입력해주세요.");
        } else {
            $(`${id}-error`).text("");
            if (id === "#price") {
                let price = $(id).val().replace(/[^0-9]/g, '');
                let formattedPrice = price.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                $(id).val(formattedPrice);
                if ($(id).val() === "") {
                    $(`${id}-error`).text("숫자만 입력 가능합니다.");
                }
            }
        }
    });
}

function calculateTotal() {
    let price = parseInt($("#price").val().replace(/,/g, ''));
    let discount = parseInt($("#discount").val());
    let totalPrice = price * (100 - discount) / 100;
    if (isNaN(totalPrice)) {
       $("#totalPrice").val(0);
    } else {
        let formattedTotalPrice = totalPrice.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        $("#totalPrice").val(formattedTotalPrice);
    }
}

function checkErrorAndShowModal(id) {
    if ($(id + '-error').text().length > 0) {
        $(".txt05 h5").html('입력 항목을 다시 확인해주세요.' + '<br><a>확인</a>')
        showModal();
        $('.txt05 a').click(function() {
            $('.txt05').hide();
        });
        $(id).focus();
        return true; // 필수 입력 값이 없는 경우 true 반환
    }
    return false;
}

$(() => {
    showErrorMessage("#productName");
    showErrorMessage("#sort");
    showErrorMessage("#price");
    showErrorMessage("#stock");
    showErrorMessage("#description");

    $("#discount").on("focusout", () => {
        let regexDiscount = $("#discount").val().replace(/[^0-9]/g, '');
        if (regexDiscount === "") {
            $("#discount-error").text("숫자만 입력가능 합니다.");
        } else if (parseInt(regexDiscount) < 1 || parseInt(regexDiscount) > 100) {
            $("#discount-error").text("1 ~ 100 까지의 숫자만 입력가능합니다.");
        } else {
            $("#discount-error").text("");
        }
    });

    $("#productPicture").on("change", () => {
        if ($("#productPicture").val() == null) {
            $("#image-error").text("이미지를 추가해주세요.")
        } else {
            $("#image-error").text("")
        }
    });

    // 파일 선택 시 업로드 함수를 자동 호출하도록 change 이벤트 추가
    $("#productPicture").on("change", () => {
        uploadImage();
    })

    // TinyMCE 초기화
    tinymce.init({
        selector: "#description", // TinyMCE를 적용할 textarea 요소의 선택자를 지정
        plugins: "paste image imagetools", // 'paste', 'image', 'imagetools' 플러그인 추가
        height: 500,
        width: 900,
        toolbar: "undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | outdent indent | image", // 'image' 버튼 툴바에 추가
        paste_data_images: true, // 이미지 붙여넣기 설정 활성화
        automatic_uploads: true, // 이미지를 업로드할 때 자동으로 업로드하도록 설정
        file_picker_types: 'image', // TinyMCE에서 이미지를 선택할 때, 이미지 파일만 선택 (옵션 : media, file 등)
        images_upload_handler: function (blobInfo, success) { // 이미지를 업로드하는 핸들러 함수
            const formData = new FormData();
            // blobInfo : TinyMCE에서 이미지 업로드 시 사용되는 정보를 담고 있는 객체
            formData.append('file', blobInfo.blob());
            formData.append('path', 'images');
            formData.append('fileName', blobInfo.filename());
            axios({
                method: "post",
                url: "/upload",
                data: formData,
                dataType: "json",
            }).then(res => {
                const imageUrl = res.data; // 업로드된 이미지의 다운로드 URL
                success(imageUrl); // 성공 시 이미지 URL을 TinyMCE 에디터에 전달
            })
        }
    });

    // #discount 필드의 값이 변경될 때 calculateTotal() 함수 호출
    $("#discount").on("focusout", () => {
        calculateTotal();
    });


    $("#cancelBtn").on("click", () => {
        $(".txt05 h5").html('작성중이던 항목이 모두 삭제됩니다. 취소하겠습니까?' + '<br><a href="/admin/product">확인</a>');
        showModal();
    });

    $("#addBtn").on("click", (e) => {
        e.preventDefault();

        if (checkErrorAndShowModal("#productName")) return;
        if (checkErrorAndShowModal("#price")) return;
        if (checkErrorAndShowModal("#sort")) return;
        if (checkErrorAndShowModal("#discount")) return;
        if (checkErrorAndShowModal("#stock")) return;
        if (checkErrorAndShowModal("#description")) return;

        formData.append("productName", $("#productName").val());
        formData.append("price", parseInt($("#price").val().replace(/,/g, '')));
        formData.append("sort", $("#sort").val());
        formData.append("discount", parseInt($("#discount").val()));
        formData.append("stock", parseInt($("#stock").val()));
        formData.append("description", tinymce.get("description").getContent());
        formData.append("imageUrl", $("#previewImage").attr("src"));

        axios({
            method: "post",
            url: "/add/product",
            data: formData,
            headers: {'Content-Type': 'multipart/form-data'}
        }).then(res => {
            if (res.data === "success") {
                $(".txt05 h5").html('상품등록이 완료되었습니다.' + '<br><a href="/admin/product">확인</a>');
                showModal();
            } else {
                $(".txt05 h5").html('입력 항목을 다시 확인해주세요.' + '<br><a>확인</a>')
                showModal();
                $('.txt05 a').click(function () {
                    $('.txt05').hide();
                });
            }
        });
    });
});