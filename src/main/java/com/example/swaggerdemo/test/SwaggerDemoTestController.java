/**
 * Copyright (C), 2015-2020, 中信银行有限公司
 * FileName: SwaggerDemoTest
 * Author:   willem
 * Date:     2020/12/22 3:05 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.swaggerdemo.test;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author willem
 * @create 2020/12/22
 * @since 1.0.0
 */
@RequestMapping
@RestController
public class SwaggerDemoTestController {

    @ApiOperation(value = "获取新的订单信息")
//    @RequestMapping(value = "/getOrder",method = RequestMethod.GET)
    @GetMapping(value = "order")
    public String getOrder(@ApiParam(value = "订单编号",required = true) @RequestParam(value = "orderNo", required=false) String orderNo,
                           @ApiParam(value = "当前页") @RequestParam(value = "pageNum",required = false) Integer pageNum,
                           @ApiParam(value = "每页显示数量") @RequestParam(value = "pageSize",required = false) Integer pageSize){
        return "请求测试成功";
    }
}
