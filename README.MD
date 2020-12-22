## springboot2.0.6实现集成swagger2.9.2，良心版最新版，看了复制就能用。
##### 1、pom依赖：
	<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>${springfox-swagger2.version}</version>
        </dependency>

        <!--这个很坑我 忘了引依赖-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>${springfox-swagger-ui.version}</version>
        </dependency>

        <dependency>
            <groupId>com.github.xiaoymin</groupId>
            <artifactId>swagger-bootstrap-ui</artifactId>
            <version>${swagger-bootstrap-ui.version}</version>
        </dependency>

#### 2、配置文件 ，一般新建模块后，直接new---》page包config ，将这个类放入，改改包名，其他的随自己，不会影响。

```java
package com.citic.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: Jointtest
 * @description: 注入swagger资源文件
 * @author: Wang XinWen
 * @create: 2018-10-30 19:54
 **/


@Configuration
@EnableSwagger2
public class JointtestSwaggerConfig implements WebMvcConfigurer {
    /**
     *
     * 显示swagger-ui.html文档展示页，还必须注入swagger资源：
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    //可以注入多个doket，也就是多个版本的api，可以在看到有三个版本groupName不能是重复的，v1和v2是ant风格匹配，配置文件
    @Bean
    public Docket api() {
        //可以添加多个header或参数
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder
                .parameterType("header") //参数类型支持header, cookie, body, query etc
                .name("token") //参数名
                .defaultValue("token") //默认值
                .description("header中token字段测试")
                .modelRef(new ModelRef("string"))//指定参数值的类型
                .required(false).build(); //非必需，这里是全局配置，然而在登陆的时候是不用验证的
        List<Parameter> aParameters = new ArrayList<Parameter>();
        aParameters.add(aParameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2).groupName("v1").select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/v1/**")).build().apiInfo(apiInfo1()).globalOperationParameters(aParameters);
    }


    private ApiInfo apiInfo1() {
        return new ApiInfoBuilder()
                .title("exampleApi 0.01")
                .termsOfServiceUrl("www.example.com")
                .contact(new Contact("liumei","http://blog.csdn.net/pc_gad","hilin2333@gmail.com"))
                .version("v0.01")
                .build();
    }
}


```
#### 3、测试

```java

package com.citic.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * @program: Jointtest
 * @description:
 * @author: Wang XinWen
 * @create: 2018-11-02 15:36
 **/
@RequestMapping
@RestController
public class Controller {

    @ApiOperation(value = "获取新的订单信息")
//    @RequestMapping(value = "/getOrder",method = RequestMethod.GET)
    @GetMapping(value = "order")
    public String getOrder(@ApiParam(value = "订单编号",required = true) @RequestParam(value = "orderNo", required=false) String orderNo,
                           @ApiParam(value = "当前页") @RequestParam(value = "pageNum",required = false) Integer pageNum,
                           @ApiParam(value = "每页显示数量") @RequestParam(value = "pageSize",required = false) Integer pageSize){
        return "请求测试成功";
    }

//    @GetMapping(value = "/order")
//    public String sayHello(){
//        return "Hello ";
//    }

}
```
### *注意!!!!!!!!!!!!!!!!!注意！！！！！！*

访问地址是：
	http://localhost:8080/doc.html
	主机名+端口号+doc.html

![-w1427](https://sumulige-1257145452.cos.ap-beijing.myqcloud.com/MWeb/2020/12/22/16086213749534.jpg)

**[看到很多人有问题，博主亲自手写代码并上传github，感兴趣的同学自行下载](https://github.com/sumulige/swaggerdemo.git)**
--	 有什么问题可以联系本人