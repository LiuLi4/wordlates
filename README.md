# wordlates
基于模板的文档自动生成系统

## 快速开始
- 打开idea菜单栏VCS --> Get from Version Control -->选择Git,URL输入 https://github.com/LiuLi4/wordlates.git
- upload文件夹下的**2018年XX食品年度分析报告**是原始Word文档用于阅读，**2018年XX食品年度分析报告（模板）**已经添加了标记语言用于开发

## 计算结果生成开发安排
1. 新建分支进行开发
2. 在DATAUtil类下进行数值计算
3. 数据通过FILEUtil下的readCSV方法以Map形式返回
    
    Map<String, List<String>> dataMap = FILEUtil.readCSV(Constants.FILE_CSV_PATH);
     
4. 需要新的数据在resource文件夹下的csv.properties中配置

    
    抽样地点_2*=6
    省(市、自治区)*=10
    地区(市、州、盟)=11
    县(市、区)=12
    区域类型*=13

列名字=列号

## 配置文件说明
1. 将不变的配置项放在application.yml中，application-dev文件下放本地开发环境配置，application-prod文件下放线上环境配置。
2. 部署到线上服务器时，用线上环境打包 
``` bash
mvn clean package -P prod
```
3. 参考文章：https://juejin.cn/post/6856965881378029582
