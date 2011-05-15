package org.builgen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.floreysoft.jmte.Engine;
import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.Filer;
import com.sun.mirror.declaration.AnnotationMirror;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.ParameterDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.type.TypeMirror;
import com.sun.mirror.util.DeclarationVisitors;
import com.sun.mirror.util.SimpleDeclarationVisitor;

public class Processor implements AnnotationProcessor{
	public Processor(
			AnnotationProcessorEnvironment env
			, Engine templateEngine) {
		this.env = env;
		this.templateEngine = templateEngine;
		InputStream is = getClass().getResourceAsStream("Builder.java.template");
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuilder b = new StringBuilder();
			String line = null;
			while((line = br.readLine()) != null){
				b.append(line).append("\r\n");
			}
			template = b.toString();
		} catch(IOException e){
			env.getMessager().printError(e.toString());
		} finally{
			try{
				is.close();
			} catch(IOException e){
				env.getMessager().printError(e.toString());
			} 
		}
    }

	public void process() {
		if(template == null) return;
		for (TypeDeclaration typeDecl : env.getSpecifiedTypeDeclarations()){
			typeDecl.accept(DeclarationVisitors.getDeclarationScanner(
					new ClassVisitor(), DeclarationVisitors.NO_OP
					));
		}
	}

	private class ClassVisitor extends SimpleDeclarationVisitor {
		public void visitClassDeclaration(ClassDeclaration d) {
			for (AnnotationMirror mirror : d.getAnnotationMirrors()) {
				AnnotationTypeDeclaration annotationTypeDeclaration =
					mirror.getAnnotationType().getDeclaration();
				if (!annotationTypeDeclaration.getQualifiedName().equals(
						"buildergenerator.GenerateBuilder")) continue;
				String packageName = d.getPackage().getQualifiedName() + ".builder";
				binding.put("packageName", packageName);
				String className = d.getSimpleName() + "Builder";
				binding.put("className", className);
				binding.put("beanClassName", d.getQualifiedName());
				List<Map<String, Object>> props = new ArrayList<Map<String,Object>>();
				for(MethodDeclaration md : d.getMethods()){
					final Map<String, Object> prop = new HashMap<String, Object>();
					String methodName = md.getSimpleName();
					prop.put("setterName", methodName);
					if(!methodName.startsWith("set")) continue;
					String propName = methodName.substring(3, 4).toLowerCase()
							+ methodName.substring(4);
					prop.put("name", propName);
					Collection<ParameterDeclaration> params = md.getParameters();
					if(params.size() != 1) continue;
					params.iterator().next().getType().accept(new TypeVisitorAdapter() {
						@Override
						protected void defaultVisitor(TypeMirror mirror) {
							prop.put("type", mirror.toString());
						}
					});
					props.add(prop);
				}
				binding.put("properties", props);
				Filer f = env.getFiler();
				try{
					PrintWriter w = f.createSourceFile(packageName + "." + className);
					w.write(templateEngine.transform(template, binding));
					w.close();
				} catch(IOException e){
					env.getMessager().printError(e.toString());
				}

				return;
			}
		}

		private Map<String, Object> binding = new HashMap<String, Object>();
	}

	private AnnotationProcessorEnvironment env;
	private Engine templateEngine;
	private String template;
}
