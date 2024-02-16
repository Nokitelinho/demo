/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WildcardType;

/**
 * @author A-1759
 *
 */
public class ASTNodeBuilder {
	
	public static Map<MethodInvocation, MethodInvocation> oldToNewMap = new HashMap<>();

	public static ASTNode getNewNode(AST ast, ASTNode astNode) {
		return getNewNode(ast, astNode,false);
	}
	public static ASTNode getNewNode(AST ast, ASTNode astNode,boolean neo) {
		ASTNode newNode = null;
		if (astNode instanceof SimpleName) {
			newNode = ast.newSimpleName(((SimpleName) astNode).toString());
		} else if (astNode instanceof MethodInvocation) {
			MethodInvocation old = (MethodInvocation) astNode;
			MethodInvocation neu = ast.newMethodInvocation();
			neu.setName(ast.newSimpleName(old.getName().toString()));
			if (Objects.nonNull(old.getExpression())) {
				neu.setExpression((Expression) getNewNode(ast, old.getExpression(),neo));
			}
			if (!old.arguments().isEmpty()) {
				List<ASTNode> arguments = old.arguments();
				for (ASTNode node : arguments) {
					neu.arguments().add(getNewNode(ast, node,neo));
				}
			}
			newNode = neu;
			oldToNewMap.put(old,neu);
		} else if (astNode instanceof NumberLiteral) {
			String doubleValue = ((NumberLiteral) astNode).getToken();
			newNode = ast.newNumberLiteral(doubleValue);

		} else if (astNode instanceof StringLiteral) {
			String stringValue = ((StringLiteral) astNode).getLiteralValue();
			StringLiteral neu = ast.newStringLiteral();
			neu.setLiteralValue(stringValue);
			newNode = neu;
		} else if (astNode instanceof ArrayAccess) {
			ArrayAccess arrayAccess = (ArrayAccess) astNode;
			ArrayAccess neu = ast.newArrayAccess();
			neu.setArray((Expression) getNewNode(ast, arrayAccess.getArray(),neo));
			neu.setIndex((Expression) getNewNode(ast, arrayAccess.getIndex(),neo));
			newNode = neu;
		} else if (astNode instanceof SimpleType) {
			if (!neo && UtilityFrameworkSupport.getUtilityclassmap().containsKey(astNode.toString())) {
				Class neoClazz = UtilityFrameworkSupport.getUtilityclassmap().get(astNode.toString()).getNeoClazz();
				newNode = ast.newSimpleType(ast.newSimpleName(neoClazz.getSimpleName()));
			} else {
				newNode = ast.newSimpleType((Name) getNewNode(ast, ((SimpleType) astNode).getName(),neo));
			}
		} else if (astNode instanceof ParameterizedType) {
			ParameterizedType pType = (ParameterizedType) astNode;
			newNode = ast.newParameterizedType((Type) getNewNode(ast, pType.getType(),neo));
			List<Type> typeArgs = pType.typeArguments();
			for (Type arg : typeArgs) {
				((ParameterizedType) newNode).typeArguments().add(getNewNode(ast, arg,neo));
			}
		} else if (astNode instanceof BooleanLiteral) {
			newNode = ast.newBooleanLiteral(((BooleanLiteral) astNode).booleanValue());
		} else if (astNode instanceof ClassInstanceCreation) {
			ClassInstanceCreation clCreation = (ClassInstanceCreation) astNode;
			ClassInstanceCreation neu = ast.newClassInstanceCreation();
			neu.setType((Type) getNewNode(ast, clCreation.getType(),neo));
			List<ASTNode> arguements = clCreation.arguments();
			for (ASTNode arg : arguements) {
				neu.arguments().add(getNewNode(ast, arg,neo));
			}
			newNode = neu;
		} else if (astNode instanceof FieldAccess) {
			FieldAccess fieldAccess = (FieldAccess) astNode;
			FieldAccess neu = ast.newFieldAccess();
			neu.setExpression((Expression) getNewNode(ast, fieldAccess.getExpression(),neo));
			neu.setName(ast.newSimpleName(fieldAccess.getName().toString()));
			newNode = neu;
		} else if (astNode instanceof ThisExpression) {
			ThisExpression thisExpression = (ThisExpression) astNode;
			newNode = ast.newThisExpression();
			if (Objects.nonNull(thisExpression.getQualifier())) {
				((ThisExpression) newNode).setQualifier(ast.newSimpleName(thisExpression.getQualifier().toString()));
			}
		} else if (astNode instanceof QualifiedName) {
			newNode = ast.newName(astNode.toString());
		} else if (astNode instanceof WildcardType) {
			WildcardType wildcardType = (WildcardType) astNode;
			WildcardType neu = ast.newWildcardType();
			neu.setBound((Type) getNewNode(ast, wildcardType.getBound(),neo), wildcardType.isUpperBound());
			newNode = neu;
		} else if (astNode instanceof PrimitiveType) {
			newNode = ast.newPrimitiveType(((PrimitiveType) astNode).getPrimitiveTypeCode());
		} else if (astNode instanceof ArrayType) {
			ArrayType arrayType = (ArrayType) astNode;
			newNode = ast.newArrayType((Type) getNewNode(ast, arrayType.getElementType(),neo));
		}else if (astNode instanceof NullLiteral) {
			newNode = ast.newNullLiteral();
		}else if (astNode instanceof TypeLiteral) {
			TypeLiteral typeLiteral = ast.newTypeLiteral();
			typeLiteral.setType((Type)getNewNode(ast, (((TypeLiteral)astNode).getType())));
			newNode = typeLiteral;
		}else if(astNode instanceof InfixExpression) {
			InfixExpression oldInfixExpression = (InfixExpression) astNode;
			InfixExpression newInfixExpression = ast.newInfixExpression();
			newInfixExpression.setLeftOperand((Expression)getNewNode(ast, oldInfixExpression.getLeftOperand()));
			newInfixExpression.setOperator(oldInfixExpression.getOperator());
			newInfixExpression.setRightOperand((Expression)getNewNode(ast, oldInfixExpression.getRightOperand()));
			newNode = newInfixExpression;
		}else if(astNode instanceof ParenthesizedExpression){
			ParenthesizedExpression oldParenthesizedExpression = (ParenthesizedExpression)astNode;
			ParenthesizedExpression newParenthesizedExpression = ast.newParenthesizedExpression();
			newParenthesizedExpression.setExpression((Expression)getNewNode(ast, oldParenthesizedExpression.getExpression()));
			newNode = newParenthesizedExpression;
		}else if(astNode instanceof ConditionalExpression) {
			ConditionalExpression oldConditionalExpression = (ConditionalExpression)astNode;
			ConditionalExpression newConditionalExpression = ast.newConditionalExpression();
			newConditionalExpression.setExpression((Expression)getNewNode(ast, oldConditionalExpression.getExpression()));
			newConditionalExpression.setThenExpression((Expression)getNewNode(ast, oldConditionalExpression.getThenExpression()));
			newConditionalExpression.setElseExpression((Expression)getNewNode(ast, oldConditionalExpression.getElseExpression()));
			newNode = newConditionalExpression;
		}else if(astNode instanceof ArrayCreation) {
			ArrayCreation oldArrayCreation = (ArrayCreation)astNode;
			ArrayCreation newArrayCreation = ast.newArrayCreation();
			newArrayCreation.setType((ArrayType)getNewNode(ast, oldArrayCreation.getType()));
			newArrayCreation.setInitializer((ArrayInitializer)getNewNode(ast,oldArrayCreation.getInitializer()));
			newNode = newArrayCreation;
		}else if(astNode instanceof ArrayInitializer) {
			ArrayInitializer oldArrayInitializer = (ArrayInitializer)astNode;
			ArrayInitializer newArrayInitializer = ast.newArrayInitializer();
			for(Object exp : oldArrayInitializer.expressions()) {
				newArrayInitializer.expressions().add(getNewNode(ast, (Expression)exp));
			}
			newNode = newArrayInitializer;
		}else if(astNode instanceof CastExpression) {
			CastExpression oldCastExpression = (CastExpression)astNode;
			CastExpression newCastExpression = ast.newCastExpression();
			newCastExpression.setType((Type)ASTNodeBuilder.getNewNode(ast, oldCastExpression.getType()));
			newCastExpression.setExpression((Expression)ASTNodeBuilder.getNewNode(ast, oldCastExpression.getExpression()));
			
			newNode = newCastExpression;
		}
		return newNode;

	}

