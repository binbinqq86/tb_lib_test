package com.tb.annotation.info;

import java.util.List;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * @auther tb
 * @time 2017/12/15 下午2:03
 * @desc 对应需要生成某个类的全部相关信息
 */
public class ProxyInfo {
    /**
     * 一个类的全部变量信息
     */
    public List<ClassInfo> list;
    
    public static class ClassInfo {
        /**
         * 某个变量的类型和名称
         */
        public VariableElement variableElement;
        /**
         * 某个变量的值
         */
        public Object value;
    }
    
    private String packageName;
    private String proxyClassName;
    private TypeElement typeElement;
    
    public static final String PROXY = "InjectBaseUrl";
    
    public ProxyInfo(Elements elementUtils, TypeElement classElement) {
        this.typeElement = classElement;
        String packageName = elementUtils.getPackageOf(classElement).getQualifiedName().toString();
        //classname
        String className = classElement.getQualifiedName().toString().substring(packageName.length() + 1).replace('.', '$');
        this.packageName = packageName;
        this.proxyClassName = className + "$$" + PROXY;
    }
    
    public String getProxyClassFullName() {
        return packageName + "." + proxyClassName;
    }
    
    public TypeElement getTypeElement() {
        return typeElement;
    }
    
    public String generateJavaCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("// Generated code. Do not modify!\n");
        builder.append("package ").append(packageName).append(";\n\n");
        builder.append("import com.tb.annotation.*;\n");
        builder.append("import com.tb.baselib.constant.BaseConstant;\n");
        builder.append('\n');
        
        builder.append("public class ").append(proxyClassName);
        builder.append(" {\n");
    
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).variableElement.getSimpleName().equals("BASE_API_URL")){
                //只处理我们目前的情况，其他情况需另行拓展
                builder.append("BaseConstant.BASE_API_URL=").append(list.get(i).value).append(";\n");
            }
        }
        builder.append('\n');
        
        builder.append("}\n");
        return builder.toString();
    }
    
}
