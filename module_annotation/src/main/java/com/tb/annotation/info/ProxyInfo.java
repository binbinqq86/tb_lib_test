package com.tb.annotation.info;

import com.tb.annotation.annotation.NoProguard;
import com.tb.annotation.api.InjectBaseUrl;

import java.io.Serializable;

import javax.lang.model.element.TypeElement;

/**
 * @auther tb
 * @time 2017/12/15 下午2:03
 * @desc 对应需要生成某个类的全部相关信息
 */
public class ProxyInfo{
    /**
     * 类
     */
    public TypeElement typeElement;
    /**
     * 某个变量的值
     */
    public Object value;
    public String packageName;
    
    /**
     * 采用此种方式InjectBaseUrl不能被混淆，或者采用字符串方式
     */
    public static final String PROXY = InjectBaseUrl.class.getSimpleName();
    public static final String ClassSuffix = "_" + PROXY;
    
    public String getProxyClassFullName() {
        return typeElement.getQualifiedName().toString() + ClassSuffix;
    }
    
    public String getClassName() {
        return typeElement.getSimpleName().toString() + ClassSuffix;
    }
    
    public String generateJavaCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("//自动生成的注解类，勿动!!!\n");
        builder.append("package ").append(packageName).append(";\n\n");
        builder.append("import com.tb.baselib.constant.BaseConstant;\n");
        builder.append("import com.tb.annotation.api.InjectBaseUrl;\n");
        builder.append("import com.tb.annotation.annotation.NoProguard;\n");
        builder.append('\n');
        
        builder.append("@NoProguard").append("\n");//禁止混淆，否则反射的时候找不到该类
        builder.append("public class ").append(getClassName()).append(" implements " + ProxyInfo.PROXY + "<" + typeElement.getQualifiedName() + ">");
        builder.append(" {\n");
        
        generateMethod(builder);
        
        builder.append('\n');
        
        builder.append("}\n");
        return builder.toString();
    }
    
    private void generateMethod(StringBuilder builder) {
        builder.append("@Override\n ");
        builder.append("public void inject(" + typeElement.getQualifiedName() + " host, Object source ) {\n");
        
        builder.append("BaseConstant.BASE_API_URL=\"").append(value).append("\";\n");
        
        builder.append("  }\n");
    }
    
}
