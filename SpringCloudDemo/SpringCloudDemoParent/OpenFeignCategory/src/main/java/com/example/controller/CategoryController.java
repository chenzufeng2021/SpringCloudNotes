package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.entity.Product;
import com.example.feignClient.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chenzufeng
 */

@RestController
@Slf4j
public class CategoryController {
    /**
     * 注入客户端对象
     */
    @Autowired
    private ProductClient productClient;

    @GetMapping("Category")
    public String getCategory() {
        log.info("======================");
        log.info("进入品类服务......");

        String product = productClient.getProduct();
        log.info("品类服务调用商品服务中getProduct方法：{}", product);

        String parameterQueryString = productClient.getParameterQueryString("chenzf", 27);
        log.info("品类服务调用商品服务中getParameterQueryString方法：{}", parameterQueryString);

        String parameter = productClient.getParameter("zufeng", 28);
        log.info("品类服务调用商品服务中getParameter方法：{}", parameter);

        String objectParameter = productClient.getObjectParameter(new Product(1, "chen", 20000.0, new Date()));
        log.info("品类服务调用商品服务中getObjectParameter方法：{}", objectParameter);

        String arrayParameter = productClient.getArrayParameter(new String[]{"chen", "zu", "feng"});
        log.info("品类服务调用商品服务中getArrayParameter方法：{}", arrayParameter);

        String listParameter = productClient.getListParameter(new String[]{"c", "z", "f"});
        log.info("品类服务调用商品服务中getListParameter方法：{}", listParameter);

        log.info("======================");
        return "品类服务调用商品服务中getProduct方法：" + product + "\n" +
                " 和getParameterQueryString方法：" + parameterQueryString + "\n" +
                " 和getParameter方法：" + parameter + "\n" +
                " 和getObjectParameter方法：" + objectParameter + "\n" +
                " 和getArrayParameter方法：" + arrayParameter + "\n" +
                " 和getListParameter方法：" + listParameter;
    }

    @GetMapping("Product")
    public Product getProduct() {
        log.info("======================");
        log.info("进入品类服务......");
        Product returnedProduct = productClient.returnProduct(1);
        log.info("品类服务调用商品服务中returnProduct方法：{}", returnedProduct);
        log.info("======================");
        return returnedProduct;
    }

    @GetMapping("ListProduct")
    public List<Product> getListProduct() {
        log.info("======================");
        log.info("进入品类服务......");
        List<Product> products = productClient.findByCategoryId(0);
        products.forEach(product -> log.info("Product：{}", product));
        log.info("品类服务调用商品服务中returnProduct方法：{}", products);
        log.info("======================");
        return products;
    }

    @GetMapping("MapProduct")
    public String getMapProduct() {
        String objectMapString = productClient.findByCategoryIdAndPage(1, 5, 1);

        /*
         * 自定义Json反序列化（对象转为Json——序列化；Json字符串转为对象——反序列化）
         * import com.alibaba.fastjson.JSONObject;
         * public class JSONObject extends JSON implements Map<String, Object>...
         */
        JSONObject jsonObject = JSONObject.parseObject(objectMapString);
        log.info("======================");
        log.info("total：{}", jsonObject.get("total"));
        log.info("rows：{}", jsonObject.get("rows"));
        log.info("======================");

        /*
         * 二次Json反序列化
         * rows：[{"name":"chen","birth":"2021-06-15T11:17:10.276+0000","id":1,"salary":16000.0},
         *            {"name":"zufeng","birth":"2021-06-15T11:17:10.276+0000","id":2,"salary":20000.0}]
         */
        Object rows = jsonObject.get("rows");
        List<Product> products = JSONObject.parseArray(rows.toString(), Product.class);
        products.forEach(product -> log.info("product：{}", product));

        return objectMapString;
    }
}