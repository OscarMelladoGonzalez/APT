package melladogonzalez.oscar.processor;

import java.util.ArrayList;

public class GeneradorControllers {
	
	public static String getInterface(){
		return "package Controllers;\n\n"
		+ "public interface Controller<T> {\n\n"
		+ "	public Object add();\n"
		+ "	public Object delete();\n"
		+ "	public void update(Object o);\n\n"
		+ "}\n";
	}
	
	public static String getPackage() {
		return "package Controllers;\n"
				+ "import GUI.*;\n"
				+ "import MelladoGonzalez.Oscar.*;\n\n";
	}

	public static String getClass(String aptClassName, String aptName) {
		return "public class " + aptClassName + " implements Controller<" + aptName + ">{\n";
	}

	public static String getMethodAdd(String aptClassName) {
		int length = aptClassName.length();
		String name = aptClassName.substring(0, length - 10);
		return "	@Override\n" 
				+ "	public Object add() {\n"
				+ "		// TODO Auto-generated method stub\n" 
				+ "		Gui" + name + " g"+ name +" = Gui" + name + ".getInstance();\n"
				+ "		g"+ name + ".ShowGUI();\n"
				//+ "		g"+ name + ".HideGUI();\n"
				+ "		return g" + name + ".getInstanceClass" + name + "();\n"
				//+ "		return new " + name + "();\n"
				+ "	}\n";
	}
	
	public static String getMethodDelete() {
		return "	@Override\n" 
				+ "	public Object delete() {\n"
				+ "		// TODO Auto-generated method stub\n" 
				+ "		return null;\n"
				+ "	}\n";
	}
	
	public static String getMethodUpdate(String aptClassName) {
		int length = aptClassName.length();
		String name = aptClassName.substring(0, length - 10);
		return "	@Override\n" 
				+ "	public void update(Object o) {\n"
				+ "		// TODO Auto-generated method stub\n" 
				+ "		Gui" + name + " g"+ name +" = Gui" + name + ".getInstance();\n"
				+ "		g" + name + ".setInstanceClass" + name + "(o);\n"
				+ "		g"+ name + ".ShowGUI();\n"
				//+ "		g"+ name + ".HideGUI();\n"
				+ "	}\n";
	}

	public static String getMethod() {
		return "	@Override\n" 
				+ "	public Object update() {\n"
				+ "		// TODO Auto-generated method stub\n" 
				+ "		return null;\n"
				+ "	}\n";
	}
	
	public static String getAttributes(ArrayList<String> lNombres,
			ArrayList<String> lTipos) {
		String cadena = "";
		ArrayList<String> lTiposController = new ArrayList<String>();
		ArrayList<String> lNombresController = new ArrayList<String>();
		for (int i = 0;i < lTipos.size(); i++) 
			if(lTipos.get(i).contains("Mellado")){
				lTiposController.add(lTipos.get(i));
				lNombresController.add(lNombres.get(i));
			}
		
		for(int i = 0;i < lTiposController.size(); i++)	{
				cadena += " 	/* " 
						+ lNombresController.get(i) 
						+ "  -  " 
						+ lTiposController.get(i)
						+ " */\n";
		}
		return cadena + "\n";
	}
	

	public static String getGetsSets(ArrayList<String> lNombres, ArrayList<String> lTipos) {
		String cadena = "";

		for (int i = 0; i < lTipos.size(); i++) {
			switch (lTipos.get(i)) {
			case "java.lang.String": // Tipo String
				//Get
				cadena += "	public String get" + lNombres.get(i) + "(){\n"
						+ "		return " + lNombres.get(i) + ";\n"
						+ "	}\n";
				//Set
				cadena += "	public void set" + lNombres.get(i) + "(String " + lNombres.get(i) + "){\n"
						+ "		this." + lNombres.get(i) + " = " + lNombres.get(i) + ";\n"
						+ "	}\n";
				break;
			case "int": // Tipo int
				//Get
				cadena += "	public int get" + lNombres.get(i) + "(){\n"
						+ "		return " + lNombres.get(i) + ";\n"
						+ "	}\n";
				//Set
				cadena += "	public void set" + lNombres.get(i) + "(int " + lNombres.get(i) + "){\n"
						+ "		this." + lNombres.get(i) + " = " + lNombres.get(i) + ";\n"
						+ "	}\n";
				break;
			case "boolean": // Tipo Boolean
				//Get
				cadena += "	public Boolean get" + lNombres.get(i) + "(){\n"
						+ "		return " + lNombres.get(i) + ";\n"
						+ "	}\n";
				//Set
				cadena += "	public void set" + lNombres.get(i) + "(Boolean " + lNombres.get(i) + "){\n"
						+ "		this." + lNombres.get(i) + " = " + lNombres.get(i) + ";\n"
						+ "	}\n";
				break;
			case "java.lang.String[]": // Tipo String[]
				//Get
				cadena += "	public String[] get" + lNombres.get(i) + "(){\n"
						+ "		return " + lNombres.get(i) + ";\n"
						+ "	}\n";
				//Set
				cadena += "	public void set" + lNombres.get(i) + "(String[] " + lNombres.get(i) + "){\n"
						+ "		this." + lNombres.get(i) + " = " + lNombres.get(i) + ";\n"
						+ "	}\n";
				break;
			default: // Tipo Array/class/enum
				break;
			}
		}
		cadena += "\n";
		return cadena;
	}
	
	public static String endClass() {
		return "}\n";
	}
}
