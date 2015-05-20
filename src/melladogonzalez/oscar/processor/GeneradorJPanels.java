package melladogonzalez.oscar.processor;

import java.util.ArrayList;

public class GeneradorJPanels {

	public static String getPackage() {
		return "package GUI;\n";
	}

	public static String getImports(String color, String nombrePaquete) {
		String cadena ="";
		
		//String cadena = "";//aptClassName.substring(3,aptClassName.length()-6);
		if (color != null) {
			cadena += "import java.awt.Color; \n";
		}
		cadena += "import java.awt.BorderLayout; \n"
				+ "import java.awt.FlowLayout; \n"
				+ "import java.awt.GridLayout; \n"
				+ "import java.awt.event.ActionEvent; \n"
				+ "import java.awt.event.ActionListener; \n"
				+ "import javax.swing.JButton; \n"
				+ "import Controllers.*; \n"
				+ "import javax.swing.DefaultListModel;\n"
				+ "import java.lang.reflect.Field;\n"
				//+ "import javax.swing.JFrame; \n"
				+ "import javax.swing.JOptionPane; \n"
				+ "import javax.swing.JLabel; \n"
				+ "import javax.swing.JPanel; \n"
				+ "import javax.swing.JTextField; \n"
				+ "import javax.swing.JComboBox;\n"
				+ "import java.util.ArrayList; \n"
				+ "import javax.swing.JList;\n"
				+ "import javax.swing.JScrollPane;\n"
				+ "import java.awt.Dimension;\n"
				+ "import " + nombrePaquete + ";\n"
				+ "import javax.swing.JCheckBox;\n\n";
		return cadena;
	}

	public static String getClass(String aptClassName) {
		return "public class " + aptClassName + " extends JPanel{\n";
	}

	public static String getAttributes(ArrayList<String> lNombres,
			ArrayList<String> lTipos) {
		String cadena = "";
		cadena += " 	private JPanel p;\n";
		for (int i = 0; i < lTipos.size(); i++) {
			cadena += " 	private JLabel " + lNombres.get(i)
					+ "Label = new JLabel(\"" + lNombres.get(i)
					+ "\", JLabel.RIGHT);\n";
			switch (lTipos.get(i)) {
			case "java.lang.String": // Tipo String, JTextField
				cadena += " 	private JTextField " + lNombres.get(i)
						+ " = new JTextField();\n";
				break;
			case "int": // Tipo int, JTextField
				cadena += " 	private JTextField " + lNombres.get(i)
						+ " = new JTextField();\n";
				break;
			case "boolean": // Tipo Boolean, JCheckBox
				cadena += "	private JCheckBox " + lNombres.get(i)
						+ " = new JCheckBox();\n";
				break;
			case "java.lang.String[]": // Tipo String[], JComboBox<String>
				cadena += "	private JComboBox<String> " + lNombres.get(i)
						+ " = new JComboBox<String>();\n";
				break;
			default: // Tipo Array/class/enum, JList
				cadena += "	private JList<?> " + lNombres.get(i)
						+ " = new JList<Object>();\n";
				cadena += "	private JScrollPane " + lNombres.get(i)
						+ "Scroll = new JScrollPane("+ lNombres.get(i) +");\n";
				cadena += "	private DefaultListModel " + lNombres.get(i) 
						+ "Model = new DefaultListModel<String>();\n" ;
				break;
			}
		}
		return cadena + "\n";
	}

