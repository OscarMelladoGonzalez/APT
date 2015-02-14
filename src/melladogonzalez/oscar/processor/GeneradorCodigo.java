package melladogonzalez.oscar.processor;

public class GeneradorCodigo {

	public static String getImportsMain() {
		return "import java.awt.FlowLayout; \n"
				+ "import java.awt.BorderLayout;\n"
				+ "import java.awt.Dimension;\n"
				+ "import javax.swing.JFrame; \n"
				+ "import javax.swing.BorderFactory;\n"
				+ "import javax.swing.JLabel; \n"
				+ "import javax.swing.JTextField; \n"
				+ "import javax.swing.JButton; \n"
				+ "import javax.swing.JOptionPane;\n"
				+ "import javax.swing.JList;\n"
				+ "import javax.swing.JPanel;\n"
				+ "import javax.swing.JScrollPane;\n"
				+ "import java.util.ArrayList;\n"
				+ "import java.awt.event.ActionEvent;\n"
				+ "import java.awt.event.ActionListener;\n"
				+ "import MelladoGonzalez.Oscar.*;\n";
	}

	public static String getImports() {
		return "import java.awt.FlowLayout; \n"
				+ "import javax.swing.JFrame; \n"
				+ "import javax.swing.JLabel; \n"
				+ "import javax.swing.JTextField; \n"
				+ "import javax.swing.JButton; \n"
				+ "import javax.swing.JOptionPane;\n"
				+ "import java.util.ArrayList;\n"
				+ "import java.awt.event.ActionEvent;\n"
				+ "import java.awt.event.ActionListener;\n"
				+ "import MelladoGonzalez.Oscar.*;\n";
	}
	
	public static String getAttributes() {
		return "	private static JFrame ventana;\n"
				+ "	private static ArrayList<JLabel> lEtiquetas;\n"
				+ "	private static ArrayList<JTextField> lCampoTextos;\n";
	}
	
	public static String inicializarJFrame() {
		return "		ventana = new JFrame();\n"
				+ "		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);\n"
				+ "		ventana.setVisible(true);\n"
				+ "		ventana.setLayout(new FlowLayout());\n"
				+ "		lEtiquetas = new ArrayList<JLabel>();\n"
				+ "		lCampoTextos = new ArrayList<JTextField>();\n";
	}
	
	public static String getCodeAux(String titulo) {
		return    "		if(lEtiquetas.size() != lCampoTextos.size())\n"
				+ "			System.out.println(\"Algo ha fallado\"); \n"
				+ "		else{\n" 
				+ "			for(int i=0;i<lEtiquetas.size();i++){\n"
				+ "				ventana.add(lEtiquetas.get(i));\n"
				+ "				ventana.add(lCampoTextos.get(i));\n " 
				+ "			}\n "
				+ "		}\n" 
				+ " 	JButton boton = new JButton(\"Enviar\");\n"
				+ "		ventana.add(boton);\n"
				+ "		boton.addActionListener(new ActionListener() {\n"
				+ "			public void actionPerformed(ActionEvent e) {\n"
				+ "				botonGetMensaje(lCampoTextos.size());\n" 
				+ "			}\n"
				+ "		});\n" 
				+ "		ventana.setTitle(\""+ titulo + "\");\n "
				+ "		ventana.pack();\n";
	}

	public static String getCodeMain(String titulo) {
		return    "		JList<?> list;\n"
				+ "		for(int i=0;i<lEtiquetas.size();i++){\n"
				+ "			JPanel panel = new JPanel();\n"
				+ " 		panel.setLayout(new BorderLayout());\n"
				+ "			panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));\n"
				+ "			list = new JList<Object>();\n"
				+ "			JScrollPane pane = new JScrollPane();\n"
				+ "			pane.getViewport().add(list);\n"
				+ "			pane.setPreferredSize(new Dimension(250, 200));\n"
				+ "			panel.add(pane);\n"
				+ "			JLabel label = lEtiquetas.get(i);\n"
				+ "			panel.add(label, BorderLayout.NORTH);\n"
				+ "			JButton button = new JButton(\"Nuevo\");\n"
				+ "			panel.add(button, BorderLayout.SOUTH);\n"
				+ "			ventana.add(panel);\n" 
				+ "		}\n "
				+ "		ventana.setTitle(\""+ titulo + "\");\n "
				+ "		ventana.setLocationRelativeTo(null);\n "
				+ "		ventana.pack();\n";
	}

