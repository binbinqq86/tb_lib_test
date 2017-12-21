package com.tb.annotation.compiler;

import com.google.auto.service.AutoService;
import com.tb.annotation.annotation.BaseApiUrl;
import com.tb.annotation.info.ProxyInfo;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * @auther tb
 * @time 2017/12/15 上午11:51
 * @desc
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class BaseApiUrlProcesser extends AbstractProcessor {
    private Filer mFileUtils;
    private Elements mElementUtils;
    private Messager messager;
    /**
     * 一个需要生成的类的集合（key为类的全名，value为该类所有相关的需要的信息）
     */
    private Map<String, ProxyInfo> mProxyMap = new HashMap<String, ProxyInfo>();
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<String>();
        annotationTypes.add(BaseApiUrl.class.getCanonicalName());
        return annotationTypes;
    }
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFileUtils = processingEnvironment.getFiler();
        mElementUtils = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        collectionInfo(roundEnvironment);
        generateClass();
        return true;
    }
    
    /**
     * 生成代理类
     */
    private void generateClass() {
        for (String key : mProxyMap.keySet()) {
            ProxyInfo proxyInfo = mProxyMap.get(key);
            JavaFileObject sourceFile = null;
            try {
                sourceFile = mFileUtils.createSourceFile(proxyInfo.getProxyClassFullName(), proxyInfo.typeElement);
                Writer writer = sourceFile.openWriter();
                writer.write(proxyInfo.generateJavaCode());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 收集所需生成类的信息
     *
     * @param roundEnvironment
     */
    private void collectionInfo(RoundEnvironment roundEnvironment) {
        //process可能会多次调用，避免生成重复的代理类
        mProxyMap.clear();
        //获得被该注解声明的元素
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BaseApiUrl.class);
        //收集信息
        for (Element element : elements) {
            if (!element.getKind().isClass()) {
                continue;//只处理类注解
            }
            //获取注解的值
            String baseApiUrl = element.getAnnotation(BaseApiUrl.class).value();
            TypeElement typeElement = (TypeElement) element;
            //类的完整路径
            String qualifiedName = typeElement.getQualifiedName().toString();
            /*类名*/
            String clsName = typeElement.getSimpleName().toString();
            /*获取包名*/
            String packageName = mElementUtils.getPackageOf(typeElement).getQualifiedName().toString();
            
            ProxyInfo proxyInfo = mProxyMap.get(qualifiedName);
            if (proxyInfo == null) {
                proxyInfo = new ProxyInfo();
                mProxyMap.put(qualifiedName, proxyInfo);
            }
            
            proxyInfo.value = baseApiUrl;
            proxyInfo.typeElement = typeElement;
            proxyInfo.packageName = packageName;
        }
    }
}
