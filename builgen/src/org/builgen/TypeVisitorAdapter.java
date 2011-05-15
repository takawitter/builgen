package org.builgen;

import com.sun.mirror.type.AnnotationType;
import com.sun.mirror.type.ArrayType;
import com.sun.mirror.type.ClassType;
import com.sun.mirror.type.DeclaredType;
import com.sun.mirror.type.EnumType;
import com.sun.mirror.type.InterfaceType;
import com.sun.mirror.type.PrimitiveType;
import com.sun.mirror.type.ReferenceType;
import com.sun.mirror.type.TypeMirror;
import com.sun.mirror.type.TypeVariable;
import com.sun.mirror.type.VoidType;
import com.sun.mirror.type.WildcardType;
import com.sun.mirror.util.TypeVisitor;

public class TypeVisitorAdapter implements TypeVisitor{
	@Override
	public void visitAnnotationType(AnnotationType arg0) {
		defaultVisitor(arg0);
	}

	@Override
	public void visitArrayType(ArrayType arg0) {
		defaultVisitor(arg0);
	}

	@Override
	public void visitClassType(ClassType arg0) {
		defaultVisitor(arg0);
	}

	@Override
	public void visitDeclaredType(DeclaredType arg0) {
		defaultVisitor(arg0);
	}

	@Override
	public void visitEnumType(EnumType arg0) {
		defaultVisitor(arg0);
	}

	@Override
	public void visitInterfaceType(InterfaceType arg0) {
		defaultVisitor(arg0);
	}

	@Override
	public void visitPrimitiveType(PrimitiveType arg0) {
		defaultVisitor(arg0);
	}

	@Override
	public void visitReferenceType(ReferenceType arg0) {
		defaultVisitor(arg0);
	}

	@Override
	public void visitTypeMirror(TypeMirror arg0) {
		defaultVisitor(arg0);
	}

	@Override
	public void visitTypeVariable(TypeVariable arg0) {
		defaultVisitor(arg0);
	}

	@Override
	public void visitVoidType(VoidType arg0) {
		defaultVisitor(arg0);
	}

	@Override
	public void visitWildcardType(WildcardType arg0) {
		defaultVisitor(arg0);
	}

	protected void defaultVisitor(TypeMirror mirror){
	}
}
