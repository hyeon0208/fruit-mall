let productImage = [];
let editorImages = [];

// 파일 고유 ID를 생성하는 함수
function generateFileId() {
    return Math.random().toString(36).substring(2, 10);
}

function showModal() {
    $(".txt05").css("display", "block");
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

function checkErrorAndShowModal(id) {
    if ($(id + '-error').text().length > 0) {
        $(".txt05 h5").html('입력 항목을 다시 확인해주세요.' + '<br><a>확인</a>')
        showModal();
        $('.txt05 a').click(function() {
            $('.txt05').hide();
        });
        $(id).focus();
    }
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

$(() => {
    showErrorMessage("#productName");
    showErrorMessage("#sort");
    showErrorMessage("#price");
    showErrorMessage("#stock");
    showErrorMessage("#productPicture");
    showErrorMessage("#description");

    $("#price").on("focusout", () => {
        calculateTotal();
    });

    $("#discount").on("focusout", () => {
        let regexDiscount = $("#discount").val().replace(/[^\d]/g, '');
        $("#discount").val(regexDiscount); // 입력값을 숫자만 남긴 값으로 대체
        let discountValue = parseInt(regexDiscount);
        if (discountValue < 1 || discountValue > 100) {
            if (discountValue !== 0) {
                $("#discount-error").text("1부터 100까지의 숫자만 입력해주세요.");
            } else {
                $("#discount-error").text("");
            }
        } else {
            $("#discount-error").text("");
        }
        if (isNaN(discountValue)) {
            $("#discount").val(0)
        }
        calculateTotal();
    });

    tinymce.init({
        selector: "#description",
        plugins: "paste image",
        height: 500,
        width: 900,
        toolbar: "undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | outdent indent | image", // 'image' 버튼 툴바에 추가
        paste_data_images: true,
        file_picker_types: 'image',
        file_picker_callback: function (callback, value, meta) {
            if (meta.filetype === 'image') {
                const input = document.createElement('input');
                input.setAttribute('type', 'file');
                input.setAttribute('accept', 'image/*');

                input.onchange = function () {
                    const file = this.files[0];
                    const fileId = generateFileId();
                    file.id = fileId;
                    editorImages.push(file);

                    const blobUrl = URL.createObjectURL(file);
                    callback(blobUrl, { alt: fileId });
                };
                input.click();
            }
        }
    });
});

$(document).on("change", "#productPicture", () => {
    const fileInput  = $("#productPicture")[0]; // 입력 요소를 가져온 시점
    const file = (fileInput && fileInput.files.length > 0) ? fileInput.files[0] : null; // 사용자가 파일을 선택한 시점
    if (file) {
        productImage.push(file);

        // 이미지 미리보기를 생성하는 로직
        const reader = new FileReader();

        // 파일 읽기가 완료되면 실행
        reader.onload = (e) => {
            $('#previewImage').attr('src', e.target.result);
        }
        reader.readAsDataURL(file); // file을 Data URL로 읽어오고, 읽기가 완료되면 이미지 미리보기로 표시
    }
});

$(document).on("click", "#cancelBtn", () => {
    $(".txt05 h5").html('작성중이던 항목이 모두 삭제됩니다. 취소하겠습니까?' + '<br><a href="/admin/product">확인</a>');
    showModal();
});

// 상품 등록 버튼 클릭
$(document).on("click", "#addBtn", (e) => {
    let formData = new FormData();
    e.preventDefault();

    checkErrorAndShowModal("#productName");
    checkErrorAndShowModal("#price");
    checkErrorAndShowModal("#sort");
    checkErrorAndShowModal("#discount");
    checkErrorAndShowModal("#stock");
    checkErrorAndShowModal("#description");

    if (productImage.length === 0) {
        $("#image-error").text("상품 이미지를 추가해주세요");
        $("#image-error").show();
    } else {
        $("#image-error").hide();
        formData.append("images", productImage[productImage.length - 1]);
    }

    tinymce.activeEditor.$('img').each((i, e) => {
        const fileId = $(e).attr('alt');
        const file = editorImages.find(f => f.id === fileId);
        if (file) {
            const blobUrl = URL.createObjectURL(file);
            $(e).attr('src', blobUrl);
            formData.append("images", file);
            formData.append("imageUrls", blobUrl);
        }
    });

    formData.append("productName", $("#productName").val());
    formData.append("price", parseInt($("#price").val().replace(/,/g, '')));
    formData.append("sort", $("#sort").val());
    formData.append("discount", parseInt($("#discount").val()));
    formData.append("stock", parseInt($("#stock").val()));
    formData.append("description", tinymce.get("description").getContent());

    axios({
        method: "post",
        url: "/add/product",
        data: formData,
        headers: {'Content-Type': 'multipart/form-data'}
    }).then(res => {
        productImage = [];
        editorImages = [];
        if (res.data === "success") {
            $(".txt05 h5").html('상품등록이 완료되었습니다.' + '<br><a href="/admin/product">확인</a>');
            showModal();
        } else {
            $(".txt05 h5").html('입력 항목을 다시 확인해주세요.' + '<br><a>확인</a>')
            showModal();
            $('.txt05 a').click(() => {
                $('.txt05').hide();
            });
        }
    }).catch(err => {
        $(".txt05 h5").html('입력 항목을 다시 확인해주세요.' + '<br><a>확인</a>')
        showModal();
        $('.txt05 a').click(() => {
            $('.txt05').hide();
        });
    });
});

// 상품 수정 버튼 클릭
$(document).on("click", "#editBtn", (e) => {
    const productId = $("#editForm").attr("data-productId");
    e.preventDefault();
    let formData = new FormData();

    checkErrorAndShowModal("#productName");
    checkErrorAndShowModal("#price");
    checkErrorAndShowModal("#sort");
    checkErrorAndShowModal("#discount");
    checkErrorAndShowModal("#stock");
    checkErrorAndShowModal("#description");

    formData.append("productImage",  productImage[productImage.length - 1]);

    tinymce.activeEditor.$('img').each((i, e) => {
        const fileId = $(e).attr('alt');
        const file = editorImages.find(f => f.id === fileId);
        if (file) {
            const blobUrl = URL.createObjectURL(file);
            $(e).attr('src', blobUrl);
            formData.append("editorImages", file);
            formData.append("imageUrls", blobUrl);
        }
    });

    formData.append("productName", $("#productName").val());
    formData.append("price", parseInt($("#price").val().replace(/,/g, '')));
    formData.append("sort", $("#sort").val());
    formData.append("discount", parseInt($("#discount").val()));
    formData.append("stock", parseInt($("#stock").val()));
    formData.append("description", tinymce.get("description").getContent());

    axios({
        method: "post",
        url: `/admin/edit/product/${productId}`,
        data: formData,
        headers: {'Content-Type': 'multipart/form-data'}
    }).then(res => {
        if (res.data === "success") {
            productImage = [];
            editorImages = [];
            $(".txt05 h5").html('상품수정이 완료되었습니다.' + '<br><a href="/admin/product">확인</a>');
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