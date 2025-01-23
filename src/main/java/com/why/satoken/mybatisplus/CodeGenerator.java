package com.why.satoken.mybatisplus;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

public class CodeGenerator {



    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/sa_token_demo"
                        , "root", "Mysql123")
                .globalConfig(builder -> {
                    builder.author("muyu") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir("/Users/muyu/IdeaProjects/sa-token"); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent("com.why.satoken") // 设置父包名
                                .moduleName("auth") // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, "/Users/muyu/IdeaProjects/sa-token/mappers")) // 设置mapperXml生成路径
                )
                .strategyConfig(builder ->
                        builder.addInclude("Books")
                                .addInclude("Borrow_Records")
                                .addInclude("Categories")
                                .addInclude("Users")// 设置需要生成的表名
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}