	public static String getConstructor(String aptClassName, String color,
			boolean main) {
		if (main) {
			String cadena = "		public "
					+ aptClassName
					+ "(ArrayList<String> labels, int width, ArrayList<String> tips) {\n"
					+ "		JList<?> list;\n"
					+ "		for (int i = 0; i < labels.size(); i++) {\n"
					+ "			JPanel panel = new JPanel();\n"
					+ "			panel.setLayout(new BorderLayout());\n"
					+ "			panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));\n"
					+ "			list = new JList<Object>();\n"
					+ "			JScrollPane pane = new JScrollPane();\n"
					+ "			pane.getViewport().add(list);\n"
					+ "			pane.setPreferredSize(new Dimension(250, 200));\n"
					+ "			panel.add(pane);\n"
					+ "			JLabel label = new JLabel(labels.get(i));\n"
					+ "			panel.add(label, BorderLayout.NORTH);\n"
					+ "			JButton button = new JButton(\"Nuevo\");\n"
					+ "			panel.add(button, BorderLayout.SOUTH);\n";
			if (color != null)
				cadena += "			panel.setBackground(Color." + color + ");\n";
			cadena += " 			add(panel);\n" + " 		}\n" + " 	}\n";
			return cadena;

		} else {
			String cadena = "	public "
					+ aptClassName
					+ "(ArrayList<String> labels, int width, ArrayList<String> tips) {\n"
					+ "		super(new BorderLayout());\n"
					+ "		JPanel labelPanel = new JPanel(new GridLayout(labels.size(), 1));\n"
					+ "		JPanel fieldPanel = new JPanel(new GridLayout(labels.size(), 1));\n\n";
			if (color != null) {
				cadena += "		labelPanel.setBackground(Color." + color + ");\n";
			}
			cadena += "		add(labelPanel, BorderLayout.WEST);\n"
					+ "		add(fieldPanel, BorderLayout.CENTER);\n"
					+ "		fields = new JTextField[labels.size()];\n\n"
					+ "		for (int i = 0; i < labels.size(); i += 1) {\n"
					+ "			fields[i] = new JTextField();\n"
					+ "			if (i < tips.size())\n"
					+ "				fields[i].setToolTipText(tips.get(i));\n"
					+ "				fields[i].setColumns(width);\n\n"
					+ "				JLabel lab = new JLabel(labels.get(i), JLabel.RIGHT);\n"
					+ "				lab.setLabelFor(fields[i]);\n"
					+ "				labelPanel.add(lab);\n"
					+ "				JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));\n"
					+ "				p.add(fields[i]);\n" 
					+ "				fieldPanel.add(p);\n"
					+ "			}\n" + "		}\n";
			return cadena;
		}
	}

