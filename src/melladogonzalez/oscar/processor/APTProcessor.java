package melladogonzalez.oscar.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import javax.lang.model.SourceVersion;
import javax.tools.JavaFileObject;

import melladogonzalez.oscar.annotation.*;

@SupportedAnnotationTypes(value = { "*" })
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class APTProcessor extends AbstractProcessor {

	private Filer filer;
	private Messager messager;
	private String labels = "";
	private ArrayList<String> descs = new ArrayList<String>();
	private String tituloForm = "GUI";
	private ArrayList<String> lValorHidden;
	private String nombreClase = "";
	private String colorBackground = null;
	private ArrayList<String> lTipos = new ArrayList<String>();
	private int min, max;
	private String RangeMethod = " /* Metodos VALUES */\n";
	private String CallRangeMethod = "";
	private String RequiredMethod = " /* Metodos Required */\n";
	private String CallRequiredMethod = "";
	private ArrayList<String> lDatosComboBox;

	@Override
	public void init(ProcessingEnvironment env) {
		filer = env.getFiler();
		messager = env.getMessager();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.annotation.processing.AbstractProcessor#process(java.util.Set,
	 * javax.annotation.processing.RoundEnvironment)
	 */
	@Override
	public boolean process(Set<? extends TypeElement> elements,
			RoundEnvironment env) {
		RangeMethod = "";
		CallRangeMethod = "";
		RequiredMethod = "";
		CallRequiredMethod = "";

		lDatosComboBox = new ArrayList<String>();
		lValorHidden = new ArrayList<String>();
		lValorHidden.clear();
		String a = "a: ";
		String b = "b: ";
		String c = "c: ";
		String d = "d: ";
		String e = "e: ";
		String f = "f: ";
		String g = "g: ";

		/*
		 * Procesamiento de anotaciones tipo HIDDEN
		 */
		try {
			for (Element element : env.getElementsAnnotatedWith(Hidden.class)) {
				// tipoAnno = "hidden";
				if (element.getKind() == ElementKind.FIELD)
					lValorHidden.add(element.getSimpleName().toString());
				else
					messager.printMessage(
							Kind.ERROR,
							"La anotacion HIDDEN solo puede usarse en atributos",
							element);
				// writeClass("GuiHidden_" + element.getSimpleName(),
				// "/* \n valorHidden: " + lValorHidden.toString() + "\n" + e
				// + "\n" + f + "\n" + g + "\n*/");
			}
		} catch (Exception exc) {
			writeClass("Exception en HIDDEN", exc.toString());
		}
		/*
		 * Procesamiento de anotaciones tipo REQUIRED
		 */
		try {
			for (Element element : env.getElementsAnnotatedWith(Required.class)) {
				// tipoAnno = "required";

				ArrayList<String> lAnnoTypesRequired = new ArrayList<String>();
				ArrayList<String> lAnnoValuesRequired = new ArrayList<String>();
				getAnnoValue(element, lAnnoTypesRequired, lAnnoValuesRequired);

				if (element.getKind() == ElementKind.FIELD) {
					f += "Field |" + element.getSimpleName() + "|\n";
					f += "TYPE |" + element.asType().toString() + "|\n";
					if (element.asType().toString().equals("int")
							|| element.asType().toString()
									.equals("java.lang.String")) {
						RequiredMethod += "	public void "
								+ element.getSimpleName()
								+ "Required(){\n"
								+ "		if ("
								+ element.getSimpleName()
								+ ".getText().isEmpty())\n"
								+ "			JOptionPane.showMessageDialog(null,\"El campo "
								+ element.getSimpleName()
								+ " es obligatorio y no puede estar vacío \");\n"
								+ "	}\n\n";
						CallRequiredMethod += "				form."
								+ element.getSimpleName() + "Required();\n";
					} else
						messager.printMessage(
								Kind.ERROR,
								"La anotacion REQUIRED solo puede usarse en tipo de dato int o String",
								element);
				} else
					messager.printMessage(
							Kind.ERROR,
							"La anotacion REQUIRED solo puede usarse en atributos",
							element);

				// writeClass("GuiRequired_" + element.getSimpleName(), "/*"
				// + lAnnoValuesRequired.toString() + e + "\n" + f + "\n" + g
				// + "\n*/");
			}
		} catch (Exception exc) {
			writeClass("Exception en REQUIRED", exc.toString());
		}

		/*
		 * Procesamiento de anotaciones tipo COMBOBOX
		 */
		try {
			for (Element element : env.getElementsAnnotatedWith(ComboBox.class)) {
				ArrayList<String> lAnnoTypesCombo = new ArrayList<String>();
				ArrayList<String> lAnnoValuesCombo = new ArrayList<String>();
				getAnnoValue(element, lAnnoTypesCombo, lAnnoValuesCombo);

				if (element.getKind() == ElementKind.FIELD) {
					if (element.asType().toString()
							.equals("java.lang.String[]")) {
						String valueComboAux = "";
						String valueComboDatos = "		"
								+ element.getSimpleName().toString()
								+ ".addItem(\"  \");\n";
						;
						String valueComboSplit[];
						for (String valueCombo : lAnnoValuesCombo) {
							valueComboAux = valueCombo.substring(1,
									valueCombo.length() - 1);
							valueComboSplit = valueComboAux.split(",");
							f += valueComboSplit.toString();
							for (int i = 0; i < valueComboSplit.length; i++) {
								valueComboDatos += "		"
										+ element.getSimpleName().toString()
										+ ".addItem(\"" + valueComboSplit[i]
										+ "\");\n";
							}
							lDatosComboBox.add(valueComboDatos);
						}
						e += element.getSimpleName().toString() + "\n 2";
						e += element.getEnclosingElement().toString() + "\n 3";
						e += element.getEnclosedElements().toString() + "\n";
						g += lDatosComboBox.toString();
					} else
						messager.printMessage(
								Kind.ERROR,
								"La anotacion COMBOBOX solo puede usarse en tipo String[]",
								element);
				} else
					messager.printMessage(
							Kind.ERROR,
							"La anotacion COMBOBOX solo puede usarse en atributos",
							element);
				// writeClass("GuiComboBox_" + element.getSimpleName(), "/*"
				// + lAnnoValuesCombo.toString() + "\n\n" +
				// lAnnoTypesCombo.toString() + "\n\n"
				// + e + "\n" + f + "\n" + g
				// + "\n*/");
			}
		} catch (Exception exc) {
			writeClass("Exception en COMBOBOX", exc.toString());
		}

		/*
		 * Procesamiento de anotaciones tipo VALUES
		 */
		try {
			for (Element element : env.getElementsAnnotatedWith(Values.class)) {

				ArrayList<String> lAnnoTypesValues = new ArrayList<String>();
				ArrayList<String> lAnnoValuesValues = new ArrayList<String>();
				getAnnoValue(element, lAnnoTypesValues, lAnnoValuesValues);

				if (element.getKind() == ElementKind.FIELD) {
					try {
						f += "Field |" + element.getSimpleName() + "|\n";
						f += "TYPE |" + element.asType().toString() + "|\n";

						if (element.asType().toString().equals("int")) {
							for (int j = 0; j < lAnnoTypesValues.size(); j++) {
								switch (lAnnoTypesValues.get(j)) {
								case "min":
									try {
										min = Integer
												.parseInt(lAnnoValuesValues
														.get(j));
									} catch (Exception ex) {
										messager.printMessage(
												Kind.ERROR,
												"La anotacion VALUES solo puede contener numeros",
												element);
									}
									break;
								case "max":
									try {
										max = Integer
												.parseInt(lAnnoValuesValues
														.get(j));
									} catch (Exception ex) {
										RangeMethod += "MAX EXC" + ex + "\n";
										messager.printMessage(
												Kind.ERROR,
												"La anotacion VALUES solo puede contener numeros",
												element);
									}
									break;
								default:
									break;
								}
							}
							f += "tipo |" + element.asType().toString()
									+ "|\n\n";
							RangeMethod += "	public void "
									+ element.getSimpleName()
									+ "Range(){\n"
									+ "		try {\n"
									+ "			if (Integer.parseInt("
									+ element.getSimpleName()
									+ ".getText()) < "
									+ min
									+ " || Integer.parseInt("
									+ element.getSimpleName()
									+ ".getText()) > "
									+ max
									+ ")\n"
									+ "				JOptionPane.showMessageDialog(null,\" El campo "
									+ element.getSimpleName()
									+ " está fuera del rango "
									+ min
									+ " - "
									+ max
									+ " \");\n"
									+ "		} catch (Exception ex) {\n"
									+ "			JOptionPane.showMessageDialog(null, \"El campo "
									+ element.getSimpleName()
									+ " sólo se puede contener números\");\n"
									+ "		}\n" + "	}\n\n";
							CallRangeMethod += "				form."
									+ element.getSimpleName() + "Range();\n";
						} else {
							messager.printMessage(
									Kind.ERROR,
									"La anotacion VALUES solo puede usarse en tipo INT",
									element);
						}
					} catch (Exception ex) {
					}
				} else
					messager.printMessage(
							Kind.ERROR,
							"La anotacion VALUES solo puede usarse en atributos",
							element);

				// writeClass("GuiValues_" + element.getSimpleName(), "/* a: " +
				// a + "\nTYPES" + lAnnoTypesValues.toString() + "\n"
				// + lAnnoValuesValues.toString()+ "\n min:   " + min +
				// "\n max:   " + max + "\n" + e + "\n" + f + "\n" + g +
				// "\n\n method" +
				// RangeMethod + "\n*/");
			}
		} catch (Exception exc) {
			writeClass("Exception en VALUES", exc.toString());
		}

		/*
		 * Procesamiento de anotaciones tipo FORM
		 */
		try {
			for (Element element : env.getElementsAnnotatedWith(Form.class)) {
				a += element.toString();
				nombreClase = element.toString().substring(22);
				a += "\n" + nombreClase + "\n";

				ArrayList<String> lAnnoTypesForm = new ArrayList<String>();
				ArrayList<String> lAnnoValuesForm = new ArrayList<String>();
				getAnnoValue(element, lAnnoTypesForm, lAnnoValuesForm);
				if (element.getKind() == ElementKind.CLASS) {
					for (int i = 0; i < lAnnoTypesForm.size(); i++) {
						switch (lAnnoTypesForm.get(i)) {
						case "name":
							tituloForm = lAnnoValuesForm.get(i);
							break;
						case "background":
							colorBackground = lAnnoValuesForm.get(i)
									.toUpperCase();
							break;
						default:
							break;
						}
					}
					b += lAnnoValuesForm.toString() + "\n\n";
					c += element.getSimpleName();
					d += element.getEnclosedElements().toString();

					List<? extends Element> aux = element.getEnclosedElements();
					for (int i = 0; i < aux.size(); i++) {
						if (aux.get(i).getKind() == ElementKind.METHOD) {
							e += "method |" + aux.get(i).getSimpleName()
									+ "|\n";
						} else if (aux.get(i).getKind() == ElementKind.FIELD) {
							try {
								f += "Field |" + aux.get(i).getSimpleName()
										+ "|\n";
								f += "tipo |" + aux.get(i).asType().toString()
										+ "|\n\n";

								if (!lValorHidden.isEmpty())
									f += "lvalor |" + lValorHidden.get(0)
											+ "|\n";
								f += "condicion "
										+ lValorHidden.contains(aux.get(i)
												.getSimpleName().toString())
										+ "\n";
								if (!lValorHidden.contains(aux.get(i)
										.getSimpleName().toString())) {
									labels += " 		labels.add(\""
											+ aux.get(i).getSimpleName()
											+ "\");\n";
									descs.add(aux.get(i).getSimpleName()
											.toString());
									// Se añaden los tipos de los atributos
									// anotados
									lTipos.add(aux.get(i).asType().toString());
								} else {
									f += "\tEs hidden\t";
								}
							} catch (Exception ex) {
								writeClass("Exception", ex.toString());
							}
						} else {
							g += aux.get(i).getKind().toString() + "\t"
									+ aux.get(i).getSimpleName() + "\n";
						}
					}
					ElementKind kind = element.getKind();

					// FormAnnotation solo puede anotar clases
					if (kind == ElementKind.CLASS) {
						a = a + "Es una clase\n";
						String aptClassName = "Gui" + element.getSimpleName();
						String aptClassContent = "/*\n" + a + "\n   " + b
								+ "\n   " + c + "\n   " + d + "\n   " + e
								+ "\n   " + f + "\n   " + g + "\nhidden: "
								+ lValorHidden.toString() + "\nvalorAnno"
								+ lAnnoValuesForm.toString() + "\n*/";

						writeClass(aptClassName + "JPanel", aptClassContent
								+ getCodeClassJPanel(aptClassName));
						writeClass(aptClassName, aptClassContent
								+ getCodeClassJFrame(aptClassName));
					} else {
						a += "NO ES UNA CLASE \n";

						messager.printMessage(
								Kind.ERROR,
								"Esta anotacion solo puede usarse en una clase",
								element);
					}
				} else
					messager.printMessage(Kind.ERROR,
							"La anotacion FORM solo puede usarse en una clase",
							element);
			}
		} catch (Exception exc) {
			writeClass("Exception en FORM", exc.toString());
		}
		return true;
	}

	public boolean writeClass(String className, String classContent) {
		JavaFileObject file = null;
		try {
			file = filer.createSourceFile("GUI/" + className);
			file.openWriter().append(classContent).close();
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public String getCodeClassJPanel(String aptClassName) {
		String aptClassNameAux = aptClassName + "JPanel";
		String cadena = GeneradorJPanels.getPackage()
				+ GeneradorJPanels.getImports(colorBackground)
				+ GeneradorJPanels.getClass(aptClassNameAux)
				+ GeneradorJPanels.getAttributes(descs, lTipos)
				// + GeneradorJPanels.getConstructor(aptClassName,
				// colorBackground, main)
				+ GeneradorJPanels.getConstructorGenerico(aptClassNameAux,
						colorBackground, descs, lTipos, lDatosComboBox)
				// + GeneradorJPanels.getMethod()
				+ RequiredMethod + RangeMethod + "}\n";
		return cadena;
	}

	public String getCodeClassJFrame(String aptClassName) {
		String aptClassNameAux = aptClassName + "JPanel";
		return GeneradorJFrames.getPackage()
				+ GeneradorJFrames.getImports()
				+ GeneradorJFrames.getClass(aptClassName)
				+ GeneradorJFrames.getConstructor(aptClassName,
						aptClassNameAux, CallRequiredMethod + CallRangeMethod)
				+ GeneradorJFrames
						.getMain(aptClassName, tituloForm, labels, 15);
	}

	public void getAnnoValue(Element element, ArrayList<String> lTypes,
			ArrayList<String> lValues) {

		for (AnnotationMirror annoMirror : element.getAnnotationMirrors()) {
			Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annoMirror
					.getElementValues();
			for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues
					.entrySet()) {
				// if (tipoAnno
				// .contains(entry.getKey().getSimpleName().toString()))
				lTypes.add(entry.getKey().getSimpleName().toString());
				lValues.add(entry.getValue().toString());
			}
		}
	}
}