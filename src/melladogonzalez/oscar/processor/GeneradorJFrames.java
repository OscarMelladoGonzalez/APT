package melladogonzalez.oscar.processor;

public class GeneradorJFrames {
	public static String getPackage() {
		return "package GUI;\n";
	}

	public static String getImports(String nombrePaquete) {
		return "import java.awt.BorderLayout; \n"
				+ "import javax.swing.JFrame; \n"
				+ "import javax.swing.JPanel; \n"
				+ "import javax.swing.JButton; \n"
				+ "import java.awt.event.ActionEvent;\n"
				+ "import java.awt.event.ActionListener;\n"
				+ "import java.util.ArrayList; \n"
				+ "import " + nombrePaquete + ";\n\n";
	}

	public static String getClass(String aptClassName) {
		return "public class " + aptClassName + " extends JFrame{\n";
	}

	public static String getAttributes(String aptClassName) {
		return "	private  static final " + aptClassName + " instance" + aptClassName + " = new " + aptClassName +"();\n"
				+ "	private " + aptClassName + "JPanel form;\n";
	}
	
	public static String getInstance(String aptClassName){
		return "	public static " + aptClassName + " getInstance(){\n"
				+ "		return instance" + aptClassName + ";\n"
				+ "	}\n";
	}

	public static String getConstructorSimple(String aptClassName, String titleForm, String labels,
			int width) {
		return "	public " + aptClassName + "(){\n"
				+ "		ArrayList<String> labels = new ArrayList<String>();\n"
				+ "		ArrayList<String> descs = new ArrayList<String>();\n"
				+ "		int width = "
				+ width
				+ ";\n"
				+ labels
				//+ descs
				+ "		"+aptClassName+"A(\""+titleForm+"\",labels,width,descs);\n"
				+ "	}\n";
	}
	
	public static String getConstructor(String aptClassName, String aptClassNameAux, String CallRangeMethod) {
		return "	public void "
				+ aptClassName
				+ "A(String title,ArrayList<String> labels, int width, ArrayList<String> tips) {\n"
				//+ "		final " + aptClassNameAux 
				+ "		form = new " + aptClassNameAux + "(labels, width, tips);\n"
				+ "		setTitle(title);\n"
				+ "		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);\n"
				+ "		getContentPane().add(form, BorderLayout.NORTH);\n"
	    		+ "    	JButton submit = new JButton(\"Enviar\"); \n"
	    		+ "		submit.addActionListener(new ActionListener() { \n"
	    		+ "			public void actionPerformed(ActionEvent e) { \n"
	    		+ CallRangeMethod
	    		+ " 			HideGUI();\n"	
	    		+ "			}\n"
	    		+ "		});\n"
	    		+ "		JPanel p = new JPanel();\n"
	    		//+ getButtonRangeValue(campo, min, max)
	    		+ "		p.add(submit);\n"
	    		+ "		getContentPane().add(p, BorderLayout.SOUTH);\n"
	    		+ "		pack();\n"
	    		+ "		setVisible(true);\n"
	    		+ "	}\n";
	}
	public static String getMain(String aptClassNameAux, String titleForm, String labels,
			int width) {
		return "	public static void main(String[] args) {\n"
				+ "		ArrayList<String> labels = new ArrayList<String>();\n"
				+ "		ArrayList<String> descs = new ArrayList<String>();\n"
				+ "		int width = "
				+ width
				+ ";\n"
				+ labels
				//+ descs
				+ "		//new "+aptClassNameAux+"(\""+titleForm+"\",labels,width,descs);\n"
				+ "	}\n" + "}\n\n";
	}
	
	public static  String getShowGUI(String aptClassName){
		return " public void ShowGUI(){\n"
				+ "		instance" + aptClassName + ".setVisible(true);\n" 
				+ "	}\n";
	}

	public static  String getHideGUI(String aptClassName){
		return " public void HideGUI(){\n"
				+ "		instance" + aptClassName + ".setVisible(false);\n" 
				+ "	}\n";
	}
	
	public static String getButtonRangeValue(String campo, int min, int max){
		if(campo == "")
			return "";
		else
			return "		submit.addActionListener(new ActionListener() {\n"
					+ "			public void actionPerformed(ActionEvent e) {\n"
					+ " 			try {\n"
					+"  				if(!form." + campo + ".getText().equals(\"\")){\n"
					+ "   					if (Integer.parseInt(form." + campo + ".getText()) < " + min + "\n"
					+ "  						|| Integer.parseInt(form." + campo + ".getText()) > "  + max + ")\n"
					+ "   						JOptionPane.showMessageDialog(null,\"El numero está fuera del rango " + min + " - " + max + "\");\n"	
					+ "					}"
					+ "     		} catch (Exception ex) {\n"			
					+ "   				JOptionPane.showMessageDialog(null,\"Solo se puede insertar numeros\");\n"
					+ " 			}\n"
					+ "  		}\n"
					+ " 	});\n";
	}
	
	public static String getInfoForm(){
		return "	public ArrayList<String> getAtributos(){\n"
				+ "		return form.getAtributos();\n"
				+ "	}\n";
	}
	
	public static String getInstanceClass(String aptClassName){
		String nombreClase = aptClassName.substring(3);
		String cadena = "";
		cadena += "	public " + nombreClase + " getInstanceClass"+nombreClase+"() { \n"
		+ "		return form.getInstanceClass" + nombreClase + "();\n"
		+ "	}\n";	
		return cadena;
	}
	
	public static String setInstanceClass(String aptClassName){
		String nombreClase = aptClassName.substring(3);
		String cadena = "";
		cadena += "	public void setInstanceClass"+nombreClase+"(Object o) { \n"
		+ "		form.setInstanceClass" + nombreClase + "(o);\n"
		+ "	}\n";	
		return cadena;
	}
}
