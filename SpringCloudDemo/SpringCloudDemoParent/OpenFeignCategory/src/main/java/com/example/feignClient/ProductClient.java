package com.example.feignClient;

import com.example.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author chenzufeng
 * ProductClient：调用商品服务的接口
 *                         value属性用来指定调用服务id
 */
@FeignClient("OpenFeignProduct")
public interface ProductClient {
    /**
     * 与ProductController.java中方法名一致
     * @return String
     */
    @GetMapping("Product")
    String getProduct();

    /**
     * 声明调用服务中的接口，传递name, age
     * /ParameterQueryString?name=xx&age=xx（QueryString方式）
     * @param name name
     * @param age age
     * @return String
     */
    @GetMapping("ParameterQueryString")
    String getParameterQueryString(@RequestParam("name") String name, @RequestParam("age") Integer age);

    /**
     * 声明调用服务中的接口，传递name, age
     * /Parameter/{name}/{age}（路径传参）
     * @param name name
     * @param age age
     * @return String
     */
    @GetMapping("Parameter/{name}/{age}")
    String getParameter(@PathVariable("name") String name, @PathVariable("age") Integer age);

    /**
     * 声明调用商品服务中getObjectParameter接口，传递Product对象
     * @return String
     * RequestBody以Json格式传递
     */
    @PostMapping("ObjectParameter")
    String getObjectParameter(@RequestBody Product product);

    /**
     * 声明声明调用商品服务中`getArrayParameter`接口，传递数组类型参数
     * getListParameter?ids=1&ids=2
     * @param ids id数组
     * @return String
     */
    @GetMapping("ArrayParameter")
    String getArrayParameter(@RequestParam("ids") String[] ids);

    /**
     * 声明调用商品服务中getListParameter接口，传递一个List集合类型参数
     * ListParameter?ids=1&ids=2
     * @param ids ids
     * @return String
     */
    @GetMapping("ListParameter")
    String getListParameter(@RequestParam("ids") String[] ids);

    /**
     * 声明调用根据id查询商品信息接口
     * @param id id
     * @return String
     */
    @GetMapping("ReturnProduct/{id}")
    Product returnProduct(@PathVariable("id") Integer id);

    /**
     * 声明调用商品服务根据categoryId查询一组商品信息并返回
     * @param categoryId categoryId
     * @return List<Product>
     */
    @GetMapping("CategoryProduct")
    List<Product> findByCategoryId(@RequestParam("categoryId") Integer categoryId);

    /**
     * 声明调用商品服务根据类别id查询分页查询商品信息以及总条数
     * @param page page
     * @param rows rows
     * @param categoryId categoryId
     * @return Map<String, Object>
     */
    @GetMapping("CategoryProductMap")
    String findByCategoryIdAndPage(
            @RequestParam("page") Integer page, @RequestParam("rows") Integer rows, @RequestParam("categoryId") Integer categoryId);
}