	public static String getConstructorGenerico(String aptClassName,
			String color, ArrayList<String> lNombres, ArrayList<String> lTipos, ArrayList<String> lDatosComboBox) {
		int j = 0;
		String cadena = "	public "
				+ aptClassName
				+ "(ArrayList<String> labels, int width, ArrayList<String> tips) {\n"
				+ "		super(new BorderLayout());\n"
				+ "		JPanel labelPanel = new JPanel(new GridLayout(labels.size(), 1));\n"
				+ "		JPanel fieldPanel = new JPanel(new GridLayout(labels.size(), 1));\n\n"
				+ "		labelPanel.setPreferredSize(new Dimension(100, 300));\n"
				+ "		fieldPanel.setPreferredSize(new Dimension(500, 300));\n\n";
		if (color != null)
			cadena += "		labelPanel.setBackground(Color." + color + ");\n";
		
		cadena += "		add(labelPanel, BorderLayout.WEST);\n"
				+ "		add(fieldPanel, BorderLayout.CENTER);\n";

		for (int i = 0; i < lTipos.size(); i++) {
			switch (lTipos.get(i)) {
			case "java.lang.String": // Tipo String, JTextField
				cadena += "		" + lNombres.get(i)
						+ ".setToolTipText(\"tips\");\n";
				cadena += "		" + lNombres.get(i) + ".setColumns(width);\n";
				cadena += "		" + lNombres.get(i) + "Label.setLabelFor("
						+ lNombres.get(i) + ");\n";
				cadena += "		labelPanel.add(" + lNombres.get(i) + "Label);\n\n";
				cadena += "		p = new JPanel(new FlowLayout(FlowLayout.LEFT));\n"
						+ "		p.add("
						+ lNombres.get(i)
						+ ");\n"
						+ "	 	fieldPanel.add(p);\n";
				break;
			case "int": // Tipo int, JTextField
				cadena += "		" + lNombres.get(i)
						+ ".setToolTipText(\"tips\");\n";
				cadena += "		" + lNombres.get(i) + ".setColumns(width);\n";
				cadena += "		" + lNombres.get(i) + "Label.setLabelFor("
						+ lNombres.get(i) + ");\n";
				cadena += "		labelPanel.add(" + lNombres.get(i) + "Label);\n\n";
				cadena += "		p = new JPanel(new FlowLayout(FlowLayout.LEFT));\n"
						+ "		p.add("
						+ lNombres.get(i)
						+ ");\n"
						+ "		fieldPanel.add(p);\n";
				break;
			case "boolean": // Tipo Boolean, JCheckBox
				cadena += "		" + lNombres.get(i)
						+ ".setToolTipText(\"tips\");\n";
				cadena += "		" + lNombres.get(i) + "Label.setLabelFor("
						+ lNombres.get(i) + ");\n";
				cadena += "		labelPanel.add(" + lNombres.get(i) + "Label);\n\n";
				cadena += "		p = new JPanel(new FlowLayout(FlowLayout.LEFT));\n"
						+ "		p.add("
						+ lNombres.get(i)
						+ ");\n"
						+ "		fieldPanel.add(p);\n";
				break;
				// Insertar los valores #Completar
			case "java.lang.String[]": // Tipo String[], JComboBox<String>
				try{
					cadena += lDatosComboBox.get(j);
				}catch(Exception ex){
					cadena += "		" + lNombres.get(i) + ".addItem(\" Se debe inicializar el comboBox \");\n";
				}
				cadena += "		" + lNombres.get(i)
						+ ".setToolTipText(\"tips\");\n";
				cadena += "		" + lNombres.get(i) + "Label.setLabelFor("
						+ lNombres.get(i) + ");\n";
				cadena += "		labelPanel.add(" + lNombres.get(i) + "Label);\n\n";
				cadena += "		p = new JPanel(new FlowLayout(FlowLayout.LEFT));\n"
						+ "		p.add("
						+ lNombres.get(i)
						+ ");\n"
						+ "		fieldPanel.add(p);\n";
				j++;
				break;
			default: // Tipo Array/class/enum, JList
				cadena += "		" + lNombres.get(i) + "Label.setLabelFor("
						+ lNombres.get(i) + ");\n";
				cadena += "		labelPanel.add(" + lNombres.get(i) + "Label);\n\n";
				cadena += "		p = new JPanel(new FlowLayout(FlowLayout.LEFT));\n";
				cadena += "		" + lNombres.get(i) + ".setSelectedIndex(1);\n"	;
				cadena += "		" + lNombres.get(i) + ".setModel(" + lNombres.get(i) + "Model);\n"	;
				cadena += "		p.add(" + lNombres.get(i) + "Scroll);\n";
				cadena += "		fieldPanel.add(p);\n";
				
				//Realizar split por los puntos, para obtener el nombre de la clase
				String[] descomp = lTipos.get(i).split("\\.");
				String nombreClase = "";
				nombreClase = descomp[descomp.length -1];
				if(lTipos.get(i).contains("Array"))
					nombreClase = nombreClase.substring(0, nombreClase.length()-1);
				cadena += listButtons(lNombres.get(i), nombreClase);
				break;
			}
		}
		cadena += "	}\n\n";
		return cadena;
	}
	
	public static String listButtons(String nombre, String controlador){
		return addListButton(nombre, controlador)
		+ updListButton(nombre, controlador)
		+ delListButton(nombre);
	}
	
	public static String addListButton(String nombre, String controlador){
		return "		JButton add"+ nombre +" = new JButton(\"add\");\n"
				+ "		add" + nombre + ".addActionListener(new ActionListener(){ \n"
				+ "			public void actionPerformed(ActionEvent e){ \n"
				+ "				//Controller\n"
				+ "				" + controlador + "Controller " + controlador + "C = new " 
 								+ controlador + "Controller();\n"
				+ "				Object o = " + controlador + "C.add();\n"
				+ "				if(o != null)\n"
				+ "					" + nombre + "Model.addElement(o);\n"
				//+ "				" + nombre + "Controller c" + nombre + " = new " + nombre + "Controller()\n;"
				//+ "				c" + nombre + "add();\n"
				+ "			}\n"
				+ "		});\n"
				+ "		p.add(add" + nombre + ");\n";
	}
	
