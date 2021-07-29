package com.example.controller;

import com.example.entity.Product;
import com.example.vos.CollectionValueObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author chenzufeng
 */

@RestController
@Slf4j
public class ProductController {
    @Value("${server.port}")
    private Integer port;

    @GetMapping("Product")
    public String getProduct() {
        log.info("======================");
        log.info("进入商品服务......");
        log.info("提供当前服务的端口[{}]", port);
        log.info("======================");
        return "提供getProduct商品服务的端口为" + port;
    }

    /**
     * 定义接收零星参数（queryString）
     * @param name 姓名
     * @param age 年龄
     * @return String
     */
    @GetMapping("ParameterQueryString")
    public String getParameterQueryString(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        log.info("name={}  age={}", name, age);
        return "提供getParameterQueryString商品服务的端口为" + port;
    }

    /**
     * 定义调用服务中的接口
     * /Parameter/{name}/{age}（路径传参）
     * @param name 姓名
     * @param age 年龄
     * @return String
     */
    @GetMapping("Parameter/{name}/{age}")
    public String getParameter(@PathVariable("name") String name, @PathVariable("age") Integer age) {
        log.info("name={}  age={}", name, age);
        return "提供getParameter商品服务的端口为" + port;
    }

    /**
     * 定义一个接受对象类型参数的接口
     * RequestBody以Json格式传递数据
     */
    @PostMapping("ObjectParameter")
    public String getObjectParameter(@RequestBody Product product) {
        log.info("Product对象：{}", product);
        return "提供getObjectParameter商品服务的端口为" + port;
    }

    /**
     * 定义接口接收数组类型参数
     * @param ids id数组
     * @return String
     */
    @GetMapping("ArrayParameter")
    public String getArrayParameter(String[] ids) {
        for (String id : ids) {
            log.info("id: {}", id);
        }
        return "提供getArrayParameter商品服务的端口为" + port;
    }

    /**
     * 定义一个接口接收集合类型参数
     */
    @GetMapping("ListParameter")
    public String getListParameter(CollectionValueObject collectionValueObject) {
        collectionValueObject.getIds().forEach(id -> log.info("id：{}", id));
        return "提供getListParameter商品服务的端口为" + port;
    }

    /**
     * 定义一个接口，接收id类型参数，并返回一个基于id查询到的对象
     * 使用路径传递方式
     * @param id id
     * @return String
     */
    @GetMapping("ReturnProduct/{id}")
    public Product returnProduct(@PathVariable("id") Integer id) {
        log.info("id：{}", id);
        return new Product(1, "祖峰", 16000.0, new Date());
    }

    /**
     * 定义一个接口，接收categoryId类型参数，并返回一个基于categoryId查询到的List对象
     * @param categoryId categoryId
     * @return String
     */
    @GetMapping("CategoryProductMap")
    public Map<String, Object> findByCategoryIdAndPage(Integer page, Integer rows, Integer categoryId) {
        log.info("当前页：{}；每页显示记录数：{}；当前类别id：{} ", page, rows, categoryId);
        /*
         * 根据类别id分页查询符合当前页集合数据  List<Product>
         *      select * from t_product where categoryId=? limt ?(page-1)*rows, ?(rows)
         * 根据类别id查询当前类别下总条数       totalCount
         *      select count(id) from t_product where categoryId=?
         */
        Map<String, Object> map = new HashMap<>();
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "chen", 16000.0, new Date()));
        products.add(new Product(2, "zufeng", 20000.0, new Date()));
        long totalCount = 1000;
        map.put("rows", products);
        map.put("total", totalCount);
        return map;
    }
}