	public static String getTypeName(ASTNode type) {
		String name = null;
		if (type instanceof SimpleType) {
			name = getTypeName(((SimpleType) type).getName());
		} else if (type instanceof SimpleName) {
			name = type.toString();
		} else if (type instanceof QualifiedName) {
			name = getTypeName(((QualifiedName) type).getName());
		} else if(type instanceof ParameterizedType) {
			name = getTypeName(((ParameterizedType)type).getType());
		} else {
			name=type.toString();
		}
		return name;
	}
	
	public static Type getNewType(AST ast, Type oldType, String oldReg, String newReg, List<String> excludeList) {
		if (oldType instanceof SimpleType) {
			String typeName = ((SimpleType)oldType).getName().toString();
			if(!excludeList.contains(typeName)) {
				typeName = typeName.replace(oldReg, newReg);
			}
			return ast.newSimpleType(ast.newSimpleName(typeName));
		}else if (oldType instanceof ParameterizedType) {
			ParameterizedType pType = ast.newParameterizedType((Type)getNewNode(ast,((ParameterizedType)oldType).getType()));
			List<Type> typeArgs = ((ParameterizedType)oldType).typeArguments();
			for (Type arg : typeArgs) {
				((ParameterizedType) pType).typeArguments().add(getNewType(ast, arg, oldReg, newReg,excludeList));
			}
			return pType;
		}
		return (Type)getNewNode(ast, oldType);
		
	}

	public static ASTNode getParentNode(ASTNode currentNode, Class... validNodes) {
		if(Objects.isNull(currentNode)) {
			return null;
		}
		ASTNode parentNode = currentNode.getParent();
		boolean isMatch = Stream.of(validNodes).anyMatch(node -> node.isInstance(parentNode));
		return isMatch ? parentNode : getParentNode(parentNode, validNodes);
	}
	
	public static <T> T getParentNode(ASTNode currentNode, Class<T> validNode){
		return (T)getParentNode(currentNode, new Class[]{validNode});
	}
	
	public static void setNewType(ASTNode type, Type newType){
		ASTNode parentNode = type.getParent();
		if(parentNode instanceof VariableDeclarationStatement){
			((VariableDeclarationStatement)parentNode).setType(newType);
		}else if(parentNode instanceof SingleVariableDeclaration){
			((SingleVariableDeclaration)parentNode).setType(newType);
		}else if(parentNode instanceof CastExpression){
			((CastExpression)parentNode).setType(newType);
		}else if(parentNode instanceof UnionType) {
			((UnionType)parentNode).types().remove(type);
			((UnionType)parentNode).types().add(newType);
		}
	}
}