	public static String delListButton(String nombre){
		return "		JButton del"+ nombre +" = new JButton(\"delete\");\n"
				+ "		del" + nombre + ".addActionListener(new ActionListener(){ \n"
				+ "			public void actionPerformed(ActionEvent e){ \n"
				+ "				//Controller\n"
				+ "				if(" + nombre + ".getSelectedValue() !=  null)\n"
				+ "					" + nombre + "Model.removeElement(" + nombre + ".getSelectedValue());\n"
				//+ "				" + nombre + "Controller c" + nombre + " = new " + nombre + "Controller();\n"
				//+ "				c" + nombre + "delete();\n"
				+ "			}\n"
				+ "		});\n"
				+ "		p.add(del" + nombre + ");\n";
	}
	public static String updListButton(String nombre, String controlador){
		return "		JButton upd"+ nombre +" = new JButton(\"update\");\n"
				+ "		upd" + nombre + ".addActionListener(new ActionListener(){ \n"
				+ "			public void actionPerformed(ActionEvent e){ \n"
				+ "				//Controller\n"
				+ "				" + controlador + "Controller " + controlador + "C = new " 
					+ controlador + "Controller();\n"
				+ "				if(" + nombre + ".getSelectedValue() != null)\n"
				+ "					" + controlador + "C.update(" + nombre + ".getSelectedValue());\n"
				//+ "					((Gui" + controlador + ")" + nombre + ".getSelectedValue()).setInstanceClass" + controlador + "("+ nombre + ".getSelectedValue());\n" 			
				/*				if(lEmpresas.getSelectedValue() != null){
					((GuiEmpresa)lEmpresas.getSelectedValue()).ShowGUI();
				}*/
				//+ "				" + nombre + "Controller c" + nombre + " = new " + nombre + "Controller();\n"
				//+ "				c" + nombre + "update();\n"
				+ "			}\n"
				+ "		});\n"
				+ "		p.add(upd" + nombre + ");\n";
	}
	public static String getInfoForm(ArrayList<String> lNombres, ArrayList<String> lTipos){
		String cadena = "";
		cadena += "	public ArrayList<String> getInfoForm(){\n"
			+ "		ArrayList <String> lAtributos = new ArrayList<String>();\n";
				
			for (int i = 0; i < lTipos.size(); i++) {
				switch (lTipos.get(i)) {
				case "java.lang.String": // Tipo String, JTextField
					cadena += "		lAtributos.add(" 
							+ lNombres.get(i)+ ".getText());\n";
					break;
				case "int": // Tipo int, JTextField
					cadena += "		lAtributos.add(" 
							+ lNombres.get(i)+ ".getText());\n";
					break;
				case "boolean": // Tipo Boolean, JCheckBox
					cadena += "		lAtributos.add(" 
							+ lNombres.get(i)+ ".getText());\n";
					break;
				case "java.lang.String[]": // Tipo String[], JComboBox<String>
					cadena += "		lAtributos.add(" 
							+ lNombres.get(i)+ ".getSelectedItem().toString());\n";
					break;
				default: // Tipo Array/class/enum, JList
					break;
				}
		}
		cadena += "		return lAtributos;\n	}\n\n";
		return cadena;
	}
	

	public static String getMethod() {
		return "	public String getText(int i) {\n"
				+ "		return (fields[i].getText());\n" 
				+ "	}\n";
	}
	
