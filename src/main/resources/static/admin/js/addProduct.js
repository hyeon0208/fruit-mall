function showModal() {
    $(".txt05").css("display", "block");
}

const imageFiles = [];
const formData = new FormData();

function uploadImage() {
    const fileInput  = $("#productPicture")[0]; // 입력 요소를 가져온 시점
    const file = (fileInput && fileInput.files.length > 0) ? fileInput.files[0] : null; // 사용자가 파일을 선택한 시점

    if (file) {
        // 이미지 미리보기를 생성하는 로직
        const reader = new FileReader();

        // 파일 읽기가 완료되면 실행
        reader.onload = (e) => {
            $('#previewImage').attr('src', e.target.result);
        }
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
        let regexDiscount = $("#discount").val().replace(/[^\d]/g, '');
        $("#discount").val(regexDiscount); // 입력값을 숫자만 남긴 값으로 대체
        let discountValue = parseInt(regexDiscount);
        if (discountValue < 1 || discountValue > 100) {
            $("#discount-error").text("1부터 100까지의 숫자만 입력해주세요.");
        } else {
            $("#discount-error").text("");
        }
    });

    $("#productPicture").on("change", () => {
        const fileList = $("#productPicture")[0].files;
        for (const file of fileList) {
            imageFiles.push(file);
        }
        uploadImage();
    });

    // TinyMCE 초기화
    tinymce.init({
        selector: "#description", // TinyMCE를 적용할 textarea 요소의 선택자를 지정
        plugins: "paste image imagetools", // 'paste', 'image', 'imagetools' 플러그인 추가
        height: 500,
        width: 900,
        toolbar: "undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | outdent indent | image", // 'image' 버튼 툴바에 추가
        paste_data_images: true, // 이미지 붙여넣기 설정 활성화
        file_picker_types: 'image', // TinyMCE에서 이미지를 선택할 때, 이미지 파일만 선택 (옵션 : media, file 등)
        images_upload_handler(blobInfo, success) { // 이미지를 업로드하는 핸들러 함수
            // blobInfo : TinyMCE에서 이미지 업로드 시 사용되는 정보를 담고 있는 객체
            const file = new File([blobInfo.blob()], blobInfo.filename());
            imageFiles.push(file);
            success(URL.createObjectURL(file)); // Blob 객체의 임시 URL을 생성해 이미지 미리보기 적용
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

        for (const file of imageFiles) {
            formData.append("files", file);
        }

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