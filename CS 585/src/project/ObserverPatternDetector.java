package project;

import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ObserverPatternDetector {

	public static void detect(String path) {

		Grabber g = new Grabber();

		HashMap<String, HashMap<String, HashSet<String>>> classMap = g
				.grab(path);

		ArrayList<String> listenerList = new ArrayList<String>();

		String[] classKeys = getKeys(classMap.keySet());

		for (int i = 0; i < classKeys.length; i++) {

			for (int j = 0; j < classKeys.length; j++) {

				String[] methodKeys = getKeys(classMap.get(classKeys[j])
						.keySet());

				classMap.get(classKeys[j]).keySet().toArray(methodKeys);

				for (int k = 0; k < methodKeys.length; k++)
					if (i != j
							&& !listenerList.contains(classKeys[i])
							&& classMap.get(classKeys[j]).get(methodKeys[k])
									.contains(classKeys[i]))
						listenerList.add(classKeys[i]);
			}
		}

		for (int i = 0; i < classKeys.length; i++)
			for (int j = 0; j < listenerList.size(); j++) {

				ArrayList<MethodDeclaration> methodList = g.getMethodList(path
						+ classKeys[i] + ".java");

				if (methodList.size() < 1)
					continue;

				HashSet<MethodDeclaration> notifyMethodSet = getUpdateMethod(
						classMap.get(listenerList.get(j)).keySet(), methodList,
						listenerList.get(j));

				if (notifyMethodSet.size() > 0) {
					System.out
							.println("Observer design pattern has been detected.");
					return;
				}
			}
	}

	private static HashSet<MethodDeclaration> getUpdateMethod(
			Set<String> methodSet, ArrayList<MethodDeclaration> methodList,
			String listener) {

		HashSet<MethodDeclaration> notifyMethodSet = new HashSet<MethodDeclaration>();

		for (MethodDeclaration method : methodList)
			for (String s : methodSet) {

				s = "." + s + "(";

				if (method.toString().contains(s))
					if (method.getParameters() != null) {
						for (Parameter p : method.getParameters())
							if (!listener.equals(p.toString().substring(0,
									p.toString().indexOf(" "))))
								notifyMethodSet.add(method);
					} else
						notifyMethodSet.add(method);
			}

		return notifyMethodSet;
	}

	private static String[] getKeys(Set<String> keySet) {

		String[] keys = new String[keySet.size()];

		keySet.toArray(keys);

		return keys;
	}
}
