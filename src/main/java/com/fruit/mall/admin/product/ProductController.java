package com.fruit.mall.admin.product;

import com.fruit.mall.admin.category.CategoryService;
import com.fruit.mall.admin.image.Image;
import com.fruit.mall.admin.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final ImageService imageService;


    @PostMapping("/add/product")
    @ResponseBody
    public String addProduct(   @RequestParam("productName") String productName,
                                @RequestParam("price") int price,
                                @RequestParam("sort") String sort,
                                @RequestParam("discount") int discount,
                                @RequestParam("stock") int stock,
                                @RequestParam("description") String description,
                                @RequestParam("imageUrl") String imageUrl,
                                @RequestParam("fileName") String fileName,
                                @RequestParam("path") String path) {

        Long categoryId = categoryService.selectIdByCategoryName(sort);
        Product product = Product.builder()
                .categoryId(categoryId)
                .productName(productName)
                .productPrice(price)
                .productStock(stock)
                .productDescription(description)
                .productDiscount(discount)
                .productSaleStatus("판매중")
                .build();
        productService.insertProduct(product);

        Image image = Image.builder()
                .productId(product.getProductId())
                .imageUrl(imageUrl)
                .path(path)
                .fileName(fileName)
                .build();
        imageService.insertImage(image);

        return "success";
    }
}
