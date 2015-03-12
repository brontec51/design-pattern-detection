package project;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.List;

public class VisitorPatternDetector {

	public static void detect(String path) {

		HashSet<String> cSet = getClassNameSet(path);

		HashSet<Object[]> Y = new HashSet<Object[]>();

		for (String c : cSet) {

			HashSet<MethodDeclaration> aSet = getMethodSet(path + c + ".java");

			for (MethodDeclaration a : aSet) {

				List<Parameter> pList = a.getParameters();

				if (pList == null)
					continue;

				HashSet<String> c_Set = getClassNameSet(pList, path);

				for (String c_ : c_Set) {

					HashSet<MethodDeclaration> vSet = getMethodSet(path + c_
							+ ".java");

					for (MethodDeclaration v : vSet) {

						if (isVisitOrAcceptMethod(c, a, c_, v, path + c
								+ ".java")) {
							Object[] objs = { c, a, c_, v };
							Y.add(objs);
						}
					}
				}
			}
		}

		HashSet<Object[]> C = new HashSet<Object[]>();

		for (Object[] y : Y) {

			String cPath = path + (String) y[0] + ".java";

			HashSet<MethodDeclaration> oSet = getMethodSet(cPath);

			for (MethodDeclaration o : oSet) {
				if (isCallBack((String) y[2], (MethodDeclaration) y[3], o,
						cPath))
					C.add(y);
			}
		}

		if (C.size() > 0)
			System.out.println("Visitor design pattern has been detected.");
	}

	private static boolean isCallBack(String c_, MethodDeclaration o,
			MethodDeclaration v, String path) {

		String callBack = "." + o.getName() + "(";

		String methodBody = v.toString();

		// System.out.println(c_ + "/" + v.getName() + "/" + o.getName());

		int i = 0;

		while (methodBody.indexOf(callBack, i) > 0) {

			i = methodBody.indexOf(callBack, i);

			int j = i;

			while (methodBody.charAt(j - 1) == '_'
					|| Character.isLetterOrDigit(methodBody.charAt(j - 1)))
				j--;

			String z = methodBody.substring(j, i);

			z = z.equals("this") ? c_ : z;

			String zClass = getType(z, v.toString(), path);

			if (c_.equals(zClass))
				return true;

			i += callBack.length();
		}

		return false;
	}

	private static boolean isVisitOrAcceptMethod(String c, MethodDeclaration a,
			String c_, MethodDeclaration v, String path) {

		String accept = "." + v.getName() + "(";

		String methodBody = a.toString();

		int i = 0;

		while (methodBody.indexOf(accept, i) > 0) {

			i = methodBody.indexOf(accept, i);

			int j = i;

			while (methodBody.charAt(j - 1) == '_'
					|| Character.isLetterOrDigit(methodBody.charAt(j - 1)))
				j--;

			String x = methodBody.substring(j, i);
			String y = methodBody.substring(i + accept.length(),
					methodBody.indexOf(")", i));

			x = x.equals("this") ? c : x;

			String xClass = getType(x, a.toString(), path);
			String yClass = y.equals("this") ? c : getType(y, a.toString(),
					path);

			if (c_.equals(xClass) && c.equals(yClass))
				return true;

			i += accept.length();
		}

		return false;
	}

	private static String getType(String objVar, String methodBody, String path) {

		String classBody = "";

		objVar = " " + objVar;

		try {

			FileReader fr = new FileReader(path);

			CompilationUnit cu = JavaParser.parse(fr, false);

			classBody = cu.toString();

			fr.close();

		} catch (Exception e) {
		}

		if (methodBody.indexOf(objVar) < 0) {

			int i = -1;

			for (int k = 0; i < 0;) {

				i = k = classBody.indexOf(objVar, k);

				if (i < 0)
					return null;

				if (classBody.charAt(i + objVar.length()) == '_'
						|| Character.isLetterOrDigit(classBody.charAt(i
								+ objVar.length())))
					i = -1;

				k += objVar.length();
			}

			int j = i;

			while (classBody.charAt(j - 1) == '_'
					|| Character.isLetterOrDigit(classBody.charAt(j - 1)))
				j--;

			return classBody.substring(j, i);

		} else {

			int i = -1;

			for (int k = 0; i < 0;) {

				i = k = methodBody.indexOf(objVar, k);

				if (i < 0)
					return null;

				if (methodBody.charAt(i + objVar.length()) == '_'
						|| Character.isLetterOrDigit(methodBody.charAt(i
								+ objVar.length())))
					i = -1;

				k += objVar.length();
			}

			int j = i;

			while (methodBody.charAt(j - 1) == '_'
					|| Character.isLetterOrDigit(methodBody.charAt(j - 1)))
				j--;

			return methodBody.substring(j, i);
		}
	}

	private static HashSet<String> getClassNameSet(List<Parameter> paramList,
			String path) {

		String[] fileNames = new File(path).list();

		HashSet<String> classNameSet = new HashSet<String>();

		for (int i = 0; i < paramList.size(); i++) {

			String className = paramList.get(i).toString()
					.substring(0, paramList.get(i).toString().indexOf(" "));

			for (int j = 0; j < fileNames.length; j++)
				if ((className + ".java").equals(fileNames[j]))
					classNameSet.add(className);
		}

		return classNameSet;
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
