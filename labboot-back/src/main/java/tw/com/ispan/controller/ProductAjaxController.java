package tw.com.ispan.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.com.ispan.domain.ProductBean;
import tw.com.ispan.dto.ProductResponse;
import tw.com.ispan.service.ProductService;

@RestController
@RequestMapping("/ajax/pages/products")
public class ProductAjaxController {
    private ProductService productService;
    public ProductAjaxController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Integer id) {
        ProductBean product = productService.findById(id);
        if(product==null) {
            return new ProductResponse(null, null, null, new ArrayList<>());
        } else {
            List<ProductBean> products = new ArrayList<>();
            products.add(product);
            return new ProductResponse(null, null, null, products);
        }
    }

    @PostMapping("/find")
    public ProductResponse find(@RequestBody String body) {
        Long count = productService.count(body);
        List<ProductBean> products = productService.find(body);
        if(products == null || products.isEmpty()) {
            return new ProductResponse(null, null, count, new ArrayList<>());
        } else {
            return new ProductResponse(null, null, count, products);
        }
    }

    @PostMapping
    public ProductResponse create(@RequestBody String body) {
        JSONObject obj = new JSONObject(body);
        Integer id = obj.isNull("id") ? null : obj.getInt("id");
        if(id == null) {
            return new ProductResponse(false, "id是必要欄位", null, null);
        } else if(productService.exists(id)) {
            return new ProductResponse(false, "id已存在", null, null);
        } else {
            ProductBean insert = productService.create(body);
            if(insert == null) {
                return new ProductResponse(false, "新增失敗", null, null);
            } else {
                return new ProductResponse(true, "新增成功", null, null);
            }
        }
    }

    @PutMapping("/{id}")
    public ProductResponse modify(@PathVariable Integer id, @RequestBody String body) {
        if(id == null) {
            return new ProductResponse(false, "id是必要欄位", null, null);
        } else if(!productService.exists(id)) {
            return new ProductResponse(false, "id不存在", null, null);
        } else {
            ProductBean update = productService.modify(body);
            if(update == null) {
                return new ProductResponse(false, "修改失敗", null, null);
            } else {
                return new ProductResponse(true, "修改成功", null, null);
            }
        }
    }

    @DeleteMapping("/{id}")
    public ProductResponse remove(@PathVariable Integer id) {
        if(id == null) {
            return new ProductResponse(false, "id是必要欄位", null, null);
        } else if(!productService.exists(id)) {
            return new ProductResponse(false, "id不存在", null, null);
        } else {
            if(!productService.remove(id)) {
                return new ProductResponse(false, "刪除失敗", null, null);
            } else {
                return new ProductResponse(true, "刪除成功", null, null);
            }
        }
    }
}
