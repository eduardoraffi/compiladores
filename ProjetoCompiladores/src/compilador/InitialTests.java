package compilador;

//public class InitialTests
//{
//	public static void main(String[] args) 
//	{
//		String path = "/Users/eduardoraffi/Documents/Faculdade/Compiladores/TestesSintatico/";
//		try {
//			SyntaxAnalyzer lexycal = new SyntaxAnalyzer(path + "teste4.txt");
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}


import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Rubens
 */
public class InitialTests extends javax.swing.JFrame {

    /**
     * Creates new form InterfaceCompilador
     */
    
    // Variaveis
    private String urlArquivo;
    private static final InitialTests JANELAC = new InitialTests();
    private SyntaxAnalyzer analisador = null;
    private FileWriter arquivoTemp;
    
    public InitialTests() {
        initComponents();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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

        botaoCompilar.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        botaoCompilar.setText("Compilar");
        botaoCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCompilarActionPerformed(evt);
            }
        });

        editorTexto.setColumns(20);
        editorTexto.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        editorTexto.setRows(5);
        editorTexto.setEnabled(false);
        jScrollPane1.setViewportView(editorTexto);

        saidaTexto.setEditable(false);
        saidaTexto.setColumns(20);
        saidaTexto.setRows(5);
        jScrollPane2.setViewportView(saidaTexto);

        jLabel1.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel1.setText("Saida");

        jLabel2.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel2.setText("Editor de texto");

        menu.setText("Arquivo");
        menu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        menu.setHideActionText(true);

        fileChooser.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        fileChooser.setText("Abrir arquivo");
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
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(321, 321, 321)
                                .addComponent(jLabel1))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(299, 299, 299)
                        .addComponent(botaoCompilar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(275, 275, 275)
                        .addComponent(jLabel2)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botaoCompilar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserActionPerformed
        // TODO add your handling code here:
     
        int returnVal = seletorDeArquivos.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) 
        {
            File file = seletorDeArquivos.getSelectedFile();
            try 
            {
                // What to do with the file, e.g. display it in a TextArea
                System.out.println(file.getAbsolutePath());
                this.urlArquivo = file.getAbsolutePath();
                editorTexto.setEnabled(true);
                leitorDeArquivo(file);
            } 
            catch (Exception ex) 
            {
                System.out.println("problem accessing file"+file.getAbsolutePath());
            }
        } 
        else 
        {
            System.out.println("File access cancelled by user.");
        }
         
    }//GEN-LAST:event_fileChooserActionPerformed

    private void botaoCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCompilarActionPerformed
        try 
        {
            arquivoTemp = new FileWriter(urlArquivo);
            arquivoTemp.write(editorTexto.getText());
            arquivoTemp.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(InitialTests.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        try
        {
            String MVFile;
            javax.swing.JFileChooser a = new javax.swing.JFileChooser();
            a.showDialog(menu, "MVFile");
            a.setFileFilter(new FiltroDeArquivo());
            //inicializar codegen por aqui??
            MVFile = a.getSelectedFile().toString();
            System.getProperty("user.dir");
            analisador = new SyntaxAnalyzer(urlArquivo);
            saidaTexto.append("Sucesso!\n");
            saidaTexto.setEnabled(true);
            saidaTexto.setEditable(false);
        }
        catch(Exception e)
        {
            saidaTexto.append(""+e.getMessage()+"\n");
            e.printStackTrace();
            System.out.println(""+e.getMessage());
        }
    }//GEN-LAST:event_botaoCompilarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InitialTests.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InitialTests.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InitialTests.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InitialTests.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JANELAC.setVisible(true);
                JANELAC.setLocationRelativeTo(null);
                JANELAC.setTitle("Compilador MLR");
                JANELAC.setResizable(false);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    // End of variables declaration//GEN-END:variables

    //Metodos
    
    public void leitorDeArquivo (File arquivo)
    {
        FileInputStream fileStream = null;
        String texto = "";

        try
        {
            fileStream = new FileInputStream(arquivo);
            int conteudo;
            while ((conteudo = fileStream.read()) != -1)
            {
                texto += (char) conteudo;
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally 
        {
            try {
                if (fileStream != null) 
                {
                    fileStream.close();
                }
            } 
            catch (IOException ex) 
            {
                ex.printStackTrace();
            }
        }
        editorTexto.setText(texto);
        //editorTexto.setLineWrap(true);
    }

    class FiltroDeArquivo extends javax.swing.filechooser.FileFilter 
    {
        @Override
        public boolean accept(File file) 
        {
            return file.isDirectory() || file.getAbsolutePath().endsWith(".obj");
        }
        @Override
        public String getDescription() 
        {
            // This description will be displayed in the dialog,
            // hard-coded = ugly, should be done via I18N
            return "MV File(*.obj)";
        }
    } 
}
