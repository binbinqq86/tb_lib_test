package com.tb.annotation.info;

import com.tb.annotation.api.InjectBaseUrl;

import javax.lang.model.element.TypeElement;

/**
 * @auther tb
 * @time 2017/12/15 下午2:03
 * @desc 对应需要生成某个类的全部相关信息
 */
public class ProxyInfo {
    /**
     * 类
     */
    public TypeElement typeElement;
    /**
     * 某个变量的值
     */
    public Object value;
    public String packageName;
    
    public static final String PROXY = InjectBaseUrl.class.getSimpleName();
    
    public String getProxyClassFullName() {
        return typeElement.getQualifiedName().toString() + "_" + PROXY;
    }
    
    public String getClassName() {
        return typeElement.getSimpleName().toString() + "_" + PROXY;
    }
    
    public String generateJavaCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("// Generated code. Do not modify!\n");
        builder.append("package ").append(packageName).append(";\n\n");
        builder.append("import com.tb.baselib.constant.BaseConstant;\n");
        builder.append("import com.tb.annotation.api.InjectBaseUrl;\n");
        builder.append('\n');
        
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
