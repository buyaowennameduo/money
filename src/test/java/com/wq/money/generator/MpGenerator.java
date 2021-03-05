package com.wq.money.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.*;

public class MpGenerator {
    //前端首页生成地址
    private static final String DISK_JAVA = "D:\\ideaProjects";
    private static final String PARNENT = "com.wq.money";
    private static final String MODULE_NAME = "framework";
    private static final String PARNENT_PATH = "com/wq/money";
    //后端控制器以及服务生成地址/table
    private static final String AUTHOR = "王强";
    private static final String[] include = {
            "test"

    };
    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        final AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        final GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(DISK_JAVA);
        gc.setFileOverride(true);
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setSwagger2(true);
        //gc.setKotlin(true);//是否生成 kotlin 代码
        gc.setAuthor(AUTHOR);
        gc.setIdType(IdType.UUID);

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                String lowerCase = fieldType.toLowerCase();
                if (lowerCase.equals("date") || lowerCase.equals("datetime") || lowerCase.equals("timestamp")) {
                    return DbColumnType.DATE;
                }
                return super.processTypeConvert(gc, fieldType);
            }
        });
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setUrl("jdbc:mysql://127.0.0.1/money?serverTimezone=Asia/Shanghai");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        strategy.setTablePrefix(new String[]{"tbl", "kpi", "examples"});// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(include); // 需要生成的表
        strategy.setEntityColumnConstant(false);
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setLogicDeleteFieldName("DEL_FLAG");
        strategy.setTableFillList(getTableFills());

        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(PARNENT);
        pc.setModuleName(MODULE_NAME);
        pc.setController("controller");
        pc.setEntity("bean.po");
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】  ${cfg.abc}
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("query", PARNENT + "." + this.getConfig().getPackageInfo().get("ModuleName") + ".bean.query");
                map.put("vo", PARNENT + "." + this.getConfig().getPackageInfo().get("ModuleName") + ".bean.vo");
                map.put("form", PARNENT + "." + this.getConfig().getPackageInfo().get("ModuleName") + ".bean.form");
                map.put("dto", PARNENT + "." + this.getConfig().getPackageInfo().get("ModuleName") + ".bean.dto");
                map.put("convert", PARNENT + "." + this.getConfig().getPackageInfo().get("ModuleName") + ".bean.convert");
                this.setMap(map);
            }
        };

        // 自定义 xxListIndex.html 生成
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();






        //  自定义 Vo.java生成
        focList.add(new FileOutConfig("/templates/mybatisplus/vo.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return DISK_JAVA + "/" + PARNENT_PATH + "/" + MODULE_NAME + "/bean/vo/" + tableInfo.getEntityName() + "Vo.java";
            }
        });
        //  自定义 Form.java生成
        focList.add(new FileOutConfig("/templates/mybatisplus/form.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return DISK_JAVA + "/" + PARNENT_PATH + "/" + MODULE_NAME + "/bean/form/" + tableInfo.getEntityName() + "Form.java";
            }
        });
        //  自定义 Query.java生成
        focList.add(new FileOutConfig("/templates/mybatisplus/query.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return DISK_JAVA + "/" + PARNENT_PATH + "/" + MODULE_NAME + "/bean/query/" + tableInfo.getEntityName() + "Query.java";
            }
        });
        //  自定义 Convert.java生成
        focList.add(new FileOutConfig("/templates/mybatisplus/convert.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return DISK_JAVA + "/" + PARNENT_PATH + "/" + MODULE_NAME + "/bean/convert/" + tableInfo.getEntityName() + "Convert.java";
            }
        });
        //  自定义 Import.java生成
        focList.add(new FileOutConfig("/templates/mybatisplus/import.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return DISK_JAVA + "/" + PARNENT_PATH + "/" + MODULE_NAME + "/bean/dto/" + tableInfo.getEntityName() + "Import.java";
            }
        });
        //  自定义 Export.java生成
        focList.add(new FileOutConfig("/templates/mybatisplus/export.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return DISK_JAVA + "/" + PARNENT_PATH + "/" + MODULE_NAME + "/bean/dto/" + tableInfo.getEntityName() + "Export.java";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 关闭默认 xml 生成，调整生成 至 根目录
        /*TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        mpg.setTemplate(tc);*/

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        TemplateConfig tc = new TemplateConfig();
        tc.setController("/templates/mybatisplus/controller.java.vm");
        tc.setService("/templates/mybatisplus/service.java.vm");
        tc.setServiceImpl("/templates/mybatisplus/serviceImpl.java.vm");
        tc.setEntity("/templates/mybatisplus/entity.java.vm");
        tc.setMapper("/templates/mybatisplus/mapper.java.vm");
        tc.setXml("/templates/mybatisplus/mapper.xml.vm");


        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

        // 打印注入设置【可无】
        System.err.println(mpg.getCfg().getMap().get("abc"));
    }

    public static List<TableFill> getTableFills() {
        TableFill cuserid = new TableFill("cuserid", FieldFill.INSERT);
        TableFill cusername = new TableFill("cusername", FieldFill.INSERT);
        TableFill cunitid = new TableFill("cunitid", FieldFill.INSERT);
        TableFill cpostid = new TableFill("cpostid", FieldFill.INSERT);
        TableFill ctime = new TableFill("ctime", FieldFill.INSERT);
        TableFill muserid = new TableFill("muserid", FieldFill.UPDATE);
        TableFill musername = new TableFill("musername", FieldFill.UPDATE);
        TableFill mtime = new TableFill("mtime", FieldFill.UPDATE);
        return Arrays.asList(cuserid, cusername, cunitid, cpostid, ctime, muserid, musername, mtime);
    }

}
