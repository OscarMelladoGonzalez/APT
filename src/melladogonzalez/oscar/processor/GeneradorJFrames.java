package melladogonzalez.oscar.processor;

public class GeneradorJFrames {
	public static String getPackage() {
		return "package GUI;\n";
	}

	public static String getImports() {
		return "import java.awt.BorderLayout; \n"
				+ "import javax.swing.JFrame; \n"
				+ "import javax.swing.JPanel; \n"
				+ "import javax.swing.JButton; \n"
				+ "import java.awt.event.ActionEvent;\n"
				+ "import java.awt.event.ActionListener;\n"
				+ "import java.util.ArrayList; \n";
	}

	public static String getClass(String aptClassName) {
		return "public class " + aptClassName + " extends JFrame{\n";
	}

	public static String getConstructor(String aptClassName, String aptClassNameAux, String CallRangeMethod) {
		return "		public "
				+ aptClassName
				+ "(String title,ArrayList<String> labels, int width, ArrayList<String> tips) {\n"
				+ "			final " + aptClassNameAux + " form = new " + aptClassNameAux + "(labels, width, tips);\n"
				+ "			setTitle(title);\n"
				+ "			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);\n"
				+ "			getContentPane().add(form, BorderLayout.NORTH);\n"
	    		+ "	    	JButton submit = new JButton(\"Enviar\"); \n"
	    		+ "			submit.addActionListener(new ActionListener() { \n"
	    		+ "				public void actionPerformed(ActionEvent e) { \n"
	    		+ CallRangeMethod
	    		+ "				}\n"
	    		+ "			});\n"
	    		+ "			JPanel p = new JPanel();\n"
	    		//+ getButtonRangeValue(campo, min, max)
	    		+ "			p.add(submit);\n"
	    		+ "			getContentPane().add(p, BorderLayout.SOUTH);\n"
	    		+ "			pack();\n"
	    		+ "			setVisible(true);\n"
	    		+ " 	}";
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
				+"new "+aptClassNameAux+"(\""+titleForm+"\",labels,width,descs);\n"
				+ " }\n" + "}\n";
	}
	
	public static String getButtonRangeValue(String campo, int min, int max){
		if(campo == "")
			return "";
		else
			return "			submit.addActionListener(new ActionListener() {\n"
					+ "				public void actionPerformed(ActionEvent e) {\n"
					+ " 				try {\n"
					+ "   					if (Integer.parseInt(form." + campo + ".getText()) < " + min + "\n"
					+ "  						|| Integer.parseInt(form." + campo + ".getText()) > "  + max + ")\n"
					+ "   						JOptionPane.showMessageDialog(null,\"El numero est� fuera del rango " + min + " - " + max + "\");\n"	
					+ "     			} catch (Exception ex) {\n"			
					+ "   					JOptionPane.showMessageDialog(null,\"Solo se puede insertar numeros\");\n"
					+ " 				}\n"
					+ "  			}\n"
					+ " 		});\n";
	}
}