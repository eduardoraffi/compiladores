package compilador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;

public class InitialTests extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Variaveis
	private String mFilePath;
	private static final InitialTests mJFrame = new InitialTests();
	@SuppressWarnings("unused")
	private SyntaxAnalyzer mSyntaxAnalyzer = null;
	private FileWriter mTempFileWriter;

	public InitialTests() {
		initComponents();

	}

	@SuppressWarnings("deprecation")
	private void initComponents() {

		seletorDeArquivos = new javax.swing.JFileChooser();
		botaoCompilar = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		editorTexto = new javax.swing.JTextArea();
		jScrollPane2 = new javax.swing.JScrollPane();
		saidaTexto = new javax.swing.JTextArea();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		barraDeMenu = new javax.swing.JMenuBar();
		menu = new javax.swing.JMenu();
		fileChooser = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		botaoCompilar.setFont(new java.awt.Font("DialogInput", 1, 14));
		botaoCompilar.setText("Compile");
		botaoCompilar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botaoCompilarActionPerformed(evt);
			}
		});

		editorTexto.setColumns(20);
		editorTexto.setFont(new java.awt.Font("Monospaced", 1, 14));
		editorTexto.setRows(5);
		editorTexto.setEnabled(false);
		jScrollPane1.setViewportView(editorTexto);

		saidaTexto.setEditable(false);
		saidaTexto.setColumns(20);
		saidaTexto.setRows(5);
		jScrollPane2.setViewportView(saidaTexto);

		jLabel1.setFont(new java.awt.Font("DialogInput", 1, 18));
		jLabel1.setText("Output");

		jLabel2.setFont(new java.awt.Font("DialogInput", 1, 18));
		jLabel2.setText("Text Editor");

		menu.setText("File");
		menu.setFont(new java.awt.Font("Arial", 1, 12));
		menu.setHideActionText(true);

		fileChooser.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
		fileChooser.setText("Open file");
		fileChooser.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fileChooserActionPerformed(evt);
			}
		});
		menu.add(fileChooser);

		barraDeMenu.add(menu);

		setJMenuBar(barraDeMenu);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 685,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(28, 28, 28).addComponent(jScrollPane2,
										javax.swing.GroupLayout.PREFERRED_SIZE, 685,
										javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup().addGap(321, 321, 321).addComponent(jLabel1))))
						.addGroup(layout.createSequentialGroup().addGap(299, 299, 299).addComponent(botaoCompilar))
						.addGroup(layout.createSequentialGroup().addGap(275, 275, 275).addComponent(jLabel2)))
				.addContainerGap(27, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup().addContainerGap(19, Short.MAX_VALUE).addComponent(jLabel2)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel1)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18).addComponent(botaoCompilar).addContainerGap()));

		pack();
	}

	private void fileChooserActionPerformed(java.awt.event.ActionEvent evt) {
		int returnVal = seletorDeArquivos.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = seletorDeArquivos.getSelectedFile();
			try {
				System.out.println(file.getAbsolutePath());
				this.mFilePath = file.getAbsolutePath();
				editorTexto.setEnabled(true);
				leitorDeArquivo(file);
			} catch (Exception ex) {
				System.out.println("problem accessing file" + file.getAbsolutePath());
			}
		} else {
			System.out.println("File access cancelled by user.");
		}

	}

	private void botaoCompilarActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			mTempFileWriter = new FileWriter(mFilePath);
			mTempFileWriter.write(editorTexto.getText());
			mTempFileWriter.close();
		} catch (IOException ex) {
			Logger.getLogger(InitialTests.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.getProperty("user.dir");
			mSyntaxAnalyzer = new SyntaxAnalyzer(mFilePath);
			saidaTexto.append("Sucesso!\n");
			saidaTexto.setEnabled(true);
			saidaTexto.setEditable(false);
		} catch (Exception e) {
			saidaTexto.append("" + e.getMessage() + "\n");
			e.printStackTrace();
			System.out.println("" + e.getMessage());
		}
	}

	public static void main(String args[]) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(InitialTests.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(InitialTests.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(InitialTests.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(InitialTests.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				mJFrame.setVisible(true);
				mJFrame.setLocationRelativeTo(null);
				mJFrame.setTitle("Compilador");
				mJFrame.setResizable(false);
			}
		});
	}

	private javax.swing.JMenuBar barraDeMenu;
	private javax.swing.JButton botaoCompilar;
	private javax.swing.JTextArea editorTexto;
	private javax.swing.JMenuItem fileChooser;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JMenu menu;
	private javax.swing.JTextArea saidaTexto;
	private javax.swing.JFileChooser seletorDeArquivos;

	public void leitorDeArquivo(File arquivo) {
		FileInputStream fileStream = null;
		String texto = "";

		try {
			fileStream = new FileInputStream(arquivo);
			int conteudo;
			while ((conteudo = fileStream.read()) != -1) {
				texto += (char) conteudo;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileStream != null) {
					fileStream.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		editorTexto.setText(texto);
	}
}
