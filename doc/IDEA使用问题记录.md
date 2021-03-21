

### 热加载部署Class文件

```xml
<!-- 热部署（必须） -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <!-- 表示依赖不会传递 -->
        <optional>true</optional>
    </dependency>

//下面的可以不要，上面可以实现热部署的话
<!-- 热部署（非必需） -->
    <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
            <!-- fork 如果没有该项配置 devtools 不会起作用，即应用不会restart -->
            <fork>true</fork>
            <!-- 支持静态文件热部署 -->
            <addResources>true</addResources>
        </configuration>
    </plugin>
```

#### 修改idea设置

- Compiler   Build project automatically
- **ctrl+shift+a** 搜索 Registry
- 找到compiler.automake.allow.when.app.running，勾上开启此功能即可
- **Build,Execution,Deployment-->Debugger-->HotSwap--> Reload classes after compilation** 

