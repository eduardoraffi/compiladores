package virtual_machine;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Interface extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	private static final Interface mInterface = new Interface();
	private String mFileUrl;
	DefaultTableModel mDefaultTableModelStack;
	DefaultTableModel mDefaultTableModelInstruction;
	VMStack mStack = new VMStack();
	private int firstClick = 0;
	private InstructionProcessor processador;
	private ArrayList<String> mBreakPoint = new ArrayList<String>();
	private int numBreaks = 0;
	private boolean runWithBreakPoint = false;

	// Construtor da classe
	public Interface() {
		this.mFileUrl = null;
		initComponents();
		mDefaultTableModelStack = (DefaultTableModel) tabelaPilha.getModel();
		mDefaultTableModelInstruction = (DefaultTableModel) tabelaInstrucao.getModel();
		textoBreak.setText("Digite as linhas de Break Point separadas por espaço.");
		textoBreak.setLineWrap(true);
		textoSaida.setLineWrap(true);
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {

		seletorDeArquivo = new javax.swing.JFileChooser();
		jScrollPane1 = new javax.swing.JScrollPane();
		tabelaInstrucao = new javax.swing.JTable();
		jScrollPane2 = new javax.swing.JScrollPane();
		tabelaPilha = new javax.swing.JTable();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jScrollPane3 = new javax.swing.JScrollPane();
		textoSaida = new javax.swing.JTextArea();
		jLabel5 = new javax.swing.JLabel();
		botaoBreak = new javax.swing.JButton();
		botaoContinuar = new javax.swing.JButton();
		jScrollPane4 = new javax.swing.JScrollPane();
		textoBreak = new javax.swing.JTextArea();
		jLabel6 = new javax.swing.JLabel();
		BarraDeMenu = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		BotaoAbrirArqv = new javax.swing.JMenuItem();
		BotaoExecutarArqv = new javax.swing.JMenuItem();

		seletorDeArquivo.setFileFilter(new FiltroDeArquivo());

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		tabelaInstrucao.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Line", "Instruction", "Attr 1", "Attr 2" }) {
			private static final long serialVersionUID = 1L;
			Class[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class,
					java.lang.String.class };
			boolean[] canEdit = new boolean[] { false, false, false, false };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		tabelaInstrucao.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		jScrollPane1.setViewportView(tabelaInstrucao);
		if (tabelaInstrucao.getColumnModel().getColumnCount() > 0) {
			tabelaInstrucao.getColumnModel().getColumn(0).setResizable(false);
			tabelaInstrucao.getColumnModel().getColumn(1).setResizable(false);
			tabelaInstrucao.getColumnModel().getColumn(2).setResizable(false);
			tabelaInstrucao.getColumnModel().getColumn(3).setResizable(false);
		}

		tabelaPilha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		tabelaPilha.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Endereço", "Valor" }) {
		
			private static final long serialVersionUID = 1L;
			Class[] types = new Class[] { java.lang.Integer.class, java.lang.Integer.class };
			boolean[] canEdit = new boolean[] { false, false };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		tabelaPilha.getTableHeader().setReorderingAllowed(false);
		jScrollPane2.setViewportView(tabelaPilha);
		if (tabelaPilha.getColumnModel().getColumnCount() > 0) {
			tabelaPilha.getColumnModel().getColumn(0).setResizable(false);
			tabelaPilha.getColumnModel().getColumn(1).setResizable(false);
		}

		jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
		jLabel1.setText("Pilha");

		jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
		jLabel2.setText("Instruções");

		textoSaida.setEditable(false);
		textoSaida.setColumns(20);
		textoSaida.setRows(5);
		textoSaida.setWrapStyleWord(true);
		jScrollPane3.setViewportView(textoSaida);

		jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
		jLabel5.setText("Break Point's");

		botaoBreak.setText("Executar c/ Break");
		botaoBreak.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botaoBreakActionPerformed(evt);
			}
		});

		botaoContinuar.setText("Executar");
		botaoContinuar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botaoContinuarActionPerformed(evt);
			}
		});

		textoBreak.setColumns(20);
		textoBreak.setRows(5);
		textoBreak.setWrapStyleWord(true);
		textoBreak.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				textoBreakMouseClicked(evt);
			}
		});
		jScrollPane4.setViewportView(textoBreak);

		jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
		jLabel6.setText("Saida");

		jMenu1.setText("Arquivo");
		jMenu1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenu1ActionPerformed(evt);
			}
		});

		BotaoAbrirArqv.setText("Abrir arquivo");
		BotaoAbrirArqv.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				BotaoAbrirArqvActionPerformed(evt);
			}
		});
		jMenu1.add(BotaoAbrirArqv);

		BotaoExecutarArqv.setText("Executar arquivo");
		BotaoExecutarArqv.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				BotaoExecutarArqvActionPerformed(evt);
			}
		});
		jMenu1.add(BotaoExecutarArqv);

		BarraDeMenu.add(jMenu1);

		setJMenuBar(BarraDeMenu);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGap(31, 31, 31)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 628,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(layout.createSequentialGroup().addGap(267, 267, 267).addComponent(
												jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 116,
												javax.swing.GroupLayout.PREFERRED_SIZE))))
						.addGroup(layout.createSequentialGroup().addGap(43, 43, 43)
								.addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(botaoBreak, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(botaoContinuar, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(3, 3, 3))
						.addGroup(layout.createSequentialGroup().addGap(104, 104, 104).addComponent(jLabel5)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jLabel6).addGap(100, 100, 100)))
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGap(82, 82, 82).addComponent(jLabel1,
								javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup().addGap(24, 24, 24).addComponent(jScrollPane2,
								javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(14, 14, 14)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 15,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel2))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addGroup(layout
						.createSequentialGroup()
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addComponent(jLabel5)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup().addComponent(jLabel6)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addGroup(layout.createSequentialGroup().addComponent(botaoBreak)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(botaoContinuar))
												.addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)))))
						.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))
				.addContainerGap(50, Short.MAX_VALUE)));

		pack();
	}

	private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {

	}

	private void BotaoAbrirArqvActionPerformed(java.awt.event.ActionEvent evt) {
		// Abrir seletor de Arquivo.
		seletorDeArquivo.setVisible(true);
		int returnVal = seletorDeArquivo.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = seletorDeArquivo.getSelectedFile();
			try {
				System.out.println(file.getAbsolutePath());
				this.mFileUrl = file.getAbsolutePath();
			} catch (Exception ex) {
				System.out.println("problem accessing file" + file.getAbsolutePath());
			}
			try {
				inicializarTabelaArquivo();
			} catch (FileNotFoundException ex) {
				Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			System.out.println("File access cancelled by user.");
		}
	}

	private void botaoBreakActionPerformed(java.awt.event.ActionEvent evt) {
		if (mFileUrl != null) {
			if (numBreaks == 0) {
				setBreaks();
				textoBreak.setEditable(false);
				botaoContinuar.setEnabled(false);
				BotaoExecutarArqv.setEnabled(false);
			}
			while (!processador.isStackReachEnd()) {
				if (runWithBreakPoint)
					break;
				processador.runInstruction();
				tabelaInstrucao.setSelectionBackground(Color.lightGray);
				tabelaInstrucao.setRowSelectionInterval(processador.mInstruction - 1, processador.mInstruction - 1);
				mStack = processador.getStack();
				zerarTabPilha();
				preencherTabPilha(mStack.tamPilha());
				exibirSaida();
				for (int a = 0; a < numBreaks; a++) {
					if (processador.mInstruction + 1 == Integer.parseInt(mBreakPoint.get(a))) {
						runWithBreakPoint = true;
					}
				}
			}
			runWithBreakPoint = false;
			if (processador.isStackReachEnd()) {
				botaoBreak.setEnabled(false);
				JOptionPane.showMessageDialog(null, "Compilação chegou ao fim!", "Alerta", JOptionPane.ERROR_MESSAGE);
			}
		} else
			JOptionPane.showMessageDialog(null, "Abra um arquivo fonte antes de compilar", "Erro de Caminho",
					JOptionPane.ERROR_MESSAGE);
	}

	private void botaoContinuarActionPerformed(java.awt.event.ActionEvent evt) {
		if (mFileUrl != null) {
			textoBreak.setEditable(false);
			if (!processador.isStackReachEnd()) {
				botaoBreak.setEnabled(false);
				processador.runInstruction();
				tabelaInstrucao.setSelectionBackground(Color.lightGray);
				tabelaInstrucao.setRowSelectionInterval(processador.mInstruction - 1, processador.mInstruction - 1);
				mStack = processador.getStack();
				zerarTabPilha();
				preencherTabPilha(mStack.tamPilha());
				exibirSaida();
			} else {
				botaoContinuar.setEnabled(false);
				botaoBreak.setEnabled(false);
				BotaoExecutarArqv.setEnabled(false);
				JOptionPane.showMessageDialog(null, "Compilação chegou ao fim!", "Alerta", JOptionPane.ERROR_MESSAGE);
			}
		} else
			JOptionPane.showMessageDialog(null, "Abra um arquivo fonte antes de compilar", "Erro de Caminho",
					JOptionPane.ERROR_MESSAGE);
	}

	private void BotaoExecutarArqvActionPerformed(java.awt.event.ActionEvent evt) {
		if (mFileUrl != null) {
			int i = 0;
			textoBreak.setEditable(false);
			while (processador.getInstrucoes().size() > i || !processador.isStackReachEnd()) {
				processador.runInstruction();
				mStack = processador.getStack();
				zerarTabPilha();
				preencherTabPilha(mStack.tamPilha());
				exibirSaida();
				i++;
			}
			botaoContinuar.setEnabled(false);
			botaoBreak.setEnabled(false);
			BotaoExecutarArqv.setEnabled(false);
			JOptionPane.showMessageDialog(null, "Compilação chegou ao fim!", "Alerta", JOptionPane.ERROR_MESSAGE);
		} else
			JOptionPane.showMessageDialog(null, "Abra um arquivo fonte antes de compilar", "Erro de Caminho",
					JOptionPane.ERROR_MESSAGE);
	}

	private void textoBreakMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_textoBreakMouseClicked
		if (firstClick == 0) {
			textoBreak.setText("");
			firstClick = 1;
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
			java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				mInterface.setVisible(true);
				mInterface.setTitle("Maquina Virtual MLR");
				mInterface.setLocationRelativeTo(null);
				mInterface.setResizable(false);
			}
		});
	}

	private javax.swing.JMenuBar BarraDeMenu;
	private javax.swing.JMenuItem BotaoAbrirArqv;
	private javax.swing.JMenuItem BotaoExecutarArqv;
	private javax.swing.JButton botaoBreak;
	private javax.swing.JButton botaoContinuar;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JFileChooser seletorDeArquivo;
	private javax.swing.JTable tabelaInstrucao;
	private javax.swing.JTable tabelaPilha;
	private javax.swing.JTextArea textoBreak;
	private javax.swing.JTextArea textoSaida;

	@SuppressWarnings("resource")
	private void inicializarTabelaArquivo() throws FileNotFoundException {
		FileInputStream abertura = new FileInputStream(mFileUrl);
		new InputStreamReader(abertura);
		mDefaultTableModelInstruction = (DefaultTableModel) tabelaInstrucao.getModel();
		try {
			int i = 0;
			processador = new InstructionProcessor(mFileUrl);
			zerarTabInstrucao();
			zerarTabPilha();
			zerarSaida();
			botaoContinuar.setEnabled(true);
			botaoBreak.setEnabled(true);
			BotaoExecutarArqv.setEnabled(true);
			textoBreak.setEditable(true);
			textoBreak.setText("Digite as linhas de Break Point separadas por espaço.");
			firstClick = 0;
			while (i < processador.getInstrucoes().size()) {
				switch (InstructionTypeAndName.getInstructionType(processador.getInstrucoes().get(i).getInstrucao())) {
				case 0:
					SimpleInstruction simples = (SimpleInstruction) processador.getInstrucoes().get(i);
					mDefaultTableModelInstruction
							.addRow(new String[] { String.valueOf(i + 1), simples.getInstrucao().toString(), "", "" });
					break;
				case 1:
					ComposedInstruction composta = (ComposedInstruction) processador.getInstrucoes().get(i);
					mDefaultTableModelInstruction.addRow(new String[] { String.valueOf(i + 1),
							composta.getInstrucao().toString(), Integer.toString(composta.getParameter()), "" });
					break;
				case 2:
					DoubleComposedInstruction dupla = (DoubleComposedInstruction) processador.getInstrucoes()
							.get(i);
					mDefaultTableModelInstruction
							.addRow(new String[] { String.valueOf(i + 1), dupla.getInstrucao().toString(),
									Integer.toString(dupla.getParameter1()), Integer.toString(dupla.getParameter2()) });
					break;
				}
				i++;
			}
		} catch (Exception ex) {
			Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void setBreaks() {
		int res, i = 0;
		String aux = "", val;
		val = String.valueOf(textoBreak.getText());
		if (val.equals("Digite as linhas de Break Point separadas por espaço.") == false) {
			System.out.println(val);
			while (i < val.length()) {
				res = val.charAt(i);
				i++;
				if (Character.isDigit(res)) {
					aux = aux + (res - 48);
				} else {
					mBreakPoint.add(aux);
					aux = "";
				}
			}
			mBreakPoint.add(aux);
		}
		numBreaks = mBreakPoint.size();
	}

	public void zerarTabPilha() {
		if (mDefaultTableModelStack.getRowCount() > 0) {
			int rows = mDefaultTableModelStack.getRowCount();
			for (int a = rows; a > 0; a--) {
				mDefaultTableModelStack.removeRow(a - 1);
			}
		}
	}

	public void preencherTabPilha(int tam) {
		if (tam > 0) {
			for (int a = tam; a > 0; a--) {
				mDefaultTableModelStack.addRow(new Integer[] { mStack.getEnd(a), mStack.getValue(a) });
			}
		}
	}

	public void zerarTabInstrucao() {
		if (mDefaultTableModelInstruction.getRowCount() > 0) {
			int rows = mDefaultTableModelInstruction.getRowCount();
			for (int a = rows; a > 0; a--) {
				mDefaultTableModelInstruction.removeRow(a - 1);
			}
		}
	}

	public void exibirSaida() {
		if (processador.getOutput() != null) {
			textoSaida.append(processador.getOutput() + "\n");
			processador.setOutput();
		}
	}

	public void zerarSaida() {
		textoSaida.setText("");
	}
}

class FiltroDeArquivo extends javax.swing.filechooser.FileFilter {
	@Override
	public boolean accept(File file) {
		return file.isDirectory() || file.getAbsolutePath().endsWith(".obj");
	}

	@Override
	public String getDescription() {
		return "MV File(*.obj)";
	}
}
