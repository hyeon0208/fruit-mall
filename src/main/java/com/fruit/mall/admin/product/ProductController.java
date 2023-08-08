package com.fruit.mall.admin.product;

import com.fruit.mall.admin.category.CategoryService;
import com.fruit.mall.admin.image.FileInfo;
import com.fruit.mall.admin.image.Image;
import com.fruit.mall.admin.image.ImageService;
import com.fruit.mall.admin.product.dto.ProductRegistrationForm;
import com.fruit.mall.firebase.FireBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
    public String addProduct(@ModelAttribute ProductRegistrationForm form, @RequestPart("files") List<MultipartFile> files) throws IOException {
        Long categoryId = categoryService.selectIdByCategoryName(form.getSort());

        String updatedDescription = null;
        List<FileInfo> imageInfo = new ArrayList<>();
        for (MultipartFile file : files) {
            String firebaseImageUrl = fireBaseService.uploadFiles(file, PATH, file.getOriginalFilename());
            String fileName = file.getOriginalFilename();
            updatedDescription = form.getDescription().replaceAll("<img[^>]*src=[\"']([^\"^']*)[\"'][^>]*>", "<img src=\"" + firebaseImageUrl + "\" />");
            imageInfo.add(new FileInfo(firebaseImageUrl, fileName));
        }

        Product product = Product.builder()
                .categoryId(categoryId)
                .productName(form.getProductName())
                .productPrice(form.getPrice())
                .productStock(form.getStock())
                .productDescription(updatedDescription)
                .productDiscount(form.getDiscount())
                .productSaleStatus(ON_SALE)
                .build();
        productService.insertProduct(product);

        for (FileInfo fileInfo : imageInfo) {
            Image image = Image.builder()
                    .productId(product.getProductId())
                    .imageUrl(fileInfo.getFirebaseImageUrl())
                    .path(PATH)
                    .fileName(fileInfo.getFileName())
                    .build();
            imageService.insertImage(image);
        }

        return "success";
    }
}
