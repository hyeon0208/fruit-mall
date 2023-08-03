package com.fruit.mall.admin.product;

import com.fruit.mall.admin.category.CategoryService;
import com.fruit.mall.admin.image.Image;
import com.fruit.mall.admin.image.ImageService;
import com.fruit.mall.firebase.FireBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final ImageService imageService;
    private final FireBaseService fireBaseService;

    private final static String PATH = "images";
    private final static String ON_SALE = "판매중";

    @PostMapping("/add/product")
    @ResponseBody
    public String addProduct(   @RequestParam("productName") String productName,
                                @RequestParam("price") int price,
                                @RequestParam("sort") String sort,
                                @RequestParam("discount") int discount,
                                @RequestParam("stock") int stock,
                                @RequestParam("description") String description,
                                @RequestParam("files") List<MultipartFile> files) throws IOException {

        Long categoryId = categoryService.selectIdByCategoryName(sort);


        for (MultipartFile file : files) {
            String firebaseImageUrl = fireBaseService.uploadFiles(file, PATH, file.getOriginalFilename());

            String updatedDescription = description.replaceAll("<img[^>]*src=[\"']([^\"^']*)[\"'][^>]*>", "<img src=\"" + firebaseImageUrl + "\" />");

            Product product = Product.builder()
                    .categoryId(categoryId)
                    .productName(productName)
                    .productPrice(price)
                    .productStock(stock)
                    .productDescription(updatedDescription)
                    .productDiscount(discount)
                    .productSaleStatus(ON_SALE)
                    .build();
            productService.insertProduct(product);

            Image image = Image.builder()
                    .productId(product.getProductId())
                    .imageUrl(firebaseImageUrl)
                    .path(PATH)
                    .fileName(file.getOriginalFilename())
                    .build();
            imageService.insertImage(image);
        }

        return "success";
    }
}