	public static String getInstanceClass(String aptClassName, ArrayList<String> lNombres, ArrayList<String> lTipos){
		String nombreClase = aptClassName.substring(3,aptClassName.length()-6);
		String getCampos = "";
		String cadena = "";
		for (int i = 0; i < lTipos.size(); i++) {
			switch (lTipos.get(i)) {
			case "java.lang.String": // Tipo String, JTextField
				getCampos += "			Field fld" + lNombres.get(i) + " = ce.getDeclaredField(\"" + lNombres.get(i) + "\");\n"
						+ "			fld" + lNombres.get(i) + ".setAccessible(true);\n"
						+ "			fld" + lNombres.get(i) + ".set(ret, " + lNombres.get(i) + ".getText());\n";
				break;
			case "int": // Tipo int, JTextField
				getCampos += "			Field fld" + lNombres.get(i) + " = ce.getDeclaredField(\"" + lNombres.get(i) + "\");\n"
						+ "			fld" + lNombres.get(i) + ".setAccessible(true);\n"
						+ "			fld" + lNombres.get(i) + ".set(ret, Integer.parseInt(\"91\"+" + lNombres.get(i) + ".getText()));\n";
				break;
			case "boolean": // Tipo Boolean, JCheckBox
				getCampos += "			Field fld" + lNombres.get(i) + " = ce.getDeclaredField(\"" + lNombres.get(i) + "\");\n"
						+ "			fld" + lNombres.get(i) + ".setAccessible(true);\n"
						+ "			fld" + lNombres.get(i) + ".set(ret, " + lNombres.get(i) + ".getText());\n";
				break;
			case "java.lang.String[]": // Tipo String[], JComboBox<String>
				break;
			default: // Tipo Array/class/enum, JList				 
				break;
			}
		}
		cadena += "	public " + nombreClase + " getInstanceClass" + nombreClase +"() { \n"
				+ "		" + nombreClase + " ret = new " + nombreClase + "();\n"
				+ "		Class<?> ce = ret.getClass();\n"
				+ "		try {\n"
				+ getCampos
				+ "		} catch (Exception e){\n"
				+ "			e.printStackTrace();\n"
				+ "		}\n"
				+ "		return ret;\n"
				+ "	}\n";
		return cadena;
	}
	
	public static String setInstanceClass(String aptClassName, ArrayList<String> lNombres, ArrayList<String> lTipos){
		String nombreClase = aptClassName.substring(3,aptClassName.length()-6);
		String getCampos = "";
		String cadena = "";
		for (int i = 0; i < lTipos.size(); i++) {
			switch (lTipos.get(i)) {
			case "java.lang.String": // Tipo String, JTextField
				getCampos += "			Field fld" + lNombres.get(i) + " = ce.getDeclaredField(\"" + lNombres.get(i) + "\");\n"
						+ "			fld" + lNombres.get(i) + ".setAccessible(true);\n"
						+ "			fld" + lNombres.get(i) + ".set(" + lNombres.get(i) + ".getText(), ret);\n";
				break;
			case "int": // Tipo int, JTextField
				getCampos += "			Field fld" + lNombres.get(i) + " = ce.getDeclaredField(\"" + lNombres.get(i) + "\");\n"
						+ "			fld" + lNombres.get(i) + ".setAccessible(true);\n"
						+ "			fld" + lNombres.get(i) + ".set(" + lNombres.get(i) + ".getText(), ret);\n";
				break;
			case "boolean": // Tipo Boolean, JCheckBox
				getCampos += "			Field fld" + lNombres.get(i) + " = ce.getDeclaredField(\"" + lNombres.get(i) + "\");\n"
						+ "			fld" + lNombres.get(i) + ".setAccessible(true);\n"
						+ "			fld" + lNombres.get(i) + ".set(" + lNombres.get(i) + ".getText(), ret);\n";
				break;
			case "java.lang.String[]": // Tipo String[], JComboBox<String>
				break;
			default: // Tipo Array/class/enum, JList				 
				break;
			}
		}
		cadena += "	public void setInstanceClass" + nombreClase +"(Object o) { \n"
				+ "		" + nombreClase + " ret = (" + nombreClase + ") o;\n"
				+ "		Class<?> ce = ret.getClass();\n"
				+ "		try {\n"
				+ getCampos
				+ "		} catch (Exception e){\n"
				+ "			e.printStackTrace();\n"
				+ "		}\n"
				+ "	}\n";
		return cadena;
	}
}
