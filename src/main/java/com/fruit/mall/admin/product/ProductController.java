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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public String addProduct(@ModelAttribute ProductRegistrationForm form,
                             @RequestParam("images") List<MultipartFile> images,
                             @RequestParam(value = "imageUrls", required = false) List<String> imageUrls) throws IOException {

        List<FileInfo> imageInfo = new ArrayList<>();
        MultipartFile productImage = images.get(0);
        images.remove(0);
        String productFileName = productImage.getOriginalFilename();
        String productFirebaseImageUrl = fireBaseService.uploadFiles(productImage, PATH, productFileName);
        imageInfo.add(new FileInfo(productFirebaseImageUrl, productFileName));

        String description = form.getDescription();
        for (int i = 0; i < images.size(); i++) {
            MultipartFile file = images.get(i);
            String editorFileName = images.get(i).getOriginalFilename();
            String editorFirebaseImageUrl = fireBaseService.uploadFiles(file, PATH, editorFileName);
            String blobUrl = imageUrls.get(i);
            description = productService.getUpdatedDescription(description, editorFirebaseImageUrl, blobUrl);
            imageInfo.add(new FileInfo(editorFirebaseImageUrl, editorFileName));
        }
        Long categoryId = categoryService.selectIdByCategoryName(form.getSort());

        Product product = Product.builder()
                .categoryId(categoryId)
                .productName(form.getProductName())
                .productPrice(form.getPrice())
                .productStock(form.getStock())
                .productDescription(description)
                .productDiscount(form.getDiscount())
                .productSaleStatus(ON_SALE)
                .build();
        productService.insertProduct(product);

        for (int i = 0; i < imageInfo.size(); i++) {
            Image image = Image.builder()
                    .productId(product.getProductId())
                    .imageUrl(imageInfo.get(i).getFirebaseImageUrl())
                    .path(PATH)
                    .fileName(imageInfo.get(i).getFileName())
                    .build();
            imageService.insertImage(image);
        }

        return "success";
    }
}
