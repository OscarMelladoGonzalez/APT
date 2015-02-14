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
import javax.swing.JOptionPane;
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
	private boolean main = false;
	private String tituloForm = "GUI";
	private ArrayList<String> lValorHidden;
	private String nombreClase = "";
	private String colorBackground = null;
	private ArrayList<String> lTipos = new ArrayList<String>();
	private int min, max;
	private String RangeMethod = "";
	private String CallRangeMethod = "";

	@Override
	public void init(ProcessingEnvironment env) {
		filer = env.getFiler();
		messager = env.getMessager();
	}

	/* (non-Javadoc)
	 * @see javax.annotation.processing.AbstractProcessor#process(java.util.Set, javax.annotation.processing.RoundEnvironment)
	 */
	@Override
	public boolean process(Set<? extends TypeElement> elements,
			RoundEnvironment env) {
		RangeMethod = "";
		CallRangeMethod = "";
		
		lValorHidden = new ArrayList<String>();
		lValorHidden.clear();
		String a = "a: ";
		String b = "b: ";
		String c = "c: ";
		String d = "d: ";
		String e = "e: ";
		String f = "f: ";
		String g = "g: ";
		try{
		/*
		 * Procesamiento de anotaciones tipo HIDDEN
		 */
		for (Element element : env.getElementsAnnotatedWith(Hidden.class)) {
			// tipoAnno = "hidden";
			lValorHidden.add(element.getSimpleName().toString());

			writeClass("GuiHidden_" + element.getSimpleName(),
					"/* \n valorHidden: " + lValorHidden.toString() + "\n" + e
							+ "\n" + f + "\n" + g + "\n*/");
		}
		/*
		 * Procesamiento de anotaciones tipo REQUIRED
		 */
		for (Element element : env.getElementsAnnotatedWith(Required.class)) {
			// tipoAnno = "required";

			ArrayList<String> lAnnoTypesRequired = new ArrayList<String>();
			ArrayList<String> lAnnoValuesRequired = new ArrayList<String>();
			getAnnoValue(element, lAnnoTypesRequired, lAnnoValuesRequired);

			writeClass("GuiRequired_" + element.getSimpleName(), "/*"
					+ lAnnoValuesRequired.toString() + e + "\n" + f + "\n" + g
					+ "\n*/");
		}
		/*
		 * Procesamiento de anotaciones tipo VALUES
		 */
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
									min = Integer.parseInt(lAnnoValuesValues.get(j));
								} catch (Exception ex) {
									messager.printMessage(
											Kind.ERROR,
											"Esta anotacion solo puede contener numeros",
											element);
								}
								break;
							case "max":
								try {
									max = Integer.parseInt(lAnnoValuesValues.get(j));
								} catch (Exception ex) {
									RangeMethod += "MAX EXC" + ex + "\n";
									messager.printMessage(
											Kind.ERROR,
											"Esta anotacion solo puede contener numeros",
											element);
								}
								break;
							default:
								break;
							}
						}
						f += "tipo |" +element.asType().toString()
								+ "|\n\n";
						RangeMethod += 
							"		public void " + element.getSimpleName() + "Range(){\n"
							+ "			try {\n"
							+ "				if (Integer.parseInt(" + element.getSimpleName() + ".getText()) < "
							+ min + " || Integer.parseInt(" + element.getSimpleName() + ".getText()) > " + max + ")\n"
							+ "					JOptionPane.showMessageDialog(null,\"El numero está fuera del rango " + min + " - " + max + " \");\n"
							+ "			} catch (Exception ex) {\n"
							+ "				JOptionPane.showMessageDialog(null, \"Solo se puede insertar numeros\");\n"
							+ "			}\n"
							+ "		}\n\n";
						CallRangeMethod += "					form." + element.getSimpleName()+ "Range();\n";
					}else{
						messager.printMessage(Kind.ERROR,
								"Esta anotacion solo puede usarse en tipo INT",
								element);
					}
				} catch (Exception ex) {}
			}
			
			writeClass("GuiValues_" + element.getSimpleName(), "/* a: " + a + "\nTYPES" + lAnnoTypesValues.toString() + "\n"
					+ lAnnoValuesValues.toString()+ "\n min:   " + min + "\n max:   " + max + "\n" + e + "\n" + f + "\n" + g + "\n\n method" +
					RangeMethod + "\n*/");
		}

		/*
		 * Procesamiento de anotaciones tipo FORM
		 */
		for (Element element : env.getElementsAnnotatedWith(Form.class)) {
			// tipoAnno = "form";
			a += element.toString();
			nombreClase = element.toString().substring(22);
			a += "\n" + nombreClase + "\n";
			// b += element.getAnnotation(FormAnnotation.class);
			ArrayList<String> lAnnoTypesForm = new ArrayList<String>();
			ArrayList<String> lAnnoValuesForm = new ArrayList<String>();
			getAnnoValue(element, lAnnoTypesForm, lAnnoValuesForm);

			for (int i = 0; i < lAnnoTypesForm.size(); i++) {
				switch (lAnnoTypesForm.get(i)) {
				case "main":
					if (lAnnoValuesForm.get(i).equals("true"))
						main = true;
					else
						main = false;
					break;
				case "name":
					tituloForm = lAnnoValuesForm.get(i);
					break;
				case "background":
					colorBackground = lAnnoValuesForm.get(i).toUpperCase();
					// esto se podrá implementar cuando cambie la manera de
					// crear las interfaces graficas
					// cuando haya JPanels y JFrame
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
					e += "method |" + aux.get(i).getSimpleName() + "|\n";
				} else if (aux.get(i).getKind() == ElementKind.FIELD) {
					try {
						f += "Field |" + aux.get(i).getSimpleName() + "|\n";
						f += "tipo |" + aux.get(i).asType().toString()
								+ "|\n\n";

						if (!lValorHidden.isEmpty())
							f += "lvalor |" + lValorHidden.get(0) + "|\n";
						f += "condicion "
								+ lValorHidden.contains(aux.get(i)
										.getSimpleName().toString()) + "\n";
						if (!lValorHidden.contains(aux.get(i).getSimpleName()
								.toString())) {
							labels += " 		labels.add(\""
									+ aux.get(i).getSimpleName() + "\");\n";
							descs.add(aux.get(i).getSimpleName().toString());
							// Se añaden los tipos de los atributos anotados
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
			// Set<Modifier> modifiers = element.getModifiers();

			// FormAnnotation solo puede anotar clases
			if (kind == ElementKind.CLASS) {
				a = a + "Es una clase\n";
				String aptClassName = "Gui" + element.getSimpleName();
				String aptClassContent = "/*\n" + a + "\n   " + b + "\n   " + c
						+ "\n   " + d + "\n   " + e + "\n   " + f + "\n   " + g
						+ "\nhidden: " + lValorHidden.toString()
						+ "\nvalorAnno" + lAnnoValuesForm.toString() + "\n*/";

				writeClass(aptClassName + "JPanel", aptClassContent
						+ getCodeClassJPanel(aptClassName));
				writeClass(aptClassName, aptClassContent
						+ getCodeClassJFrame(aptClassName));
			} else {
				a += "NO ES UNA CLASE \n";

				messager.printMessage(Kind.ERROR,
						"Esta anotacion solo puede usarse en una clase",
						element);
			}
		}
		}catch(Exception exc){
			writeClass("Exception", exc.toString());
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

	/*public String getCodeClass(String aptClassName) {
		
		 * if (main) return "package GUI;\n" + GeneradorCodigo.getImportsMain()
		 * + "public class " + aptClassName + "\n" + "{" + "\n" +
		 * GeneradorCodigo.getAttributes() +
		 * "	public static void main(String[] args)\n" + "	{\n" +
		 * GeneradorCodigo.inicializarJFrame() + etiquetas + "\n" +
		 * GeneradorCodigo.getCodeMain(tituloForm) + "	}\n" +
		 * GeneradorCodigo.funcionesAuxBoton() + "}\n"; else

		 * return "package GUI;\n" + GeneradorCodigo.getImports() +
		 * "public class " + aptClassName + "\n" + "{" + "\n" +
		 * GeneradorCodigo.getAttributes() +
		 * "	public static void main(String[] args)\n" + "	{\n" +
		 * GeneradorCodigo.inicializarJFrame() + etiquetas + "\n" +
		 * GeneradorCodigo.getCodeAux(tituloForm) + "	}\n" +
		 * GeneradorCodigo.funcionesAuxBoton() + "}\n";
		 
	}*/

	public String getCodeClassJPanel(String aptClassName) {
		String aptClassNameAux = aptClassName + "JPanel";
		String cadena = GeneradorJPanels.getPackage()
				+ GeneradorJPanels.getImports(colorBackground, main)
				+ GeneradorJPanels.getClass(aptClassNameAux)
				+ GeneradorJPanels.getAttributes(descs, lTipos)
				// + GeneradorJPanels.getConstructor(aptClassName,
				// colorBackground, main)
				+ GeneradorJPanels.getConstructorGenerico(aptClassNameAux,
						colorBackground, descs, lTipos)
				// + GeneradorJPanels.getMethod()
				+ RangeMethod
				+ "}\n";
		return cadena;
	}

	public String getCodeClassJFrame(String aptClassName) {
		String aptClassNameAux = aptClassName + "JPanel";
		return GeneradorJFrames.getPackage()
				+ GeneradorJFrames.getImports()
				+ GeneradorJFrames.getClass(aptClassName)
				+ GeneradorJFrames
						.getConstructor(aptClassName, aptClassNameAux, CallRangeMethod)
				+ GeneradorJFrames.getMain(aptClassName, tituloForm, labels,
						15);
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

	/*
	 * private boolean containsValue(ArrayList<String> lValores, String cadena)
	 * { if (lValores.isEmpty()) return false; for (String valor : lValores) {
	 * if (cadena.contains(valor)) return true; } return false; }
	 */
}