	public static String funcionesAuxBoton() {
		return    "	private static void botonGetMensaje(int size) {\n"
				+ "		switch (size) {\n"
				+ "		case 4: // Empresa\n"
				+ "			Empresa empresa = new Empresa(lCampoTextos.get(0).getText(),\n"
				+ "				Integer.parseInt(lCampoTextos.get(1).getText()));\n"
				+ "			JOptionPane.showMessageDialog(null, empresa.toString());\n"
				+ "		break;\n"
				+ "		case 5: // Direccion\n"
				+ "			Direccion direccion = new Direccion(lCampoTextos.get(0).getText(),\n"
				+ "				Integer.parseInt(lCampoTextos.get(1).getText()),\n"
				+ "				Integer.parseInt(lCampoTextos.get(2).getText()),\n"
				+ "				lCampoTextos.get(3).getText(), lCampoTextos.get(4)\n"
				+ "				.getText());\n"
				+ "			JOptionPane.showMessageDialog(null, direccion.toString());\n"
				+ "		break;\n"
				+ "		case 7: // Persona\n"
				+ "			Persona persona = new Persona(lCampoTextos.get(0).getText(),\n"
				+ "				lCampoTextos.get(1).getText(), lCampoTextos.get(2)\n"
				+ "				.getText(), Integer.parseInt(lCampoTextos.get(3)\n"
				+ "				.getText()), Integer.parseInt(lCampoTextos.get(4)\n"
				+ "				.getText()));\n"
				+ "			JOptionPane.showMessageDialog(null, persona.toString());\n"
				+ "		break;\n" 
				+ "		}\n" 
				+ "	}\n";
	}

	/*
	 * Clases de reflexion public static String getClassInfo() { return
	 * "	public static void ClassInfo()" + "\n" + "    {" + "		try { \n" +
	 * "				Class userClass = Class.forName(\"" + anotacion + "\"); \n" +
	 * "				Field[] userFieldsAll = userClass.getDeclaredFields(); \n" +
	 * "				System.out.println(\"Todos\"); \n" +
	 * "				for (Field userFields : userFieldsAll) { \n" +
	 * " 				String fieldName = userFields.getName();\n" +
	 * "					Object fieldType = userFields.getType();\n" +
	 * "					System.out.println(fieldName + \" - \" + fieldType.toString());\n"
	 * + "					lEtiquetas.add(new JLabel(fieldName));\n" +
	 * "					lCampoTextos.add(new JTextField(10));\n" + "				}\n" +
	 * "			} catch (ClassNotFoundException e) {\n" +
	 * "				e.printStackTrace(); \n" +
	 * "			} catch (IllegalArgumentException e) { \n" +
	 * "				e.printStackTrace(); \n " + "			} \n " + "	}\n"; }
	 * 
	 * public static String getMainInfo() { return
	 * "	public static boolean MainInfo()" + "\n" + "    {" + "		try { \n" +
	 * "				Class userClass = Class.forName(\"" + anotacion + "\"); \n" +
	 * "				Method getAliasMethod = userClass.getMethod(\"main\", String[].class); \n"
	 * + "				System.out.println(\"Metodos\"); \n" +
	 * "				String methodName = getAliasMethod.getName(); \n" +
	 * " 			Object methodType = getAliasMethod.getReturnType();\n" +
	 * "				System.out.println(methodName + \" - \" + methodType.toString());\n"
	 * + "				return true;\n" +
	 * "			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {\n"
	 * + "				return false; \n" + "			}\n" + "	}\n";
	 * 
	 * }
	 */
}
