$(() => {
    $(".productPrice").each(function() {
        const price = $(this).data('price');
        if (typeof price !== 'undefined') {
            $(this).text(price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','));
        }
    });
})
