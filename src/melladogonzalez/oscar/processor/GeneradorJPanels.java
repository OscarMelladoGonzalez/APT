package melladogonzalez.oscar.processor;

import java.util.ArrayList;

public class GeneradorJPanels {

	public static String getPackage() {
		return "package GUI;\n";
	}

	public static String getImports(String color, boolean main) {
		String cadena = "";
		if (color != null) {
			cadena += "import java.awt.Color; \n";
		}
		cadena += "import java.awt.BorderLayout; \n"
				+ "import java.awt.FlowLayout; \n"
				+ "import java.awt.GridLayout; \n"
				//+ "import java.awt.event.ActionEvent; \n"
				//+ "import java.awt.event.ActionListener; \n"
				//+ "import javax.swing.JButton; \n"
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
				break;
			}
		}
		return cadena;
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
			String cadena = "		public "
					+ aptClassName
					+ "(ArrayList<String> labels, int width, ArrayList<String> tips) {\n"
					+ "		    super(new BorderLayout());\n"
					+ "			JPanel labelPanel = new JPanel(new GridLayout(labels.size(), 1));\n"
					+ "			JPanel fieldPanel = new JPanel(new GridLayout(labels.size(), 1));\n\n";
			if (color != null) {
				cadena += "			labelPanel.setBackground(Color." + color + ");\n";
			}
			cadena += "			add(labelPanel, BorderLayout.WEST);\n"
					+ "			add(fieldPanel, BorderLayout.CENTER);\n"
					+ "			fields = new JTextField[labels.size()];\n\n"
					+ "			for (int i = 0; i < labels.size(); i += 1) {\n"
					+ "				fields[i] = new JTextField();\n"
					+ "				if (i < tips.size())\n"
					+ "					fields[i].setToolTipText(tips.get(i));\n"
					+ "				fields[i].setColumns(width);\n\n"
					+ "				JLabel lab = new JLabel(labels.get(i), JLabel.RIGHT);\n"
					+ "				lab.setLabelFor(fields[i]);\n"
					+ "				labelPanel.add(lab);\n"
					+ "				JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));\n"
					+ "				p.add(fields[i]);\n" + "				fieldPanel.add(p);\n"
					+ "			}\n" + "		}\n";
			return cadena;
		}
	}

	public static String getConstructorGenerico(String aptClassName,
			String color, ArrayList<String> lNombres, ArrayList<String> lTipos) {
		String cadena = "		public "
				+ aptClassName
				+ "(ArrayList<String> labels, int width, ArrayList<String> tips) {\n"
				+ "		    super(new BorderLayout());\n"
				+ "			JPanel labelPanel = new JPanel(new GridLayout(labels.size(), 1));\n"
				+ "			JPanel fieldPanel = new JPanel(new GridLayout(labels.size(), 1));\n\n"
				+ " 		labelPanel.setPreferredSize(new Dimension(100, 300));\n"
				+ " 		fieldPanel.setPreferredSize(new Dimension(300, 300));\n\n";
		if (color != null) {
			cadena += "			labelPanel.setBackground(Color." + color + ");\n";
		}

		cadena += "			add(labelPanel, BorderLayout.WEST);\n"
				+ "			add(fieldPanel, BorderLayout.CENTER);\n";

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
						+ "	 	fieldPanel.add(p);\n";
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
						+ "	 	fieldPanel.add(p);\n";
				break;
			case "java.lang.String[]": // Tipo String[], JComboBox<String>
				// Insertar los valores #Completar
				cadena += "		" + lNombres.get(i) + ".addItem(\"uno\");\n";
				cadena += "		" + lNombres.get(i) + ".addItem(\"dos\");\n";
				cadena += "		" + lNombres.get(i) + ".addItem(\"tres\");\n";
				cadena += "		" + lNombres.get(i)
						+ ".setToolTipText(\"tips\");\n";
				cadena += "		" + lNombres.get(i) + "Label.setLabelFor("
						+ lNombres.get(i) + ");\n";
				cadena += "		labelPanel.add(" + lNombres.get(i) + "Label);\n\n";
				cadena += "		p = new JPanel(new FlowLayout(FlowLayout.LEFT));\n"
						+ "		p.add("
						+ lNombres.get(i)
						+ ");\n"
						+ "	 	fieldPanel.add(p);\n";
				break;
			default: // Tipo Array/class/enum, JList
				cadena += "		" + lNombres.get(i) + "Label.setLabelFor("
						+ lNombres.get(i) + ");\n";
				cadena += "		labelPanel.add(" + lNombres.get(i) + "Label);\n\n";
				cadena += "		p = new JPanel(new FlowLayout(FlowLayout.LEFT));\n";
				cadena += "  	" + lNombres.get(i) + ".setSelectedIndex(1);\n"	;	
				cadena += "		p.add(" + lNombres.get(i) + "Scroll);\n";
				cadena += "	 	fieldPanel.add(p);\n";
				break;
			}
		}
		cadena += "		}\n";
		return cadena;
	}

	public static String getMethod() {
		return "	public String getText(int i) {\n"
				+ "			return (fields[i].getText());\n" + "		}\n";
	}

	/*
	 * public static String getMain(String aptClassName, String labels, String
	 * descs, int width) { return "	public static void main(String[] args) {\n"
	 * + "		ArrayList<String> labels = new ArrayList<String>();\n" +
	 * "		ArrayList<String> descs = new ArrayList<String>();\n" +
	 * "		int width = " + width + ";\n" + labels + descs + " 		final " +
	 * aptClassName + " form = new " + aptClassName +
	 * "(labels, width, descs);\n\n" +
	 * "	    JButton submit = new JButton(\"Enviar\");\n\n" +
	 * " 		submit.addActionListener(new ActionListener() {\n" +
	 * "			public void actionPerformed(ActionEvent e) {\n" +
	 * " 			//pendiente de implementar\n" + " 		}\n" + " 	});\n" +
	 * "//Parte ha implementar en la otra clase\n" +
	 * "JFrame f = new JFrame(\"Text Form Example\");\n" +
	 * "f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);\n" +
	 * "f.getContentPane().add(form, BorderLayout.NORTH);\n" +
	 * "JPanel p = new JPanel();\n" + "p.add(submit);\n" +
	 * "f.getContentPane().add(p, BorderLayout.SOUTH);\n" + "f.pack();\n" +
	 * "f.setVisible(true);\n" + " }\n" + "}\n"; }
	 */
}
