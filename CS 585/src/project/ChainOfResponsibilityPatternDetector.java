package project;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.List;

public class ChainOfResponsibilityPatternDetector {

	public static void detect(String path) {

		HashSet<String> cSet = getClassNameSet(path);

		HashSet<Object[]> Y = new HashSet<Object[]>();

		for (String c : cSet) {

			HashSet<String> vSet = getVariableSet(path + c + ".java");

			for (String v : vSet) {

				HashSet<String> uSet = getVariableTypeSet(path + c + ".java");

				for (String u : uSet) {

					if (c.equals(u)) {
						Object[] objs = { c, v, u };
						Y.add(objs);
					} else
						for (String c_ : cSet) {
							System.out.println(cSet);
							if (isInherited(c_, u, path + c_ + ".java")) {
								Object[] objs = { c_, v, u };
								Y.add(objs);
								break;
							}
						}
				}
			}
		}

		HashSet<Object[]> C = new HashSet<Object[]>();

		for (Object[] y : Y) {

			HashSet<MethodDeclaration> mSet = getMethodSet(path + y[2]
					+ ".java");
			System.out.println(y[0]);
			for (MethodDeclaration m : mSet)
				if (isInBody((String) y[1], m, path + y[0] + ".java")) {
					Object[] objs = { y[0], y[1], y[2], m };
					C.add(objs);
				}
		}

		if (C.size() > 0)
			System.out
					.println("Chain of responsibility design pattern has been detected.");
	}

	private static boolean isInBody(String v, MethodDeclaration m, String path) {

		String targetString = v + "." + m.getName() + "(";

		try {

			FileReader fr = new FileReader(path);

			CompilationUnit cu = JavaParser.parse(fr, false);

			if (cu.toString().contains(targetString))
				return true;

		} catch (Exception e) {
		}

		return false;
	}

	private static boolean isInherited(String c, String u, String path) {

		String cExtUString = c + " extends " + u;
		String cImpUString = c + " implements " + u;
		String cExtString = c + " extends ";
		String cImpString = c + " implements ";

		try {

			FileReader fr = new FileReader(path);

			String classBody = JavaParser.parse(fr, false).toString();

			fr.close();

			if (classBody.contains(cExtUString)
					|| classBody.contains(cImpUString)) {
				return true;
			} else if (!classBody.contains(cExtString)
					&& !classBody.contains(cImpString)) {
				// System.out.println(classBody);
				return false;
			} else {

				int i = classBody.indexOf(cExtString) + cExtString.length();

				int j = i;

				while (classBody.charAt(i + 1) == '_'
						|| Character.isLetterOrDigit(classBody.charAt(i + 1)))
					j++;

				String superClass = classBody.substring(i, j);

				System.out.println(superClass);

				// path.replace(c, u);
				// System.out.println(path);
				// return isInherited(u, u, path);

			}
		} catch (Exception e) {
		}

		return false;
	}

	private static HashSet<String> getVariableTypeSet(String path) {

		HashSet<String> variableTypeSet = new HashSet<String>();

		try {

			FileReader fr = new FileReader(path);

			CompilationUnit cu = JavaParser.parse(fr, false);

			for (TypeDeclaration typeDec : cu.getTypes()) {
				List<BodyDeclaration> members = typeDec.getMembers();
				if (members != null) {
					for (BodyDeclaration member : members) {
						// Check just members that are FieldDeclarations
						FieldDeclaration field = (FieldDeclaration) member;
						// Print the field's class typr
						variableTypeSet.add(field.getType().toString());
					}
				}
			}

			fr.close();

		} catch (Exception e) {
		}

		return variableTypeSet;
	}

	private static HashSet<String> getVariableSet(String path) {

		HashSet<String> variableSet = new HashSet<String>();

		try {

			FileReader fr = new FileReader(path);

			CompilationUnit cu = JavaParser.parse(fr, false);

			for (TypeDeclaration typeDec : cu.getTypes()) {
				List<BodyDeclaration> members = typeDec.getMembers();
				if (members != null) {
					for (BodyDeclaration member : members) {
						// Check just members that are FieldDeclarations
						FieldDeclaration field = (FieldDeclaration) member;
						// Print the field's class typr
						variableSet.add(field.getVariables().get(0).getId()
								.getName());
					}
				}
			}

			fr.close();

		} catch (Exception e) {
		}

		return variableSet;
	}

	private static HashSet<MethodDeclaration> getMethodSet(String path) {

		HashSet<MethodDeclaration> methodSet = new HashSet<MethodDeclaration>();

		try {

			FileReader fr = new FileReader(path);

			CompilationUnit cu = JavaParser.parse(fr, false);

			new MethodVisitor().visit(cu, methodSet);

			fr.close();

		} catch (Exception e) {
		}

		return methodSet;
	}

	private static HashSet<String> getClassNameSet(String path) {

		HashSet<String> classNameSet = new HashSet<String>();

		String[] fileNames = new File(path).list();

		for (int i = 0; i < fileNames.length; i++)
			try {
				classNameSet.add(fileNames[i].substring(0,
						fileNames[i].indexOf(".java")));
			} catch (StringIndexOutOfBoundsException e) {
			}

		return classNameSet;
	}

	private static class MethodVisitor extends
			VoidVisitorAdapter<HashSet<MethodDeclaration>> {

		public void visit(MethodDeclaration md,
				HashSet<MethodDeclaration> methodSet) {

			methodSet.add(md);
		}
	}
}
