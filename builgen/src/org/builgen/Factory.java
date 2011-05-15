package org.builgen;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.floreysoft.jmte.Engine;
import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;

public class Factory implements AnnotationProcessorFactory{
	public AnnotationProcessor getProcessorFor(
			Set<AnnotationTypeDeclaration> arg0,
			AnnotationProcessorEnvironment arg1) {
		return new Processor(arg1, templateEngine);
	}

	public Collection<String> supportedAnnotationTypes() {
		return supportedAnnotations;
	}

	public Collection<String> supportedOptions() {
		return supportedOptions;
	}

	private static final Engine templateEngine = new Engine();
	private static final Collection<String> supportedAnnotations
		= Collections.unmodifiableCollection(Arrays.asList("*"));
	private static final Collection<String> supportedOptions = Collections.emptySet();
}
