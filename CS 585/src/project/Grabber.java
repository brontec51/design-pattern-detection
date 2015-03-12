package project;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Grabber {

	public HashMap<String, HashMap<String, HashSet<String>>> grab(String path) {

		return grabClass(path);

	}

	private HashMap<String, HashMap<String, HashSet<String>>> grabClass(
			String path) {

		HashMap<String, HashMap<String, HashSet<String>>> classMap = new HashMap<String, HashMap<String, HashSet<String>>>();

		String[] classNames = new File(path).list();

		for (int i = 0; i < classNames.length; i++)
			if (classNames[i].contains(".java"))
				classNames[i] = classNames[i].substring(0,
						classNames[i].length() - 5);

		for (int i = 0; i < classNames.length; i++) {

			try {

				FileReader fr = new FileReader(path + classNames[i] + ".java");

				CompilationUnit cu = JavaParser.parse(fr, false);

				ArrayList<MethodDeclaration> methodList = new ArrayList<MethodDeclaration>();

				new MethodVisitor().visit(cu, methodList);

				classMap.put(classNames[i], getMethodMap(methodList));

			} catch (Exception e) {
				continue;
			}
		}

		return classMap;
	}

	private HashMap<String, HashSet<String>> getMethodMap(
			ArrayList<MethodDeclaration> methodList) {

		HashMap<String, HashSet<String>> methodMap = new HashMap<String, HashSet<String>>();

		for (int i = 0; i < methodList.size(); i++)
			methodMap.put(methodList.get(i).getName(),
					getParameterSet(methodList.get(i).getParameters()));

		return methodMap;
	}

	private HashSet<String> getParameterSet(List<Parameter> paramList) {

		HashSet<String> paramSet = new HashSet<String>();

		if (paramList != null)
			for (int i = 0; i < paramList.size(); i++) {

				String parameter = paramList.get(i).toString();

				paramSet.add(parameter.substring(0, parameter.indexOf(" ")));
			}

		return paramSet;
	}

	public ArrayList<MethodDeclaration> getMethodList(String path) {

		ArrayList<MethodDeclaration> methodList = new ArrayList<MethodDeclaration>();

		try {

			FileReader fr = new FileReader(path);

			CompilationUnit cu = JavaParser.parse(fr, false);

			new MethodVisitor().visit(cu, methodList);

		} catch (Exception e) {
		}

		return methodList;
	}

	private static class MethodVisitor extends
			VoidVisitorAdapter<ArrayList<MethodDeclaration>> {

		public void visit(MethodDeclaration md,
				ArrayList<MethodDeclaration> methodList) {
			methodList.add(md);
		}
	}
}
